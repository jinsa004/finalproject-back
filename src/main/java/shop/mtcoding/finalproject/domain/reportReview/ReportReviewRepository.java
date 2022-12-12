package shop.mtcoding.finalproject.domain.reportReview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface ReportReviewRepository extends JpaRepository<ReportReview, Long> {

    @Query("select r from ReportReview r join fetch User u on r.user.id = u.id")
    List<ReportReview> findAllByUser();
}
