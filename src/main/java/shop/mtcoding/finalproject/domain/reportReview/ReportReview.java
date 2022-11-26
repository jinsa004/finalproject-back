package shop.mtcoding.finalproject.domain.reportReview;

import java.time.LocalDateTime;

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
@Table(name = "report_review")
@Entity
public class ReportReview extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long reviewId;

    @Column(nullable = false)
    private String reason;

    @Column(nullable = true)
    private String adminComment;

    @Column(nullable = true)
    private boolean isResolve;

    @Column(nullable = true)
    protected LocalDateTime resolvedTime;

    @Builder
    public ReportReview(Long id, Long userId, Long reviewId, String reason, String adminComment, boolean isResolve,
            LocalDateTime resolvedTime) {
        this.id = id;
        this.userId = userId;
        this.reviewId = reviewId;
        this.reason = reason;
        this.adminComment = adminComment;
        this.isResolve = isResolve;
        this.resolvedTime = resolvedTime;
    }

}