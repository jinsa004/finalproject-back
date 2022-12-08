package shop.mtcoding.finalproject.domain.like;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LikeRepository extends JpaRepository<Like, Long> {

    @Query(value = "select count(id) count from likes l where l.store_id = :storeId", nativeQuery = true)
    LikeInterface findByStoreId(@Param("storeId") Long storeId);
}
