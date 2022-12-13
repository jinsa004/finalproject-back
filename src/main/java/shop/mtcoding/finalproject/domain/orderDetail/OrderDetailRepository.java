package shop.mtcoding.finalproject.domain.orderDetail;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {

    @Query("select o from OrderDetail o join fetch o.menu m where o.order.id = :orderId")
    List<OrderDetail> findAllByOrderIdToOrderHistory(@Param("orderId") Long orderId);

    @Query(value = "select * from order_details od left outer join orders o on o.id = od.order_id left outer join menus m on m.id = od.menu_id where o.id = :orderId", nativeQuery = true)
    List<OrderDetail> findAllByOrderId(@Param("orderId") Long orderId);

}
