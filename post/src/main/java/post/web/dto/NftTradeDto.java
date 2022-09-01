/*
 * ft 거래 및 nft거래에서 사용되는 dto
 * sender, to는 nft 기준이므로
 * ft거래에서는 역순으로 생각하여야함
 * */

package post.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import post.domain.posts.Trading;

@Getter
@NoArgsConstructor
public class NftTradeDto {

	private String token_id ;
	private String sender ;
	private String owner;
	private String to ;
	private float price ;
	
	public NftTradeDto(String token_id, String sender, String owner, String to) {
		this.token_id = token_id;
		this.sender = sender ;
		this.owner = owner ;
		this.to = to;
	}
	
	public void setPrice(float price) {
		this.price = price ;
	}
	
	public Trading toEntity() {
		return Trading.builder()
				.price(price)
				.receiver(owner)
				.sender(sender)
				.success("N")
				.tokenId(token_id)
				.build();
}
}
