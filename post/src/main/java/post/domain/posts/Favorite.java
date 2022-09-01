package post.domain.posts;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.data.annotation.CreatedDate;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import post.domain.CreateTimeEntity;

@Getter
@NoArgsConstructor
@Entity
@Table(name="favorite")
public class Favorite extends CreateTimeEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

	@Column(nullable = false)
    private String token_id;

    @Column(length = 500, nullable =false)
    private String wallet;

    @CreatedDate
	private LocalDateTime createdDate ;
    
    @Column(nullable = false)
    private int del_item;
 
    @Builder
    public Favorite(String tokenId, String wallet, LocalDateTime createdDate, int delItem  ) {
    	this.token_id = tokenId ;
    	this.wallet = wallet ;
    	this.createdDate = createdDate ;
    	this.del_item = delItem ;
    }
    
    //delItem 업데이트
    public void update(int delItem) {
        this.del_item = delItem;
    }
}
