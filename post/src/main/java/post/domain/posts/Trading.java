package post.domain.posts;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name="trading")
public class Trading extends CreateTimeEntity {
	
	@Id
	@Column(nullable = false)
    private String token_id;

    @Column(nullable =false)
    private String sender;
    
    @Column(nullable =false)
    private String receiver;

    @Column(nullable =false)
    private float price;
    
    @Column(nullable =false)
    private String success;
    
    @CreatedDate
	private LocalDateTime createdDate ;
    

    @Builder
    public Trading(String tokenId, String sender, String receiver, float price , String success ) {
    	this.token_id = tokenId ;
    	this.sender = sender ;
    	this.receiver = receiver ;
    	this.price = price ;
    	this.success = success;
    }
    
    // success Y, N 업데이트
    public void update(String success) {
        this.success = success;
    }

}
