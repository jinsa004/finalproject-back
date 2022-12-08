package shop.mtcoding.finalproject.domain.customerReview;

import java.util.List;
import java.util.Optional;

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

    // @Query(value = "select * customer_reviews cr left outer join users u on
    // cr.user_id = u.id left outer join stores s on cr.store_id = s.id left outer
    // join order o on cr.order_id = o.id left outer join ceo_reviews ceor
    // cr.ceo_reviews_id = ceor.id where cr.ceo_reviews_id = :ceoReviewId",
    // nativeQuery = true)
    @Query("select cr from CustomerReview cr join fetch cr.ceoReview c join fetch cr.store s join fetch cr.order o join fetch cr.user u where c.id = :ceoReviewId")
    Optional<CustomerReview> findByCeoReviewId(@Param("ceoReviewId") Long ceoReviewId);

}
