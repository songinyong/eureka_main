/*즐겨찾기 처리 dto*/

package post.web.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import post.domain.posts.Favorite;

@Getter
@NoArgsConstructor
public class FavoriteDto {
	

    private String tokenId;
    private String wallet_address;
  
    
    public FavoriteDto(String tokenId, String wallet_address ) {
    	this.tokenId = tokenId ;
    	this.wallet_address = wallet_address ;
    }
    
    public Favorite toEntity() {
    	return Favorite.builder()
    			.tokenId(tokenId)
    			.wallet(wallet_address)
    			.delItem(0)
    			.build() ;
    }
}
