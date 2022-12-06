package shop.mtcoding.finalproject.config.dummy;

import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.menu.MenuRepository;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.order.OrderRepository;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;

@RequiredArgsConstructor
@Configuration
public class DevInit extends DummyEntity {
    @Profile("dev")
    @Bean
    public CommandLineRunner dataSetting(UserRepository userRepository, StoreRepository storeRepository,
            MenuRepository menuRepository,
            OrderRepository orderRepository, CustomerReviewRepository customerReviewRepository) {

        return (args) -> {
            User ssar = userRepository.save(newUser("ssar"));
            User jinsa = userRepository.save(newUser("jinsa"));
            Store store = storeRepository.save(newStore(ssar));
            Menu menu = menuRepository.save(newMenu(store));
            Order order = orderRepository.save(newOrder(jinsa, store));
            CustomerReview customerReview = customerReviewRepository.save(newCustomerReview(jinsa, order, null));
        };
    }
}
