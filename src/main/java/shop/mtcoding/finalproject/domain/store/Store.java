package shop.mtcoding.finalproject.domain.store;

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
@Table(name = "stores")
@Entity
public class Store extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = true)
    private String category;

    @Column(nullable = true, length = 30)
    private String name;

    @Column(nullable = true, length = 11)
    private String phone;

    @Column(nullable = true)
    private String thumbnail; // 사진 타입

    @Column(nullable = false, length = 20)
    private String ceoName;

    @Column(nullable = false, length = 10)
    private String businessNumber;

    @Column(nullable = false, length = 60)
    private String businessAddress;

    @Column(nullable = true, length = 2)
    private String openTime;

    @Column(nullable = true, length = 2)
    private String closeTime;

    @Column(nullable = true, length = 5)
    private String minAmount;

    @Column(nullable = true, length = 4)
    private String deliveryHour;

    @Column(nullable = true, length = 5)
    private String deliveryCost;

    @Column(nullable = true, length = 100)
    private String intro;

    @Column(nullable = true, length = 100)
    private String notice;

    @Column(nullable = true)
    private boolean isOpend;

    @Column(nullable = true)
    private boolean isAccept;

    @Builder
    public Store(Long id, String userId, String category, String name, String phone, String thumbnail, String ceoName,
            String businessNumber, String businessAddress, String openTime, String closeTime, String minAmount,
            String deliveryHour, String deliveryCost, String intro, String notice, boolean isOpend, boolean isAccept) {
        this.id = id;
        this.userId = userId;
        this.category = category;
        this.name = name;
        this.phone = phone;
        this.thumbnail = thumbnail;
        this.ceoName = ceoName;
        this.businessNumber = businessNumber;
        this.businessAddress = businessAddress;
        this.openTime = openTime;
        this.closeTime = closeTime;
        this.minAmount = minAmount;
        this.deliveryHour = deliveryHour;
        this.deliveryCost = deliveryCost;
        this.intro = intro;
        this.notice = notice;
        this.isOpend = isOpend;
        this.isAccept = isAccept;
    }

}