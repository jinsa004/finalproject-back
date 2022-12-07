package shop.mtcoding.finalproject.domain.order;

import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // @Query("select cr from CustomerReview cr where cr.user.id = :reviewId")
    // List<Order> findReviewListByUserId(@Param("reviewId") Long reviewId);
}
