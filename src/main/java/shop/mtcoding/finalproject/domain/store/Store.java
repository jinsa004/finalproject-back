package shop.mtcoding.finalproject.domain.store;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.AudingTime;
import shop.mtcoding.finalproject.domain.user.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "stores")
@Entity
public class Store extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(nullable = true)
    private StoreCategoryEnum category;

    @Column(nullable = true, length = 30)
    private String name;

    @Column(nullable = true, length = 11)
    private String phone;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] thumbnail;

    @Column(nullable = false, length = 20)
    private String ceoName;

    @Column(nullable = false, length = 10)
    private String businessNumber;

    @Column(nullable = false, length = 60)
    private String businessAddress;

    @Column(nullable = true, length = 10)
    private String openTime;

    @Column(nullable = true, length = 10)
    private String closeTime;

    @Column(nullable = false)
    private int minAmount;

    @Column(nullable = true, length = 4)
    private String deliveryHour;

    @Column(nullable = false)
    private int deliveryCost;

    @Column(nullable = true, length = 100)
    private String intro;

    @Column(nullable = true, length = 100)
    private String notice;

    @Column(nullable = true)
    private boolean isOpend;

    @Column(nullable = true)
    private boolean isAccept;

    @Column(nullable = false)
    private boolean isClosure;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    public Store(Long id, StoreCategoryEnum category, String name, String phone, byte[] thumbnail, String ceoName,
            String businessNumber, String businessAddress, String openTime, String closeTime, int minAmount,
            String deliveryHour, int deliveryCost, String intro, String notice, boolean isOpend, boolean isAccept,
            boolean isClosure, LocalDateTime createdAt, User user) {
        this.id = id;
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
        this.isClosure = isClosure;
        this.createdAt = createdAt;
        this.user = user;
    }

    public Store close(Store store) {
        return Store.builder()
                .id(id)
                .category(store.getCategory())
                .name(store.getName())
                .phone(store.getPhone())
                .thumbnail(store.getThumbnail())
                .ceoName(ceoName)
                .businessNumber(businessNumber)
                .businessAddress(businessAddress)
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .minAmount(store.getMinAmount())
                .deliveryHour(store.getDeliveryHour())
                .deliveryCost(store.getDeliveryCost())
                .intro(store.getIntro())
                .notice(store.getNotice())
                .isOpend(store.isOpend)
                .isAccept(isAccept)
                .isClosure(true)
                .user(user)
                .createdAt(getCreatedAt())
                .build();
    }

    public Store update(Store store) {
        return Store.builder()
                .id(id)
                .category(store.getCategory())
                .name(store.getName())
                .phone(store.getPhone())
                .thumbnail(store.getThumbnail())
                .ceoName(ceoName)
                .businessNumber(businessNumber)
                .businessAddress(businessAddress)
                .openTime(store.getOpenTime())
                .closeTime(store.getCloseTime())
                .minAmount(store.getMinAmount())
                .deliveryHour(store.getDeliveryHour())
                .deliveryCost(store.getDeliveryCost())
                .intro(store.getIntro())
                .notice(store.getNotice())
                .isOpend(store.isOpend)
                .isAccept(isAccept)
                .isClosure(isClosure)
                .user(user)
                .createdAt(getCreatedAt())
                .build();
    }

    public void updateOpen(Boolean opened) {
        this.isOpend = opened;
    }

    public void updateClosure(Boolean closure) {
        this.isClosure = closure;
    }

    public void updateAccept(Boolean accept) {
        this.isAccept = accept;
    }

    public void checkCeo(Long userId) {
        if (!this.user.getId().equals(userId)) {
            throw new CustomApiException("권한이 없습니다", HttpStatus.BAD_REQUEST);
        }
    }

    public void checkStoreBusinessState() {
        if (!this.isAccept || this.isClosure) {
            throw new CustomApiException("가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
    }
}