package shop.mtcoding.finalproject.domain.orderDetail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "order_details")
@Entity
public class OrderDetail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false)
    private Long menuId;

    @Column(nullable = false)
    private int count;

    @Builder
    public OrderDetail(Long id, Long orderId, Long menuId, int count) {
        this.id = id;
        this.orderId = orderId;
        this.menuId = menuId;
        this.count = count;
    }

}
