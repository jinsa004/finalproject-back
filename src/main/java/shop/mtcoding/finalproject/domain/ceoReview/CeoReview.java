package shop.mtcoding.finalproject.domain.ceoReview;

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
@Table(name = "ceo_reviews")
@Entity
public class CeoReview extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long review_id;

    @Column(nullable = false)
    private Long user_id;

    @Column(nullable = false, length = 100)
    private String content;

    @Builder
    public CeoReview(Long id, Long review_id, Long user_id, String content) {
        this.id = id;
        this.review_id = review_id;
        this.user_id = user_id;
        this.content = content;
    }

}

// 기존 테이블명 : comments
// 변경 사유 : 테이블명이 비슷해야 더 편하게 쓸것 같아서 변경함
// 기존 테이블명으로 수정해주셔도 됩니다!