package shop.mtcoding.finalproject.domain.orderDetail;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.finalproject.domain.AudingTime;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.order.Order;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "order_details")
@Entity
public class OrderDetail extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int count;

    @OneToOne
    private Order order;

    @OneToOne
    private Menu menu;

    @Builder
    public OrderDetail(Long id, int count, Order order, Menu menu) {
        this.id = id;
        this.count = count;
        this.order = order;
        this.menu = menu;
    }

}
