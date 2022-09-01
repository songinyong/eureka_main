/*
 * wallet의 잔액조회, contract정보 조회 기능을 제공한다.
 * */

package post.service;

import org.json.simple.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import lombok.RequiredArgsConstructor;
import post.web.dto.GetNftIdDto;

@RequiredArgsConstructor
@Service
public class WalletService {
	
	//trade 서비스가 ft 잔액조회를 위해 호출함 
    protected boolean getNumOfFt(String wallet, float price) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		
		RestTemplate rt = new RestTemplate();
		JSONObject createData = new JSONObject();
		 HttpEntity<String> entity = 
			      new HttpEntity<String>(createData.toString(), headers);
		 
    	String uri = "http://54.180.114.232:5555/chain/numOfFt?address="+wallet;
    	
		try {
			ResponseEntity<JSONObject> result =rt.exchange(uri, HttpMethod.GET, entity, JSONObject.class);
			//지갑 잔액이 구매하려는 금액보다 부족하면 api 호출할 수 없음
			if(Integer.decode((String) result.getBody().get("balance")) < price ) {
				return false ;
			}
			else 
				return true;
	
		}
		catch (HttpClientErrorException e) {

			return false ;
		}
    }
	
	
	//client가 ft 잔액조회를 위해 호출함 
    public ResponseEntity<JSONObject> getNumOfFt(String wallet) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		
		RestTemplate rt = new RestTemplate();
		JSONObject createData = new JSONObject();
		 HttpEntity<String> entity = 
			      new HttpEntity<String>(createData.toString(), headers);
		 
    	String uri = "http://54.180.114.232:5555/chain/numOfFt?address="+wallet;
    	
		try {
			ResponseEntity<JSONObject> result =rt.exchange(uri, HttpMethod.GET, entity, JSONObject.class);

			return result;
	
		}
		catch (HttpClientErrorException e) {
			JSONObject resultObj = new JSONObject();
			resultObj.put("result",false);
			resultObj.put("reason",e);
			return new ResponseEntity<JSONObject>(resultObj, HttpStatus.FORBIDDEN);	
		}
    }
    
    //contract 정보 조회
    public ResponseEntity<JSONObject> getContractInfo(GetNftIdDto getNftIdDto) {
		HttpHeaders headers = new HttpHeaders();
		headers.add("Content-type", "application/json");
		
		RestTemplate rt = new RestTemplate();
		JSONObject createData = new JSONObject();
		createData.put("owner", getNftIdDto.getOwner());
		createData.put("token_id", getNftIdDto.getToken_id());
		 HttpEntity<String> entity = 
			      new HttpEntity<String>(createData.toString(), headers);
		 
    	String uri = "http://54.180.114.232:5555/chain/contractInfo" ;
    	
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
    
    
}
