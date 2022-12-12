package shop.mtcoding.finalproject.dto.reportReview;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.reportReview.ReportReview;

public class ReportReviewRespDto {

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
