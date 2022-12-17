package shop.mtcoding.finalproject.domain.reportReview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface ReportReviewRepository extends JpaRepository<ReportReview, Long> {

    @Query("select r from ReportReview r join fetch User u on r.user.id = u.id")
    List<ReportReview> findAllByUser();

    @Query(value = "select * from report_reviews rr left join ceo_reviews cer on cer.id = rr.ceo_review_id left join customer_reviews cr on cr.id = rr.customer_review_id left join users u on u.id = rr.user_id where rr.customer_review_id = :customerReviewId", nativeQuery = true)
    ReportReview findByCustomerReviewId(@Param("customerReviewId") Long customerReviewId);

}
