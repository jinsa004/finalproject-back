package shop.mtcoding.finalproject.domain.ceoReview;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CeoReviewRepository extends JpaRepository<CeoReview, Long> {

    @Query(value = "select count(id) id from ceo_reviews c where c.store_id = :storeId", nativeQuery = true)
    CeoReview findByStoreId(@Param("storeId") Long storeId);
}
