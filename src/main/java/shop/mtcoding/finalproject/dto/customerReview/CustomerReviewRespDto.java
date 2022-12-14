package shop.mtcoding.finalproject.dto.customerReview;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.customerReview.CustomerMenuInterface;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewInterface;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.util.CustomBase64ConvertUtil;
import shop.mtcoding.finalproject.util.CustomDateUtil;

public class CustomerReviewRespDto {

    @Getter
    @Setter
    public static class StoreReviewListRespDto {// 가게 상세보기 -> 가게 리뷰탭 리뷰 목록보기
        private List<CustomerReviewDto> customerReviewDtoList = new ArrayList<>();

        public StoreReviewListRespDto(List<CustomerReviewInterface> customerReviewDtos,
                List<CustomerMenuInterface> customerMenuDtos) {

            if (customerReviewDtos.size() == 0) {
                new CustomApiException("리뷰가 없습니다.", HttpStatus.BAD_REQUEST);
                return;
            }

            for (CustomerReviewInterface customerReviewDto : customerReviewDtos) {
                List<CustomerMenuInterface> tempReviews = new ArrayList<>();
                for (CustomerMenuInterface customerMenuDto : customerMenuDtos) {
                    if (customerMenuDto.getOrderId() == customerReviewDto.getOrderId()) {
                        tempReviews.add(customerMenuDto);
                    }
                }
                customerReviewDtoList.add(new CustomerReviewDto(customerReviewDto, tempReviews));
            }
        }

        @Getter
        @Setter
        public class CustomerReviewDto {
            private Long orderId;
            private String nickname;
            private String uPhoto;
            private String crPhoto;
            private String content;
            private Double starPoint;
            private String comment;

            private List<CustomerMenuDto> customerMenuDtos = new ArrayList<>();

            public CustomerReviewDto(CustomerReviewInterface customerReviewDto, List<CustomerMenuInterface> crm) {
                this.orderId = customerReviewDto.getOrderId();
                this.nickname = customerReviewDto.getNickname();
                this.uPhoto = CustomBase64ConvertUtil.convertToString(customerReviewDto.getUPhoto());
                this.crPhoto = customerReviewDto.getCrPhoto();
                this.content = customerReviewDto.getContent();
                this.starPoint = customerReviewDto.getStarPoint();
                this.comment = customerReviewDto.getComment();
                this.customerMenuDtos = crm.stream().map(CustomerMenuDto::new).collect(Collectors.toList());
            }

        }

        @Getter
        @Setter
        public class CustomerMenuDto {
            private Long orderId;
            private String menuName;

            public CustomerMenuDto(CustomerMenuInterface customerMenuDto) {
                this.orderId = customerMenuDto.getOrderId();
                this.menuName = customerMenuDto.getMenuName();
            }

        }
    }

    @Getter
    @Setter
    public static class InsertCustomerReviewRespDto { // 고객 리뷰 등록하기 기능
        private String content;
        private Double starPoint;
        private String photo;

        public InsertCustomerReviewRespDto(CustomerReview customerReview) {
            this.content = customerReview.getContent();
            this.photo = CustomBase64ConvertUtil.convertToString(customerReview.getPhoto());
            this.starPoint = customerReview.getStarPoint();
        }

    }

    @Getter
    @Setter
    public static class CustomerReviewListRespDto {
        private UserDto user;
        private OrderDto order;
        private List<CustomerReviewDto> customerReviews;

        public CustomerReviewListRespDto(List<CustomerReview> customerReviews, Order order,
                User user) {
            this.customerReviews = customerReviews.stream()
                    .map((customerReview) -> new CustomerReviewDto(customerReview))
                    .collect(Collectors.toList());
            this.order = new OrderDto(order);
            this.user = new UserDto(user);
        }

        @Getter
        @Setter
        public class UserDto {
            private Long id;
            private String nickname;

            public UserDto(User user) {
                this.id = user.getId();
                this.nickname = user.getNickname();
            }

        }

        @Getter
        @Setter
        public class OrderDto {
            private Long id;
            private String storeName; // 가게이름

            public OrderDto(Order order) {
                this.id = order.getId();
                this.storeName = order.getStore().getName();
            }

        }

        @Getter
        @Setter
        public class CustomerReviewDto {
            private String content;
            private String photo;
            private Double starPoint;
            private String createdAt;
            private String comment;
            private String commentCreatedAt;

            public CustomerReviewDto(CustomerReview customerReview) {
                this.content = customerReview.getContent();
                this.photo = CustomBase64ConvertUtil.convertToString(customerReview.getPhoto());
                this.starPoint = customerReview.getStarPoint();
                this.createdAt = CustomDateUtil.toStringFormat(customerReview.getCreatedAt());
                this.comment = customerReview.getCeoReview().getContent();
                this.commentCreatedAt = CustomDateUtil.toStringFormat(customerReview.getCeoReview().getCreatedAt());
            }

        }

    }
}
