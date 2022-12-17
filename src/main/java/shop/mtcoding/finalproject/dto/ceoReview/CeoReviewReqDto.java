package shop.mtcoding.finalproject.dto.ceoReview;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;

public class CeoReviewReqDto {

    @Getter
    @Setter
    public static class InsertCeoReviewReqDto {

        private String content;

        public CeoReview toEntity(CustomerReview customerReview) {
            return CeoReview.builder()
                    .content(content)
                    .store(customerReview.getStore())
                    .order(customerReview.getOrder())
                    .build();
        }
    }
}
