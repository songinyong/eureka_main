package post.web.dto;
/*
 * 블록체인 서비스에서 아이템 정보를 읽어오고 DB에 저장하는 dto
 * 
 *  21.12.07: ICT 발표전 최종 코드 버전
 *  21.01.04: 현재 테이블 구조 변경으로 수정함
 * */

import java.time.LocalDateTime;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import post.domain.posts.Posts;

@Getter
@NoArgsConstructor
public class PostsSaveRequestDto {

	private Long id ;
	private String title ;
	private int sell_state;
	private float price;
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	private LocalDateTime createdDate ;
	@JsonDeserialize(using=LocalDateTimeDeserializer.class)
	private LocalDateTime modifiedDate ;
	private String nft_description;
	private String nft_hash;
	private String token_id;
	private String token_name;
	private String creator;
	private String image_path ;
	private String owner;
	
	//후에 msa 구현후 user의 id를 받아와 저장private String user_id ; 
	//is_sell은 판매중 0 , 거래중 1, 판매완료 2 3개의 상태를 가지고 있으면 기본적으로 등록시에는 0이 디폴트이다.
	
    @Builder
    public PostsSaveRequestDto(String title, float price, String nft_description, String nft_hash, String token_id, String token_name ,String creator, String image_path, String owner, LocalDateTime createdDate, LocalDateTime modifiedDate ) {
        this.title = title;    
       
        this.price = price ;
        this.nft_description = nft_description;
        this.nft_hash = nft_hash;
        this.token_id = token_id;
        this.token_name = token_name;
        this.creator = creator;
        this.image_path = image_path;
        this.owner = owner;
        this.createdDate = createdDate;
        this.modifiedDate = modifiedDate;
    }
	
	public Posts toEntity() {
		return Posts.builder()
				.title(title)
				.price(price)
				.nft_description(nft_description)
				.nft_hash(nft_hash)
				.token_id(token_id)
				.token_name(token_name)
				.creator(creator)
				.image_path(image_path)
				.owner(owner)
				.createdDate(createdDate)
				.modifiedDate(modifiedDate)
				.build();
	}
	
}
