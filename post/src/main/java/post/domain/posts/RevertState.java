/*
 * sell_state 3인 아이템들을 따로 확인하는 View Table과 연동하기 위한 도메인
 * */

package post.domain.posts;

import java.time.LocalDateTime;

public interface RevertState {

	String getTOKEN_ID() ;

	LocalDateTime getMODIFIED_DATE() ;
	
	String getSELL_STATE() ; 


}
