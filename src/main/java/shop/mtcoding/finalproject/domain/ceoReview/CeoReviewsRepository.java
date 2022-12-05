package shop.mtcoding.finalproject.domain.ceoReview;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface CeoReviewsRepository extends JpaRepository<CeoReviews, Long> {
}
