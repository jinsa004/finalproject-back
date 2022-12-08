package shop.mtcoding.finalproject.domain.reportReview;

import java.util.List;

import javax.persistence.EntityManager;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

import shop.mtcoding.finalproject.config.dummy.DummyEntity;
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
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;

@Import(ReportReviewRepositoryQuery.class)
@ActiveProfiles("test")
@DataJpaTest
public class ReportReviewRepositoryQueryTest extends DummyEntity {

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
    private ReportReviewRepositoryQuery reportReviewRepositoryQuery;

    @BeforeEach
    public void setUp() {
        autoincrement_reset();
        dummy_init();
    }

    @Test
    public void 조회_test() throws Exception {
        // given
        Long storeId = 1L;

        // when
        List<ReportReviewRespDto> reportReviewRespDtos = reportReviewRepositoryQuery.findAllByStoreId(storeId);

        // then
        Assertions.assertThat(reportReviewRespDtos.size()).isEqualTo(2);
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
    }
}
