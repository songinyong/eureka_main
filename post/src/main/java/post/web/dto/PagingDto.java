/*
 * DB에서 게시물 정보들을 페이징 형태로 불러오는 dto 
 * */

package post.web.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.NoArgsConstructor;
import post.domain.posts.Posts;

@Getter
@NoArgsConstructor
public class PagingDto {

	private Long id ;
	private String title ;
	private int sell_state;
	private float price;
	private LocalDateTime createdDate ;
	private LocalDateTime ModifiedDate ;
	private String nft_description;
	private String nft_hash;
	private String token_id;
	private String token_name;
	private String creator;
	private String image_path ;
	private String owner;

	
	public PagingDto(Posts entity) {
		this.id = entity.getId();
        this.title = entity.getTitle();
        this.sell_state = entity.getSell_state();
        this.price = entity.getPrice();
        this.createdDate = entity.getCreatedDate();
        this.ModifiedDate = entity.getModifiedDate();
        this.nft_description = entity.getNft_description();
        this.nft_hash = entity.getNft_hash();
        this.token_id = entity.getToken_id();
        this.token_name = entity.getToken_name();
        this.creator = entity.getCreator();
        this.image_path = entity.getImage_path();
        this.owner = entity.getOwner();
    
	}
}

