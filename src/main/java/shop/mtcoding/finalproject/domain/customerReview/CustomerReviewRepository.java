package shop.mtcoding.finalproject.domain.customerReview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long> {

    @Query("select cr from CustomerReviews cr where cr.user.id = :userId")
    List<CustomerReview> findReviewListByUserId(@Param("userId") Long userId);
}
