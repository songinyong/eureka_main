/*
 * Scheduler 관련 Service
 * 클라이언트가 호출할 수 있는 api 제공안함
 * */

package post.service;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.RequiredArgsConstructor;
import post.domain.posts.Posts;
import post.domain.posts.PostsRepository;
import post.domain.posts.RevertState;
import post.web.dto.PostsSaveRequestDto;

@RequiredArgsConstructor
@Service
public class SchedulerService {
	private final PostsRepository postsRepository;

    /*
     * 블록체인 아이템 DB로부터 정보를 받아온뒤 nft token_id를 기준으로 DB에 없는 아이템들을 추가한다.
     * crontab으로 동기화 시행
     * */
    @Transactional
    public ResponseEntity<JSONObject> recvNftInfofromBlckdb() {
    	MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
		
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json;charset=utf-8");
		
		HttpEntity<MultiValueMap<String, String>> entity = new HttpEntity<>(params, headers); 
		RestTemplate rt = new RestTemplate();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
		JsonNode response =rt.getForObject("http://54.180.114.232:5555/chain/findAllnfts", JsonNode.class,entity );
    	
		
    	JsonNode items =  response.get("items");
    	System.out.println(items);
    	List<PostsSaveRequestDto> accountList = mapper.convertValue(
    		    items,
    		    new TypeReference<List<PostsSaveRequestDto>>(){}
    		);
    	//동기화하여 추가한 아이템 수 체크
    	int count = 0;
    	
    	/*Stream으로 변경 예정*/
    	for(int i =0; i<accountList.size(); i++) {
    		if(postsRepository.checkID(accountList.get(i).getToken_id()).isEmpty()) {
    		postsRepository.save(accountList.get(i).toEntity());
    		count++;
    	    } 
    		//이미 있는 아이디인경우 수정된값 있을시 업데이트 
    		// sell_state 2인경우 update 진행되지 않음?
    		else {
    			Optional<Posts> posts = postsRepository.findBytokenID(accountList.get(i).getToken_id());
    			posts.get().synUpdate(accountList.get(i).getOwner(), accountList.get(i).getModifiedDate());
    		}
    		
    	}
		
		JSONObject resultObj = new JSONObject();  
		resultObj.put("result","true");
		resultObj.put("count",count);
    	
    	return new ResponseEntity<JSONObject>(resultObj, HttpStatus.ACCEPTED);
    	
		} catch (Exception e) {
			JSONObject resultObj = new JSONObject();  
			resultObj.put("result","false");
	    	return new ResponseEntity<JSONObject>(resultObj, HttpStatus.ACCEPTED);
		}
    }
	
	/*시간이 지나면 sell_state가 3인 아이템들이 다시 0으로 변경된다.
	 * 거래 완료(거래 직후 일정시간 수정이나 거래 불가능) -> 다시 일반 default 상태로 바꿈
	crontab으로 동기화 시행
	*/
    @Transactional
    public void revertState() {
    List<RevertState> revertstate = postsRepository.findRevertState() ;
    
    for(int i=0; i<revertstate.size(); i++) {
    	// 생성된지 몇분 지났는지 확인
    	int time = (int) (ChronoUnit.MINUTES.between(revertstate.get(i).getMODIFIED_DATE(),LocalDateTime.now())-540);
    	
    	if(time > 4) {
    		//token_id 기준으로 post 검색후 sell_state값을 다시 0으로 바꿈
    		postsRepository.findBytokenID(revertstate.get(i).getTOKEN_ID()).get().stateUpdate(0);
    	}
    }
    
  
    }
}
