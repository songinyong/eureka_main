package post.domain.posts;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;


public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

	  //token_id기준 모든 favorite 검색
	  @Query("SELECT f FROM Favorite f WHERE f.token_id =?1 ")
	  List<Favorite> findByTokenId(String token_id);
	  
	  //wallet 기준 delItem Y인 favorite 검색
	   @Query("SELECT f FROM Favorite f WHERE f.wallet =?1 AND f.del_item =?2")
	   List<Favorite> findByWallet(String wallet, int delItem);
	   
	//wallet 기준 delItem Y인 favorite 검색
	   @Query("SELECT f FROM Favorite f WHERE f.wallet =?1")
	   List<Favorite> findByWallet(String wallet);
}