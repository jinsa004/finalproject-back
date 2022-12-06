package shop.mtcoding.finalproject.domain.customerReview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long> {

    @Query(value = "select * from customer_reviews cr right outer join ceo_reviews cor on cor.id = cr.ceo_review_id where cr.user_id = :userId", nativeQuery = true)
    List<CustomerReview> findReviewListByUserId(@Param("userId") Long userId);

    @Query(value = "select * from customer_reviews cr left outer join ceo_reviews cor on cor.id = cr.ceo_review_id left outer join orders o on o.id = cr.order_id left outer join stores s on s.id = o.store_id where s.id = :storeId", nativeQuery = true)
    List<CustomerReview> findAllByStoreId(@Param("storeId") Long storeId);

    @Query(value = "update customer_reviews cr set ceo_review_id = :ceoReviewId", nativeQuery = true)
    void updateToCeoReviewId(@Param("ceoReviewId") Long ceoReviewId);

}
