package shop.mtcoding.finalproject.domain.customerReview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CustomerReviewRepository extends JpaRepository<CustomerReview, Long> {

    // @Query("select cr from CustomerReview cr where cr.user.id = :userId")
    // List<CustomerReview> findReviewListByUserId(@Param("userId") Long userId);

    // @Query("select cr from CustomerReview cr left join cr.얘는 ceoreview가 없는데..?")
    // List<CustomerReview> findReviewListByUserId(@Param("userId") Long userId);
}
