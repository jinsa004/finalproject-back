package shop.mtcoding.finalproject.dto.customerReview;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;

public class CustomerReviewRespDto {
    @Getter
    @Setter
    public static class InsertCustomerReviewRespDto {
        private String content;
        private int starPoint;
        private String photo;

        public InsertCustomerReviewRespDto(CustomerReview customerReview) {
            this.content = customerReview.getContent();
            this.photo = customerReview.getPhoto();
            this.starPoint = customerReview.getStarPoint();
        }

    }
}
