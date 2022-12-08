package shop.mtcoding.finalproject.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewRepository;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
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
            ReportReviewRepository reportReviewRepository) {

        return (args) -> {
            User ssar = userRepository.save(newUser("ssar", UserEnum.CEO));
            User jinsa = userRepository.save(newUser("jinsa", UserEnum.CUSTOMER));
            Store store = storeRepository.save(newStore(ssar));
            Menu menu = menuRepository.save(newMenu(store));
            Order order1 = orderRepository.save(newOrder(jinsa, store));
            Order order2 = orderRepository.save(newOrder(jinsa, store));
            OrderDetail orderDetail1 = orderDetailRepository.save(newOrderDetail(order1, menu));
            OrderDetail orderDetail2 = orderDetailRepository.save(newOrderDetail(order1, menu));
            CeoReview ceoReview = ceoReviewRepository.save(newCeoReview(store, order1));
            CustomerReview customerReview = customerReviewRepository
                    .save(newCustomerReview(jinsa, order1, store, ceoReview));
            CustomerReview customerReview2 = customerReviewRepository
                    .save(newCustomerReview(jinsa, order1, store, null));
            ReportReview reportReview1 = reportReviewRepository.save(newReportReview(ssar, customerReview, ceoReview));
            ReportReview reportReview2 = reportReviewRepository.save(newReportReview(ssar, customerReview, ceoReview));
        };
    }
}
