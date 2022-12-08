package shop.mtcoding.finalproject.domain.ceoReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CeoReviewRepository extends JpaRepository<CeoReview, Long> {
    // 리뷰 답글 개수
    @Query(value = "select count(c.id) count from ceo_reviews c where c.store_id = :storeId", nativeQuery = true)
    CeoReviewInterface findByStoreId(@Param("storeId") Long storeId);
}
