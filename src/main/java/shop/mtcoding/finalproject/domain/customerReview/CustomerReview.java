package shop.mtcoding.finalproject.domain.customerReview;

import java.time.LocalDateTime;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.AudingTime;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "customer_reviews")
@Entity
public class CustomerReview extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false)
    private Double starPoint;

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] photo;

    @Column(nullable = false)
    private boolean isClosure;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @OneToOne(fetch = FetchType.LAZY)
    private CeoReview ceoReview;

    @Builder
    public CustomerReview(Long id, String content, Double starPoint, byte[] photo, boolean isClosure, User user,
            Store store, Order order, CeoReview ceoReview, LocalDateTime createdAt) {
        this.id = id;
        this.content = content;
        this.starPoint = starPoint;
        this.photo = photo;
        this.isClosure = isClosure;
        this.user = user;
        this.store = store;
        this.order = order;
        this.createdAt = createdAt;
        this.ceoReview = ceoReview;
    }

    public void ??????????????????() {
        this.isClosure = true;
    }

    public CustomerReview updateCeoReview(CeoReview ceoReviewPS) {
        return CustomerReview.builder()
                .id(id)
                .content(content)
                .starPoint(starPoint)
                .photo(photo)
                .isClosure(isClosure)
                .user(user)
                .store(store)
                .order(order)
                .ceoReview(ceoReviewPS)
                .createdAt(createdAt)
                .build();
    }

    public void checkCeoReview() {
        if (this.ceoReview != null) {
            throw new CustomApiException("?????? ????????? ???????????????.", HttpStatus.BAD_REQUEST);
        }
    }

}

// ?????? ???????????? : reviews
// ?????? ?????? : ??????????????? ???????????? ??? ????????? ?????? ????????? ?????????
// ?????? ?????????????????? ?????????????????? ?????????!