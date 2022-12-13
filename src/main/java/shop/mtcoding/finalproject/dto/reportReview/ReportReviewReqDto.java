package shop.mtcoding.finalproject.dto.reportReview;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.reportReview.ReportReview;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.util.CustomEnumUtil;

public class ReportReviewReqDto {

    @Getter
    @Setter
    public static class ResolveReportReviewReqDto {
        private String adminComment;
    }

    @Getter
    @Setter
    public static class InsertReportReviewReqDto {

        private Long userId;
        private Long reviewId;
        private String userKind; // 일반 회원, 사업자 회원
        private String reason;

        public ReportReview toEntity(User user, CustomerReview customerReview, CeoReview ceoReview) {
            return ReportReview.builder()
                    .user(user)
                    .customerReview(customerReview)
                    .ceoReview(ceoReview)
                    .reason(CustomEnumUtil.toReportReasonEnumFormat(reason))
                    .build();
        }
    }
}
