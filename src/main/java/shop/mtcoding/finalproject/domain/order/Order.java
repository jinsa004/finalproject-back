package shop.mtcoding.finalproject.domain.order;

import java.sql.Timestamp;
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

    @Column(nullable = false)
    private Timestamp createdTime;

    @Column(nullable = false)
    private String state;

    @Column(nullable = true)
    private String reason;

    @Column(nullable = false)
    private boolean is_closure;

    @Builder
    public Order(Long id, Long userId, Long storeId, int paymentId, String comment, Timestamp createdTime, String state,
            String reason, boolean is_closure) {
        this.id = id;
        this.userId = userId;
        this.storeId = storeId;
        this.paymentId = paymentId;
        this.comment = comment;
        this.createdTime = createdTime;
        this.state = state;
        this.reason = reason;
        this.is_closure = is_closure;
    }

}
