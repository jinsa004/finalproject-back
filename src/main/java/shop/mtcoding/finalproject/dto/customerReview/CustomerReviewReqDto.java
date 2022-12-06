package shop.mtcoding.finalproject.dto.customerReview;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;

public class CustomerReviewReqDto {
    @Getter
    @Setter
    public static class InsertCustomerReviewReqDto {
        private String content;
        private int starPoint;
        private String photo;

        public CustomerReview toEntity(Store store, User user) {
            return CustomerReview.builder()
                    .content(content)
                    .starPoint(starPoint)
                    .photo(photo)
                    .user(user)
                    .store(store)
                    .isClosure(false)
                    .build();
        }
    }
}
