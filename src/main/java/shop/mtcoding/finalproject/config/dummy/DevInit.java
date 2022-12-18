package shop.mtcoding.finalproject.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.enums.DeliveryStateEnum;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewRepository;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.like.Like;
import shop.mtcoding.finalproject.domain.like.LikeRepository;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.menu.MenuRepository;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.order.OrderRepository;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetail;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetailRepository;
import shop.mtcoding.finalproject.domain.reportReview.ReportReview;
import shop.mtcoding.finalproject.domain.reportReview.ReportReviewRepository;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;

@RequiredArgsConstructor
@Configuration
public class DevInit extends DummyEntity {
        @Profile("dev")
        @Bean
        public CommandLineRunner dataSetting(UserRepository userRepository,
                        StoreRepository storeRepository,
                        MenuRepository menuRepository,
                        OrderRepository orderRepository,
                        OrderDetailRepository orderDetailRepository,
                        CustomerReviewRepository customerReviewRepository,
                        CeoReviewRepository ceoReviewRepository,
                        LikeRepository likeRepository,
                        ReportReviewRepository reportReviewRepository) {

                return (args) -> {
                        User ssar = userRepository.save(newUser("ssar", UserEnum.CEO));
                        User cos = userRepository.save(newUser("cos", UserEnum.CEO));
                        User jinsa = userRepository.save(newUser("jinsa", UserEnum.CUSTOMER));
                        User admin = userRepository.save(newUser("admin", UserEnum.ADMIN));
                        User hoho = userRepository.save(newUser("hoho", UserEnum.CEO));
                        User haha = userRepository.save(newUser("haha", UserEnum.CEO));
                        User koko = userRepository.save(newUser("koko", UserEnum.CEO));
                        User kaka = userRepository.save(newUser("kaka", UserEnum.CEO));
                        User hihi = userRepository.save(newUser("hihi", UserEnum.CEO));
                        User kiki = userRepository.save(newUser("kiki", UserEnum.CEO));
                        User papa = userRepository.save(newUser("papa", UserEnum.CEO));
                        User popo = userRepository.save(newUser("popo", UserEnum.CEO));
                        User pepe = userRepository.save(newUser("pepe", UserEnum.CEO));
                        User jina = userRepository.save(newUser("jina", UserEnum.CUSTOMER));
                        User pipi = userRepository.save(newUser("pipi", UserEnum.CEO));
                        Store store1 = storeRepository.save(newStore(ssar, "그린치킨", StoreCategoryEnum.CHICKEN,
                                        "assets/images/store_thumbnail/네네치킨.png"));
                        Store store2 = storeRepository.save(newStore(cos, "레드치킨", StoreCategoryEnum.CHICKEN,
                                        "assets/images/store_thumbnail/희치킨.png"));
                        Store store3 = storeRepository.save(newApplyStore(hoho));
                        Store store4 = storeRepository.save(newStore(haha, "그린피자", StoreCategoryEnum.PIZZA,
                                        "assets/images/store_thumbnail/피자.jpg"));
                        Store store5 = storeRepository.save(newStore(koko, "그린버거", StoreCategoryEnum.BURGER,
                                        "assets/images/store_thumbnail/로고.png"));
                        Store store6 = storeRepository.save(newStore(kaka, "그린분식", StoreCategoryEnum.SCHOOLFOOD,
                                        "assets/images/store_thumbnail/분식.jpg"));
                        Store store7 = storeRepository.save(newStore(hihi, "그린한식", StoreCategoryEnum.KRFOOD,
                                        "assets/images/store_thumbnail/한식.jpg"));
                        Store store8 = storeRepository.save(newStore(kiki, "그린중식", StoreCategoryEnum.CNFOOD,
                                        "assets/images/store_thumbnail/중식.jpg"));
                        Store store9 = storeRepository.save(newStore(papa, "그린일식", StoreCategoryEnum.JPFOOD,
                                        "assets/images/store_thumbnail/일식.jpg"));
                        Store store10 = storeRepository.save(newStore(popo, "그린보쌈", StoreCategoryEnum.BOSSAM,
                                        "assets/images/store_thumbnail/보쌈.jpg"));
                        Store store11 = storeRepository.save(newStore(pepe, "그린죽", StoreCategoryEnum.PORRIDGE,
                                        "assets/images/store_thumbnail/죽.jpg"));
                        Store store12 = storeRepository.save(newSaveStore(pipi));
                        Menu menu1 = menuRepository.save(newMenu(store1, "후라이드"));
                        Menu menu2 = menuRepository.save(newMenu(store1, "간장치킨"));
                        Menu menu3 = menuRepository.save(newMenu(store2, "간장치킨"));
                        Menu menu4 = menuRepository.save(newMenu(store4, "페퍼로니피자"));
                        Menu menu5 = menuRepository.save(newMenu(store5, "치즈버거"));
                        Menu menu6 = menuRepository.save(newMenu(store6, "떡볶이"));
                        Menu menu7 = menuRepository.save(newMenu(store7, "비빔밥"));
                        Menu menu8 = menuRepository.save(newMenu(store8, "짜장면"));
                        Menu menu9 = menuRepository.save(newMenu(store9, "초밥"));
                        Menu menu10 = menuRepository.save(newMenu(store10, "보쌈"));
                        Menu menu11 = menuRepository.save(newMenu(store11, "전복죽"));
                        Order order1 = orderRepository.save(
                                        newOrder(jinsa, store1, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                        Order order2 = orderRepository.save(
                                        newOrder(jinsa, store1, OrderStateEnum.COMPLETE, DeliveryStateEnum.TAKEOUT));
                        Order order3 = orderRepository.save(
                                        newOrder(jinsa, store1, OrderStateEnum.CHECK, DeliveryStateEnum.DELIVERY));
                        Order order4 = orderRepository.save(
                                        newOrder(jinsa, store2, OrderStateEnum.COMPLETE, DeliveryStateEnum.TAKEOUT));
                        Order order5 = orderRepository.save(
                                        newOrder(jinsa, store2, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                        Order order6 = orderRepository.save(
                                        newOrder(jinsa, store4, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                        Order order7 = orderRepository.save(
                                        newOrder(jinsa, store5, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                        Order order8 = orderRepository.save(
                                        newOrder(jinsa, store6, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                        Order order9 = orderRepository.save(
                                        newOrder(jinsa, store7, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                        Order order10 = orderRepository.save(
                                        newOrder(jinsa, store8, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                        Order order11 = orderRepository.save(
                                        newOrder(jinsa, store9, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                        Order order12 = orderRepository.save(
                                        newOrder(jinsa, store10, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                        Order order13 = orderRepository.save(
                                        newOrder(jinsa, store11, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                        OrderDetail orderDetail1 = orderDetailRepository.save(newOrderDetail(order1, menu1));
                        OrderDetail orderDetail2 = orderDetailRepository.save(newOrderDetail(order1, menu2));
                        OrderDetail orderDetail3 = orderDetailRepository.save(newOrderDetail(order2, menu1));
                        OrderDetail orderDetail4 = orderDetailRepository.save(newOrderDetail(order3, menu1));
                        OrderDetail orderDetail5 = orderDetailRepository.save(newOrderDetail(order4, menu3));
                        OrderDetail orderDetail6 = orderDetailRepository.save(newOrderDetail(order5, menu3));
                        OrderDetail orderDetail7 = orderDetailRepository.save(newOrderDetail(order5, menu2));
                        OrderDetail orderDetail8 = orderDetailRepository.save(newOrderDetail(order6, menu4));
                        OrderDetail orderDetail9 = orderDetailRepository.save(newOrderDetail(order7, menu5));
                        OrderDetail orderDetail10 = orderDetailRepository.save(newOrderDetail(order8, menu6));
                        OrderDetail orderDetail11 = orderDetailRepository.save(newOrderDetail(order9, menu7));
                        OrderDetail orderDetail12 = orderDetailRepository.save(newOrderDetail(order10, menu8));
                        OrderDetail orderDetail13 = orderDetailRepository.save(newOrderDetail(order11, menu9));
                        OrderDetail orderDetail14 = orderDetailRepository.save(newOrderDetail(order12, menu10));
                        OrderDetail orderDetail15 = orderDetailRepository.save(newOrderDetail(order13, menu11));
                        CeoReview ceoReview = ceoReviewRepository.save(newCeoReview(store1, order1));
                        CeoReview ceoReview2 = ceoReviewRepository.save(newCeoReview(store1, order2));
                        CustomerReview customerReview = customerReviewRepository
                                        .save(newCustomerReview(jinsa, order1, store1, null, 5.0));
                        CustomerReview customerReview2 = customerReviewRepository
                                        .save(newCustomerReview(jinsa, order4, store2, null, 4.0));
                        CustomerReview customerReview3 = customerReviewRepository
                                        .save(newCustomerReview(jinsa, order6, store4, null, 5.0));
                        CustomerReview customerReview4 = customerReviewRepository
                                        .save(newCustomerReview(jinsa, order2, store1, ceoReview, 3.0));
                        Like like1 = likeRepository.save(newLike(jinsa, store2));
                        Like like2 = likeRepository.save(newLike(jinsa, store1));
                        Like like3 = likeRepository.save(newLike(jinsa, store4));
                        ReportReview reportReview1 = reportReviewRepository
                                        .save(newReportReview(ssar, customerReview, ceoReview, null));
                        ReportReview reportReview2 = reportReviewRepository
                                        .save(newReportReview(jinsa, customerReview2, ceoReview2, null));

                };
        }
}
