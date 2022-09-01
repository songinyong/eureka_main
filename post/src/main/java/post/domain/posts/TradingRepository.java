package post.domain.posts;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface TradingRepository extends JpaRepository<Trading, Long> {
	
	   @Query("SELECT t FROM Trading t WHERE t.token_id =?1")
	   Trading findTrading(String token_id);
}