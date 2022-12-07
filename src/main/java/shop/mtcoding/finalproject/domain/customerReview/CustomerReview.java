package shop.mtcoding.finalproject.domain.ceoReview;

import javax.persistence.Column;
import javax.persistence.Entity;
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
import shop.mtcoding.finalproject.domain.AudingTime;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.store.Store;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "ceo_reviews")
@Entity
public class CeoReview extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = true, length = 100)
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    private Store store;

    @OneToOne(fetch = FetchType.LAZY)
    private Order order;

    @Builder
    public CeoReview(Long id, String content, Store store, Order order) {
        this.id = id;
        this.content = content;
        this.store = store;
        this.order = order;
    }
}

// 기존 테이블명 : comments
// 변경 사유 : 테이블명이 비슷해야 더 편하게 쓸것 같아서 변경함
// 기존 테이블명으로 수정해주셔도 됩니다!