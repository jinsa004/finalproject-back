package shop.mtcoding.finalproject.dto.customerReview;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.order.Order;

public class CustomerReviewReqDto {
    @Getter
    @Setter
    public static class InsertCustomerReviewReqDto {
        private String content;
        private int starPoint;
        private String photo;

        public CustomerReview toEntity(Order order) {
            return CustomerReview.builder()
                    .content(content)
                    .starPoint(starPoint)
                    .photo(photo)
                    .user(order.getUser())
                    .order(order)
                    .isClosure(false)
                    .build();
        }
    }
}
