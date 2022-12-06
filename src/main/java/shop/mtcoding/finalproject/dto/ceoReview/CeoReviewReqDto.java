package shop.mtcoding.finalproject.dto.ceoReview;

import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;

public class CeoReviewReqDto {

    public class UpdateCeoReviewReqDto {

        private String content;

        public CeoReview toEntity(CeoReview ceoReview) {
            return CeoReview.builder()
                    .id(ceoReview.getId())
                    .content(content)
                    .store(ceoReview.getStore())
                    .order(ceoReview.getOrder())
                    .build();
        }
    }
}
