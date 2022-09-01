/*
 * 클라이언트와 호출하며 FT, NFT trade를 담당하는 서비스
 * 
 * 물건을 거래할려고 하는 지갑의 잔액조회 서비스이후 nft 거래가 진행된다. 
 * */

package post.service;

import java.util.Optional;

import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import post.domain.posts.Posts;
import post.domain.posts.PostsRepository;
import post.domain.posts.TradingRepository;
import post.web.dto.NftTradeDto;

@RequiredArgsConstructor
@Service
public class TradeService {
	
	@Autowired
	PostsRepository postsRepository;
	
	@Autowired
	TradingRepository tradingRepository;
	
	/* ft 잔액조회가 완료되면 거래할려고하는 거래정보를 넘겨받아 DB에 저장하고
	 * nft 아이템의 sell_state를 거래진행중인 2로 변경한다.
	 * */
    @Transactional
    public ResponseEntity<JSONObject> chgStat(NftTradeDto tradeDto) {
    	// sell_state가 1일때만 거래 가능
    	JSONObject resultObj = new JSONObject();  
    	
    	//잔액조회 if문 추가
    	WalletService waletService = new WalletService();
    	Optional<Posts> post = postsRepository.findBytokenID(tradeDto.getToken_id());
    	float price = 0 ;
    	if(post.isPresent())
    		price = post.get().getPrice();
    	else {
    		resultObj.put("result","false");
    		resultObj.put("reason","id");
    		return new ResponseEntity<JSONObject>(resultObj, HttpStatus.BAD_REQUEST);
    	}
    	
    	if(waletService.getNumOfFt(tradeDto.getTo(), price)) {
    	
    	
    	//sell_state가 1인 아이템인지 확인
    	if(postsRepository.findBytokenID(tradeDto.getToken_id()).get().getSell_state() == 1 ) {
    		
    		//trading에 로그 추가
    		tradingRepository.save(tradeDto.toEntity());
    		
			Optional<Posts> posts = postsRepository.findBytokenID(tradeDto.getToken_id());
			posts.get().stateUpdate(2);
    		
			tradeDto.setPrice(price);
    		//db에 trading 저장후 ft거래 호출
    		boolean ftTrade = (boolean) fttrade(tradeDto).getBody().get("result");
    		if(ftTrade) {
    			//ft trade성공시 ft거래 성공을 Y로 업데이트 기능 추가
    			tradingRepository.findTrading(tradeDto.getToken_id()).update("Y");
    			
    			resultObj.put("result","true");
    			resultObj.put("reason","success ft trading ");
    			return new ResponseEntity<JSONObject>(resultObj, HttpStatus.OK);
    		
    		}
    		//ft 거래 실패할시
    		else {
        		resultObj.put("result","false");
        		resultObj.put("reason","ft transaction error");
        		return new ResponseEntity<JSONObject>(resultObj, HttpStatus.BAD_REQUEST);
    		}
    	}
    	else {
    		resultObj.put("result","false");
    		resultObj.put("reason","sell_state is not 1");
    		return new ResponseEntity<JSONObject>(resultObj, HttpStatus.BAD_REQUEST);
    	}
    	
    	}
    	//잔액조회 api로 잔액부족임을 확인할때
    	else {
    		resultObj.put("result","false");
    		resultObj.put("reason","insufficient ft");
    		return new ResponseEntity<JSONObject>(resultObj, HttpStatus.BAD_REQUEST);
    	}
    }
    
    //ft 거래 api, 거래 성공시 테이블에서 거래 성공 여부를 Y로 변경한다.
    @Transactional
    private ResponseEntity<JSONObject> fttrade(NftTradeDto tradeDto) {
    	System.out.println(tradeDto.getPrice());
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		RestTemplate rt = new RestTemplate();
	
		JSONObject createData = new JSONObject();
		
		createData.put("to", tradeDto.getSender());
		createData.put("sender",tradeDto.getTo());
		createData.put("amount" ,"0x"+Integer.toHexString((int)tradeDto.getPrice()));
		
		 HttpEntity<String> entity = 
			      new HttpEntity<String>(createData.toString(), headers);
		 System.out.println(entity);
    	String uri = "http://54.180.114.232:5555/chain/ftTrade";
    	
		try {
			ResponseEntity<JSONObject> result =rt.exchange(uri, HttpMethod.POST, entity, JSONObject.class);

			return result;
	
		}
		catch (HttpClientErrorException e) {
			JSONObject resultObj = new JSONObject();
			
			resultObj.put("result",false);
			resultObj.put("reason",e);
			return new ResponseEntity<JSONObject>(resultObj, HttpStatus.FORBIDDEN);	
		}
    	
    }
    
    
	
	//거래 성공 여부를 Y이면서 sell_state가 2인 아이템의 nft 소유권을 이전
    @Transactional
    public ResponseEntity<JSONObject> nfttrade(NftTradeDto tradeDto) {
    	// sell_state가 2일떄만 nft 소유권 이전 가능
    	JSONObject resultObj = new JSONObject();  
    	if(postsRepository.findBytokenID(tradeDto.getToken_id()).get().getSell_state() == 2) {
    		
		RestTemplate rt = new RestTemplate();
				
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");

	
		JSONObject createData = new JSONObject();
		
		createData.put("to", tradeDto.getTo());
		createData.put("sender",tradeDto.getSender());
		createData.put("owner" ,tradeDto.getOwner());
		createData.put("token_id", tradeDto.getToken_id());

		 HttpEntity<String> entity = 
			      new HttpEntity<String>(createData.toString(), headers);
	
		String uri = "http://54.180.114.232:5555/chain/trade";
		
			try {
				ResponseEntity<JSONObject> result =rt.exchange(uri, HttpMethod.PUT, entity, JSONObject.class);
		
				//거래 성공한 아이템의 sell_state를 3으로 바꿈
				System.out.println(result);
				System.out.println(result.getBody().get("token_id"));
				Optional<Posts> posts = postsRepository.findBytokenID((String)result.getBody().get("token_id"));
				posts.get().stateUpdate(3);
				resultObj.put("result","true");
				resultObj.put("hash",result.getBody().get("hash"));
				return new ResponseEntity<JSONObject>(resultObj, HttpStatus.ACCEPTED);
		
			}
			catch (Exception e) {
				resultObj.put("result","false");
				System.out.println(e);
				return new ResponseEntity<JSONObject>(resultObj, HttpStatus.FORBIDDEN);	
			}
		
    	}	
    	//sell_state가 1이 아닌 아이템을 거래할려고 할시
    	else {
    		resultObj.put("result","false");
    		resultObj.put("reason","sell_state is not 1");
    		return new ResponseEntity<JSONObject>(resultObj, HttpStatus.BAD_REQUEST);
    	}
    }


    
}
