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

    @Query(value = "select *, avg(cr.star_point) from customer_reviews cr left join stores s on s.id = cr.store_id", nativeQuery = true)
    List<CustomerReview> starPointAverageToStore();

    // 별점평균, 리뷰갯수 연산데이터 쿼리
    @Query(value = "select count(cr.id) id, avg(cr.star_point) starPoint from customer_reviews cr where cr.store_id =:storeId group by cr.store_id", nativeQuery = true)
    CustomerInterface findByStoreId(@Param("storeId") Long storeId);
}
