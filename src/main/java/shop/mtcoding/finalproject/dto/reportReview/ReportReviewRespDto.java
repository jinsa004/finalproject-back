package shop.mtcoding.finalproject.dto.reportReview;

import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.reportReview.ReportReview;
import shop.mtcoding.finalproject.util.CustomDateUtil;

public class ReportReviewRespDto {

    @Getter
    @Setter
    public static class ResolveRefuseReportReviewRespDto {
        private String adminComment;
        private boolean isResolve;
        private String resolvedTime;
        private boolean isAccept;

        public ResolveRefuseReportReviewRespDto(ReportReview reportReviewPS) {
            this.adminComment = reportReviewPS.getAdminComment();
            this.isResolve = reportReviewPS.isResolve();
            this.resolvedTime = CustomDateUtil.toStringFormat(reportReviewPS.getCreatedAt());
            this.isAccept = reportReviewPS.isAccept();
        }
    }

    @Getter
    @Setter
    public static class ResolveAcceptReportReviewRespDto {
        private String adminComment;
        private boolean isResolve;
        private String resolvedTime;
        private boolean isAccept;

        public ResolveAcceptReportReviewRespDto(ReportReview reportReviewPS) {
            this.adminComment = reportReviewPS.getAdminComment();
            this.isResolve = reportReviewPS.isResolve();
            this.resolvedTime = CustomDateUtil.toStringFormat(reportReviewPS.getCreatedAt());
            this.isAccept = reportReviewPS.isAccept();
        }
    }

    @Getter
    @Setter
    public static class DetailReportReviewRespDto {
        private BigInteger reportReviewId;
        private String nickname;
        private String content;
        private String reason;
        private String storeName;
        private String comment;

        public DetailReportReviewRespDto(ReportCustomerInfoRespDto reportCustomerReviewRespDto,
                ReportCeoInfoRespDto reportCeoReviewRespDto) {
            this.reportReviewId = reportCustomerReviewRespDto.getId();
            this.nickname = reportCustomerReviewRespDto.getNickname();
            this.content = reportCustomerReviewRespDto.getContent();
            this.reason = reportCustomerReviewRespDto.getReason();
            this.storeName = reportCeoReviewRespDto.getStoreName();
            this.comment = reportCeoReviewRespDto.getComment();
        }

    }

    @Getter
    @Setter
    public static class ReportReviewListRespDto {
        private List<ReportReviewDto> reportReviews;

        public ReportReviewListRespDto(List<ReportReview> reportReviews) {
            this.reportReviews = reportReviews.stream().map((reportReview) -> new ReportReviewDto(reportReview))
                    .collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class ReportReviewDto {
            private Long id;
            private String role;
            private String username;
            private String reason;
            private Boolean isResolved;

            public ReportReviewDto(ReportReview reportReview) {
                this.id = reportReview.getId();
                this.role = reportReview.getUser().getRole().getValue();
                this.username = reportReview.getUser().getUsername();
                this.reason = reportReview.getReason().getReason();
                this.isResolved = reportReview.isResolve();
            }

        }

    }

}
