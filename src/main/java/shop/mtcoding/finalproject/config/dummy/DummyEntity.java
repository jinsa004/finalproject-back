package shop.mtcoding.finalproject.config.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.mtcoding.finalproject.config.enums.MenuCategoryEnum;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.store.Store;
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

    protected Store newStore(User user) {
        Store store = Store.builder()
                .name("그린치킨")
                .phone("01011112222")
                .thumbnail(null)
                .ceoName("cos")
                .businessNumber("112233")
                .businessAddress("부산시 진구 서면 17번 길")
                .openTime("2")
                .closeTime("4")
                .deliveryHour("30분")
                .deliveryCost("2000원")
                .intro("그린 치킨입니다.")
                .notice("리뷰 이벤트중입니다.")
                .isOpend(true)
                .isAccept(true)
                .user(user)
                .build();
        return store;
    }

    protected Menu newMenu(Store store) {
        Menu menu = Menu.builder()
                .name("후라이드치킨")
                .thumbnail(null)
                .intro("깨끗한 기름으로 튀겼습니다.")
                .price("18,000원")
                .category(MenuCategoryEnum.MAIN)
                .isClosure(false)
                .store(store)
                .build();
        return menu;
    }

    protected Order newOrder(User user, Store store) {
        Order order = Order.builder()
                .paymentId(1)
                .state(OrderStateEnum.COMPLETE)
                .reason(null)
                .user(user)
                .store(store)
                .isClosure(false)
                .build();
        return order;
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
