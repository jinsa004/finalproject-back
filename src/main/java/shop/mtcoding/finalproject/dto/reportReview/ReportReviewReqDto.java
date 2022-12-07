package shop.mtcoding.finalproject.dto.reportReview;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.reportReview.ReportReview;
import shop.mtcoding.finalproject.util.CustomEnumUtil;

public class ReportReviewReqDto {

    @Getter
    @Setter
    public static class InsertReportReviewReqDto {

        private Long userId;
        private Long reviewId;
        private String reason;

        public ReportReview toEntity() {
            return ReportReview.builder()
                    .userId(userId)
                    .reviewId(reviewId)
                    .reason(CustomEnumUtil.toReportReasonEnumFormat(reason))
                    .isResolve(false)
                    .build();
        }

    }
}
