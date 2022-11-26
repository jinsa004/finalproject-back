package shop.mtcoding.finalproject.domain.customerReview;

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
@Table(name = "customer_reviews")
@Entity
public class CustomerReview extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long orderId;

    @Column(nullable = false, length = 100)
    private String content;

    @Column(nullable = false)
    private String starPoint;

    @Column(nullable = true)
    private String photo;

    @Column(nullable = false)
    private boolean isClosure;

    @Builder
    public CustomerReview(Long id, Long userId, Long orderId, String content, String starPoint, String photo,
            boolean isClosure) {
        this.id = id;
        this.userId = userId;
        this.orderId = orderId;
        this.content = content;
        this.starPoint = starPoint;
        this.photo = photo;
        this.isClosure = isClosure;
    }

}

// 기존 테이블명 : reviews
// 변경 사유 : 테이블명이 비슷해야 더 편하게 쓸것 같아서 변경함
// 기존 테이블명으로 수정해주셔도 됩니다!