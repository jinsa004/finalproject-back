package shop.mtcoding.finalproject.domain.order;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.domain.AudingTime;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "orders")
@Entity
public class Order extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long storeId;

    @Column(nullable = false)
    private int paymentId;

    @Column(nullable = true, length = 30)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private OrderStateEnum state;

    @Column(nullable = true)
    private String reason;

    @Column(nullable = false)
    private boolean isClosure;

    @Builder
    public Order(Long id, Long userId, Long storeId, int paymentId, String comment, OrderStateEnum state, String reason,
            boolean isClosure) {
        this.id = id;
        this.userId = userId;
        this.storeId = storeId;
        this.paymentId = paymentId;
        this.comment = comment;
        this.state = state;
        this.reason = reason;
        this.isClosure = isClosure;
    }

}
