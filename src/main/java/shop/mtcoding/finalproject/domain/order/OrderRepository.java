package shop.mtcoding.finalproject.domain.order;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderRepository extends JpaRepository<Order, Long> {
    // @Query("select cr from CustomerReview cr where cr.user.id = :reviewId")
    // List<Order> findReviewListByUserId(@Param("reviewId") Long reviewId);

    @Query("select o from Order o join fetch Store s on o.store.id = s.id where o.user.id = :userId and isClosure = true")
    List<Order> findAllByUserId(@Param("userId") Long userId);

    @Query(value = "select * from orders od left outer join users u on od.user_id = u.id left outer join stores s on od.store_id = s.id where s.id = :storeId", nativeQuery = true)
    List<Order> findAllByStoreId(@Param("storeId") Long storeId);
}
