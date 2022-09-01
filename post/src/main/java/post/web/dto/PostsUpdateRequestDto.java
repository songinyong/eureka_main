/*
 * 게시물 정보 수정 dto
 * 현재 블록체인 연동후 업데이트 필요
 * */

package post.web.dto;


import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class PostsUpdateRequestDto {

	private float price;
	private String wallet_address;
	
	@Builder
	public PostsUpdateRequestDto(float price, String wallet_address) {
		this.price = price ;
		this.wallet_address = wallet_address ;
	}
}
