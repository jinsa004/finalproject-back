package shop.mtcoding.finalproject.dto.reportReview;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.reportReview.ReportReview;

public class ReportReviewRespDto {

    @Getter
    @Setter
    public static class InsertReportReviewReqDto {

        private String reason;

        public InsertReportReviewReqDto(ReportReview reportReview) {
            this.reason = reportReview.getReason().getReason();
        }
    }
}
