package shop.mtcoding.finalproject.domain.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // @Query("select cr from CustomerReview cr where cr.user.id = :reviewId")
    // List<Order> findReviewListByUserId(@Param("reviewId") Long reviewId);
}
