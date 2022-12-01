package shop.mtcoding.finalproject.config.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.user.User;

public class DummyEntity {

    protected User newUser(String username) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        User user = User.builder()
                .username(username)
                .password(encPassword)
                .nickname(username + "님")
                .phone("01011112222")
                .photo(null)
                .address("부산시 진구 서면")
                .role(username.equals("admin") ? UserEnum.ADMIN : UserEnum.CUSTOMER)
                .isActive(true)
                .build();
        return user;
    }

    protected CustomerReview newCustomerReview(User user, Order order) {
        CustomerReview customerReview = CustomerReview.builder()
                .content("맛있어요")
                .starPoint(5)
                .photo(null)
                .isClosure(false)
                .user(user)
                .order(order)
                .build();
        return customerReview;
    }
}
