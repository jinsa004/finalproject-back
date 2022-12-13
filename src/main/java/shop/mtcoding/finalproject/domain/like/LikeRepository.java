package shop.mtcoding.finalproject.domain.like;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query(value = "select count(id) count from likes l where l.store_id = :storeId", nativeQuery = true)
    LikeInterface findByStoreId(@Param("storeId") Long storeId);

    @Query(value = "select * from likes l left outer join users u on u.id = l.user_id left outer join stores s on s.id = l.store_id where l.user_id = :userId and l.store_id = :storeId", nativeQuery = true)
    Like findByUserIdAndStoreId(@Param("userId") Long userId, @Param("storeId") Long storeId);

    // 찜한 가게 목록보기 기능
    @Query("select l from Like l join fetch l.store s where l.user.id = :userId")
    List<Like> findByUserIdToLikeStore(@Param("userId") Long userId);
}
