package shop.mtcoding.finalproject.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.enums.DeliveryStateEnum;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
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
                        Store store1 = storeRepository.save(newStore(ssar));
                        Store store2 = storeRepository.save(newStore(cos));
                        Store store3 = storeRepository.save(newApplyStore(hoho));
                        Menu menu1 = menuRepository.save(newMenu(store1, "후라이드"));
                        Menu menu2 = menuRepository.save(newMenu(store2, "간장치킨"));
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
                        OrderDetail orderDetail1 = orderDetailRepository.save(newOrderDetail(order1, menu1));
                        OrderDetail orderDetail2 = orderDetailRepository.save(newOrderDetail(order1, menu1));
                        OrderDetail orderDetail3 = orderDetailRepository.save(newOrderDetail(order2, menu1));
                        OrderDetail orderDetail4 = orderDetailRepository.save(newOrderDetail(order3, menu1));
                        OrderDetail orderDetail5 = orderDetailRepository.save(newOrderDetail(order4, menu2));
                        OrderDetail orderDetail6 = orderDetailRepository.save(newOrderDetail(order5, menu2));
                        OrderDetail orderDetail7 = orderDetailRepository.save(newOrderDetail(order5, menu2));
                        CeoReview ceoReview = ceoReviewRepository.save(newCeoReview(store1, order1));
                        CustomerReview customerReview = customerReviewRepository
                                        .save(newCustomerReview(jinsa, order1, store1, ceoReview, 5.0));
                        CustomerReview customerReview2 = customerReviewRepository
                                        .save(newCustomerReview(jinsa, order2, store2, null, 4.0));
                        Like like1 = likeRepository.save(newLike(jinsa, store2));
                        Like like2 = likeRepository.save(newLike(jinsa, store1));
                        ReportReview reportReview1 = reportReviewRepository
                                        .save(newReportReview(ssar, customerReview, ceoReview));
                        ReportReview reportReview2 = reportReviewRepository
                                        .save(newReportReview(jinsa, customerReview2, ceoReview));
                };
        }
}
