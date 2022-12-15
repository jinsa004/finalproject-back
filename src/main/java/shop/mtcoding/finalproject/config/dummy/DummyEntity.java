package shop.mtcoding.finalproject.config.dummy;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import shop.mtcoding.finalproject.config.enums.DeliveryStateEnum;
import shop.mtcoding.finalproject.config.enums.MenuCategoryEnum;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.config.enums.ReportReasonEnum;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.like.Like;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetail;
import shop.mtcoding.finalproject.domain.reportReview.ReportReview;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;

public class DummyEntity {

    protected User newUser(String username, UserEnum role) {
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encPassword = passwordEncoder.encode("1234");
        User user = User.builder()
                .username(username)
                .password(encPassword)
                .nickname(username + "님")
                .phone("01011112222")
                .photo(null)
                .address("부산시 진구 서면 17번 길")
                .role(role)
                .isActive(true)
                .build();
        return user;
    }

    protected Store newStore(User user, String name, StoreCategoryEnum category) {
        Store store = Store.builder()
                .category(category)
                .name(name)
                .phone("01011112222")
                .minAmount(10000)
                .thumbnail("assets/images/category/간장치킨.jpg")
                .ceoName("cos")
                .businessNumber("112233")
                .businessAddress("부산시 진구 서면 17번 길")
                .openTime("2")
                .closeTime("4")
                .deliveryHour("30")
                .deliveryCost(2000)
                .intro("그린 치킨입니다.")
                .notice("리뷰 이벤트중입니다.")
                .isOpend(true)
                .isAccept(true)
                .user(user)
                .build();
        return store;
    }

    protected Store newApplyStore(User user) {
        Store store = Store.builder()
                .category(null)
                .name(null)
                .phone(null)
                .minAmount(0)
                .thumbnail(null)
                .ceoName("hoho")
                .businessNumber("112233")
                .businessAddress("부산시 진구 서면 17번 길")
                .openTime(null)
                .closeTime(null)
                .deliveryHour(null)
                .deliveryCost(0)
                .intro(null)
                .notice(null)
                .isOpend(false)
                .isAccept(false)
                .user(user)
                .build();
        return store;
    }

    protected Store newSaveStore(User user) {
        Store store = Store.builder()
                .category(null)
                .name(null)
                .phone(null)
                .minAmount(0)
                .thumbnail(null)
                .ceoName(user.getUsername())
                .businessNumber("112233")
                .businessAddress("부산시 진구 서면 17번 길")
                .openTime(null)
                .closeTime(null)
                .deliveryHour(null)
                .deliveryCost(0)
                .intro(null)
                .notice(null)
                .isOpend(false)
                .isAccept(true)
                .user(user)
                .build();
        return store;
    }

    protected Menu newMenu(Store store, String name) {
        Menu menu = Menu.builder()
                .name(name)
                .thumbnail(null)
                .intro("깨끗한 기름으로 튀겼습니다.")
                .price(18000)
                .category(MenuCategoryEnum.MAIN)
                .isClosure(false)
                .store(store)
                .build();
        return menu;
    }

    protected Order newOrder(User user, Store store, OrderStateEnum orderStateEnum,
            DeliveryStateEnum deliveryStateEnum) {
        Order order = Order.builder()
                .comment("젓가락 빼주세요")
                .state(orderStateEnum)
                .reason(null)
                .user(user)
                .store(store)
                .deliveryStateEnum(deliveryStateEnum)
                .isClosure(false)
                .deliveryTime(store.getDeliveryHour())
                .build();
        return order;
    }

    protected OrderDetail newOrderDetail(Order order, Menu menu) {
        OrderDetail orderDetail = OrderDetail.builder()
                .count(1)
                .order(order)
                .menu(menu)
                .build();
        return orderDetail;
    }

    protected OrderDetail newOrderDetail(Order order, Menu menu, int count) {
        OrderDetail orderDetail = OrderDetail.builder()
                .count(count)
                .order(order)
                .menu(menu)
                .build();
        return orderDetail;
    }

    protected CustomerReview newCustomerReview(User user, Order order, Store store, CeoReview ceoReview,
            Double starPoint) {

        CustomerReview customerReview = CustomerReview.builder()
                .content("맛있어요")
                .starPoint(starPoint)
                .photo(null)
                .isClosure(false)
                .ceoReview(ceoReview)
                .user(user)
                .order(order)
                .store(store)
                .build();
        return customerReview;
    }

    protected CeoReview newCeoReview(Store store, Order order) {
        CeoReview ceoReview = CeoReview.builder()
                .content("고 마워 요")
                .store(store)
                .order(order)
                .build();
        return ceoReview;
    }

    protected Like newLike(User user, Store store) {
        Like like = Like.builder()
                .user(user)
                .store(store)
                .build();
        return like;
    }

    protected ReportReview newReportReview(User user, CustomerReview customerReview, CeoReview ceoReview) {
        ReportReview reportReview = ReportReview.builder()
                .user(user)
                .customerReview(customerReview)
                .ceoReview(ceoReview)
                .reason(ReportReasonEnum.HONOR)
                .build();
        return reportReview;
    }
}
