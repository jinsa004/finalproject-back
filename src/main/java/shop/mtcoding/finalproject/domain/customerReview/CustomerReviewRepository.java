package shop.mtcoding.finalproject.domain.customerReview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long> {

    @Query(value = "select * from customer_reviews cr right outer join ceo_reviews cor on cor.id = cr.ceo_reviews_id where cr.user_id = :userId", nativeQuery = true)
    List<CustomerReview> findReviewListByUserId(@Param("userId") Long userId);

    @Query(value = "select avg(cr.star_point) from customer_reviews cr left join Stores s on cr.store_id = s.id", nativeQuery = true)
    List<CustomerReview> starPointAverageToStore();
}
