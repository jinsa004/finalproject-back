package shop.mtcoding.finalproject.dto.ceoReview;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.util.CustomDateUtil;

public class CeoReviewRespDto {

    @Getter
    @Setter
    public static class ShowReviewRespDto {

        private Long id;
        private int starPoint;
        private String orderCreatedAt;
        private boolean isClosure;
        private String customerContent;
        private String ceoContent;

        public ShowReviewRespDto(CustomerReview customerReview) {
            this.id = customerReview.getId();
            this.starPoint = customerReview.getStarPoint();
            this.orderCreatedAt = CustomDateUtil.toStringFormat(customerReview.getOrder().getCreatedAt());
            this.isClosure = customerReview.isClosure();
            this.customerContent = customerReview.getContent();
            if (customerReview.getCeoReview() == null) {
                this.ceoContent = "";
            } else {
                this.ceoContent = customerReview.getCeoReview().getContent();
            }
        }
    }

    @Getter
    @Setter
    public static class SaveCeoReviewDto {
        private String content;

        public SaveCeoReviewDto(CeoReview ceoReview) {
            this.content = ceoReview.getContent();
        }
    }
}
