package shop.mtcoding.finalproject.domain.order;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import shop.mtcoding.finalproject.config.dummy.DummyEntity;
import shop.mtcoding.finalproject.config.enums.DeliveryStateEnum;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewRepository;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.menu.MenuRepository;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetail;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetailRepository;
import shop.mtcoding.finalproject.domain.reportReview.ReportReview;
import shop.mtcoding.finalproject.domain.reportReview.ReportReviewRepository;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.order.OrderReqDto.FindStatsReqDto;
import shop.mtcoding.finalproject.dto.order.OrderStatsRespDto;

@Import(OrderRepositoryQuery.class)
@ActiveProfiles("test")
@DataJpaTest
public class OrderRepositoryQueryTest extends DummyEntity {
        @Autowired
        private EntityManager em;

        @Autowired
        private UserRepository userRepository;

        @Autowired
        private StoreRepository storeRepository;

        @Autowired
        private MenuRepository menuRepository;

        @Autowired
        private CustomerReviewRepository customerReviewRepository;

        @Autowired
        private CeoReviewRepository ceoReviewRepository;

        @Autowired
        private OrderRepository orderRepository;

        @Autowired
        private OrderDetailRepository orderDetailRepository;

        @Autowired
        private ReportReviewRepository reportReviewRepository;

        @Autowired
        private OrderRepositoryQuery orderRepositoryQuery;

        @BeforeEach
        public void setUp() {
                autoincrement_reset();
                dummy_init();
        }

        @Test
        public void 조회_test() throws Exception {
                // given
                FindStatsReqDto findStatsReqDto = new FindStatsReqDto();
                findStatsReqDto.setStoreId(1L);
                findStatsReqDto.setStartTime("2022-12-14");
                findStatsReqDto.setEndTime("2022-12-14");

                // when
                OrderStatsRespDto orderStatsRespDto = orderRepositoryQuery.findAllOrderStatsByStoreId(findStatsReqDto);

                // then
                Assertions.assertThat(orderStatsRespDto.getOrderCount()).isEqualTo("3");

                // Assertions.assertThat(reportReviewRespDtos.size()).isEqualTo(2);
        }

        private void autoincrement_reset() {
                this.em
                                .createNativeQuery("ALTER TABLE users ALTER COLUMN `id` RESTART WITH 1")
                                .executeUpdate();
                this.em
                                .createNativeQuery("ALTER TABLE stores ALTER COLUMN `id` RESTART WITH 1")
                                .executeUpdate();
                this.em
                                .createNativeQuery("ALTER TABLE menus ALTER COLUMN `id` RESTART WITH 1")
                                .executeUpdate();
                this.em
                                .createNativeQuery("ALTER TABLE orders ALTER COLUMN `id` RESTART WITH 1")
                                .executeUpdate();
                this.em
                                .createNativeQuery("ALTER TABLE order_details ALTER COLUMN `id` RESTART WITH 1")
                                .executeUpdate();
                this.em
                                .createNativeQuery("ALTER TABLE ceo_reviews ALTER COLUMN `id` RESTART WITH 1")
                                .executeUpdate();
                this.em
                                .createNativeQuery("ALTER TABLE customer_reviews ALTER COLUMN `id` RESTART WITH 1")
                                .executeUpdate();
                this.em
                                .createNativeQuery("ALTER TABLE report_reviews ALTER COLUMN `id` RESTART WITH 1")
                                .executeUpdate();
                this.em
                                .createNativeQuery("ALTER TABLE likes ALTER COLUMN `id` RESTART WITH 1")
                                .executeUpdate();

                this.em
                                .createNativeQuery("ALTER TABLE payments ALTER COLUMN `id` RESTART WITH 1")
                                .executeUpdate();

        }

        private void dummy_init() {
                User ssar = userRepository.save(newUser("ssar", UserEnum.CEO));
                User cos = userRepository.save(newUser("cos", UserEnum.CEO));
                User jinsa = userRepository.save(newUser("jinsa", UserEnum.CUSTOMER));
                Store store1 = storeRepository.save(newStore(ssar, "그린치킨", StoreCategoryEnum.CHICKEN));
                Store store2 = storeRepository.save(newStore(cos, "그린치킨", StoreCategoryEnum.CHICKEN));
                Menu menu1 = menuRepository.save(newMenu(store1, "후라이드치킨"));
                Menu menu2 = menuRepository.save(newMenu(store2, "간장치킨"));
                Order order1 = orderRepository.save(newOrder(jinsa, store1, DeliveryStateEnum.DELIVERY));
                Order order2 = orderRepository.save(newOrder(jinsa, store1, DeliveryStateEnum.TAKEOUT));
                Order order3 = orderRepository.save(newOrder(jinsa, store1, DeliveryStateEnum.DELIVERY));
                Order order4 = orderRepository.save(newOrder(jinsa, store2, DeliveryStateEnum.TAKEOUT));
                Order order5 = orderRepository.save(newOrder(jinsa, store2, DeliveryStateEnum.DELIVERY));
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
                                .save(newCustomerReview(jinsa, order2, store1, null, 4.0));
                ReportReview reportReview1 = reportReviewRepository
                                .save(newReportReview(ssar, customerReview, ceoReview));
                ReportReview reportReview2 = reportReviewRepository
                                .save(newReportReview(ssar, customerReview, ceoReview));
        }
}
