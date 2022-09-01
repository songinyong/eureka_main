package post.web.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import post.domain.posts.Favorite;

@Getter
@NoArgsConstructor
public class FavoriteResponseDto {
	

    private String tokenId;
    private String wallet;
	private LocalDateTime createdDate ;
    private int delItem;
    
    public FavoriteResponseDto(Favorite entity) {
    	this.tokenId = entity.getToken_id();
    	this.wallet = entity.getWallet();
    	this.createdDate = entity.getCreatedDate();
    	this.delItem = entity.getDel_item();
    }
    

}
