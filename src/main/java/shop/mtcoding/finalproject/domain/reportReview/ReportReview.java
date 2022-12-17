package shop.mtcoding.finalproject.domain.reportReview;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.finalproject.config.enums.ReportReasonEnum;
import shop.mtcoding.finalproject.domain.AudingTime;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewReqDto.ResolveReportReviewReqDto;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Table(name = "report_reviews")
@Entity
public class ReportReview extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private User user;

    @OneToOne(fetch = FetchType.LAZY)
    private CustomerReview customerReview;

    @OneToOne(fetch = FetchType.LAZY)
    private CeoReview ceoReview;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ReportReasonEnum reason;

    @Column(nullable = true)
    private String adminComment;

    @Column(nullable = true)
    private boolean isResolve;

    @Column(nullable = true)
    private LocalDateTime resolvedTime;

    @Builder
    public ReportReview(Long id, User user, CustomerReview customerReview, CeoReview ceoReview, ReportReasonEnum reason,
            String adminComment, boolean isResolve, LocalDateTime resolvedTime, LocalDateTime createdAt) {
        this.id = id;
        this.user = user;
        this.customerReview = customerReview;
        this.ceoReview = ceoReview;
        this.reason = reason;
        this.adminComment = adminComment;
        this.isResolve = isResolve;
        this.resolvedTime = resolvedTime;
        this.createdAt = createdAt;
    }

    public void resolve(ResolveReportReviewReqDto resolveReportReviewReqDto) {
        this.adminComment = resolveReportReviewReqDto.getAdminComment();
        this.resolvedTime = createdAt;
        this.isResolve = true;
    }

}