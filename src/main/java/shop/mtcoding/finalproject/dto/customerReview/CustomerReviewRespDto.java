package shop.mtcoding.finalproject.dto.customerReview;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.util.CustomDateUtil;

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

    @Setter
    @Getter
    public static class CustomerReviewListRespDto {
        private UserDto user;
        private List<CustomerReviewDto> customerReviews;

        public CustomerReviewListRespDto(User user, List<CustomerReview> customerReviews) {
            this.user = new UserDto(user);
            this.customerReviews = customerReviews.stream()
                    .map((customerReview) -> new CustomerReviewDto(customerReview))
                    .collect(Collectors.toList());
        }

        @Setter
        @Getter
        public class UserDto {
            private Long id;
            private String nickname;

            public UserDto(User user) {
                this.id = user.getId();
                this.nickname = user.getNickname();
            }
        }

        @Setter
        @Getter
        public class CustomerReviewDto {
            private Long id;
            private String content;
            private String photo;
            private int starPoint;
            private String comment;
            private String createAt;

            public CustomerReviewDto(CustomerReview customerReview) {
                this.id = customerReview.getId();
                this.content = customerReview.getContent();
                this.photo = customerReview.getPhoto();
                this.starPoint = customerReview.getStarPoint();
                this.comment = customerReview.getCeoReview().getContent();
                this.createAt = CustomDateUtil.toStringFormat(customerReview.getCreatedAt());
            }

        }

    }
}
