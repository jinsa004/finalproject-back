package shop.mtcoding.finalproject.domain.payment;

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

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "payments")
@Entity
public class Payment extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = true)
    private Long orderId;
    @Column(nullable = false)
    private String merchantUid;
    @Column(nullable = false)
    private String impUid;
    @Column(nullable = false)
    private int amount;
    @Column(nullable = false)
    private String nickname;
    @Column(nullable = false)
    private String menuName;
    @Column(nullable = false)
    private String payMethod;
    @Column(nullable = true)
    private boolean isCanceled;

    @Builder
    public Payment(Long id, Long orderId, String merchantUid, String impUid, int amount, String nickname,
            String menuName, String payMethod, boolean isCanceled) {
        this.id = id;
        this.orderId = orderId;
        this.merchantUid = merchantUid;
        this.impUid = impUid;
        this.amount = amount;
        this.nickname = nickname;
        this.menuName = menuName;
        this.payMethod = payMethod;
        this.isCanceled = isCanceled;
    }

}
