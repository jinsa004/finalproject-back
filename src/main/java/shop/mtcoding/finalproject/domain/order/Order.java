package shop.mtcoding.finalproject.domain.order;

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
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.domain.AudingTime;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "orders")
@Entity
public class Order extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int paymentId;

    @Column(nullable = true, length = 80)
    private String comment;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private OrderStateEnum state;

    @Column(nullable = true)
    private String reason;

    @Column(nullable = false)
    private boolean isClosure;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @OneToOne
    private Store store;

    @Builder
    public Order(Long id, int paymentId, String comment, OrderStateEnum state, String reason, boolean isClosure,
            User user, Store store) {
        this.id = id;
        this.paymentId = paymentId;
        this.comment = comment;
        this.state = state;
        this.reason = reason;
        this.isClosure = isClosure;
        this.user = user;
        this.store = store;
    }

}
