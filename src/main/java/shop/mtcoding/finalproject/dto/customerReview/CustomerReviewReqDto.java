package shop.mtcoding.finalproject.dto.customerReview;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.util.CustomBase64ConvertUtil;

public class CustomerReviewReqDto {
    @Getter
    @Setter
    public static class InsertCustomerReviewReqDto {
        private String content;
        private Double starPoint;
        private String photo;

        public CustomerReview toEntity(User user, Store store, Order order) {
            return CustomerReview.builder()
                    .content(content)
                    .starPoint(starPoint)
                    .photo(CustomBase64ConvertUtil.convertToByte(photo))
                    .order(order)
                    .user(user)
                    .store(store)
                    .ceoReview(null)
                    .isClosure(false)
                    .build();
        }
    }
}
