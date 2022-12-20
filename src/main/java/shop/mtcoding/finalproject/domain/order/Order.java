package shop.mtcoding.finalproject.domain.order;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.finalproject.config.enums.DeliveryStateEnum;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.domain.AudingTime;
import shop.mtcoding.finalproject.domain.payment.Payment;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "orders")
@Entity
public class Order extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 80)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private OrderStateEnum state;

    @Column(nullable = true)
    private String reason;

    @Column(nullable = false)
    private boolean isClosure;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private DeliveryStateEnum deliveryStateEnum;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    private Payment payment;

    @Column(nullable = true)
    private LocalDateTime completeTime;

    @Column(nullable = true)
    private String deliveryTime;

    @Builder
    public Order(Long id, String comment, OrderStateEnum state, String reason, boolean isClosure,
            DeliveryStateEnum deliveryStateEnum, User user, Store store, Payment payment, LocalDateTime completeTime,
            String deliveryTime, LocalDateTime createdAt) {
        this.id = id;
        this.comment = comment;
        this.state = state;
        this.reason = reason;
        this.isClosure = isClosure;
        this.deliveryStateEnum = deliveryStateEnum;
        this.user = user;
        this.store = store;
        this.payment = payment;
        this.completeTime = completeTime;
        this.deliveryTime = deliveryTime;
        this.createdAt = createdAt;
    }

    public Order update(Order order) {
        return Order.builder()
                .id(id)
                .comment(comment)
                .state(order.getState())
                .reason(order.getReason())
                .isClosure(isClosure)
                .deliveryStateEnum(deliveryStateEnum)
                .user(user)
                .store(store)
                .payment(payment)
                .completeTime(order.getCompleteTime())
                .deliveryTime(order.getDeliveryTime())
                .createdAt(createdAt)
                .build();
    }

    public void delete(Order order) {
        this.isClosure = false;
    }
}
