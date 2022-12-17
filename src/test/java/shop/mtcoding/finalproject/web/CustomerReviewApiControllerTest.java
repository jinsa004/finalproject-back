package shop.mtcoding.finalproject.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import javax.persistence.EntityManager;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.finalproject.config.dummy.DummyEntity;
import shop.mtcoding.finalproject.config.enums.DeliveryStateEnum;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewRepository;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.like.Like;
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
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewReqDto.InsertCustomerReviewReqDto;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class CustomerReviewApiControllerTest extends DummyEntity {

        private static final String APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";
        private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded; charset=utf-8";

        @Autowired
        private MockMvc mvc;

        @Autowired
        private ObjectMapper om;

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

        @BeforeEach
        public void setUp() {
                User ssar = userRepository.save(newUser("ssar", UserEnum.CEO));
                User cos = userRepository.save(newUser("cos", UserEnum.CEO));
                User jinsa = userRepository.save(newUser("jinsa", UserEnum.CUSTOMER));
                User admin = userRepository.save(newUser("admin", UserEnum.ADMIN));
                User hoho = userRepository.save(newUser("hoho", UserEnum.CEO));
                User haha = userRepository.save(newUser("haha", UserEnum.CEO));
                Store store1 = storeRepository.save(newStore(ssar, "그린치킨", StoreCategoryEnum.CHICKEN,
                                "assets/images/store_thumbnail/후라이드치킨.jpg"));
                Store store2 = storeRepository.save(newStore(cos, "그린치킨", StoreCategoryEnum.CHICKEN,
                                "assets/images/store_thumbnail/간장치킨.jpg"));
                Store store3 = storeRepository.save(newApplyStore(hoho));
                Store store4 = storeRepository.save(newStore(haha, "그린피자", StoreCategoryEnum.PIZZA,
                                "assets/images/store_thumbnail/피자.jpg"));
                Menu menu1 = menuRepository.save(newMenu(store1, "후라이드"));
                Menu menu2 = menuRepository.save(newMenu(store2, "간장치킨"));
                Menu menu3 = menuRepository.save(newMenu(store2, "간장치킨"));
                Order order1 = orderRepository
                                .save(newOrder(jinsa, store1, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order2 = orderRepository
                                .save(newOrder(jinsa, store1, OrderStateEnum.COMPLETE, DeliveryStateEnum.TAKEOUT));
                Order order3 = orderRepository
                                .save(newOrder(jinsa, store1, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order4 = orderRepository
                                .save(newOrder(jinsa, store2, OrderStateEnum.COMPLETE, DeliveryStateEnum.TAKEOUT));
                Order order5 = orderRepository
                                .save(newOrder(jinsa, store2, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order6 = orderRepository.save(
                                newOrder(jinsa, store4, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                OrderDetail orderDetail1 = orderDetailRepository.save(newOrderDetail(order1, menu1));
                OrderDetail orderDetail2 = orderDetailRepository.save(newOrderDetail(order1, menu1));
                OrderDetail orderDetail3 = orderDetailRepository.save(newOrderDetail(order2, menu1));
                OrderDetail orderDetail4 = orderDetailRepository.save(newOrderDetail(order3, menu1));
                OrderDetail orderDetail5 = orderDetailRepository.save(newOrderDetail(order4, menu2));
                OrderDetail orderDetail6 = orderDetailRepository.save(newOrderDetail(order5, menu2));
                OrderDetail orderDetail7 = orderDetailRepository.save(newOrderDetail(order5, menu2));
                OrderDetail orderDetail8 = orderDetailRepository.save(newOrderDetail(order6, menu3));
                CeoReview ceoReview = ceoReviewRepository.save(newCeoReview(store1, order1));
                CustomerReview customerReview = customerReviewRepository
                                .save(newCustomerReview(jinsa, order1, store1, ceoReview, 5.0));
                CustomerReview customerReview2 = customerReviewRepository
                                .save(newCustomerReview(jinsa, order2, store2, null, 4.0));
                CustomerReview customerReview3 = customerReviewRepository
                                .save(newCustomerReview(jinsa, order6, store4, null, 5.0));
                ReportReview reportReview1 = reportReviewRepository
                                .save(newReportReview(ssar, customerReview, ceoReview));
                ReportReview reportReview2 = reportReviewRepository
                                .save(newReportReview(jinsa, customerReview2, ceoReview));
        }

        @WithUserDetails(value = "jinsa", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void getCustomerReviewToStore_test() throws Exception {
                // given
                Long storeId = 1L;
                Long userId = 3L;
                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/" + storeId + "/review/list"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);
                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.customerReviewDtoList.[0].nickname").value("jinsa님"));
                resultActions.andExpect(jsonPath("$.data.customerReviewDtoList.[0].customerMenuDtos.[0].menuName")
                                .value("후라이드"));
        }

        @WithUserDetails(value = "jinsa", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void insertCustomerReview_test() throws Exception {
                // given
                Long userId = 3L;
                Long storeId = 1L;
                Long orderId = 3L;
                InsertCustomerReviewReqDto insertCustomerReviewReqDto = new InsertCustomerReviewReqDto();
                insertCustomerReviewReqDto.setContent("맛잇어용");
                insertCustomerReviewReqDto.setPhoto(null);
                insertCustomerReviewReqDto.setStarPoint(4.0);

                String requestBody = om.writeValueAsString(insertCustomerReviewReqDto);
                System.out.println("테스트 : " + requestBody);
                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/user/" + userId + "/store/" + storeId + "/order/" + orderId
                                                + "/review/save")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : 응답데이터 : " + responseBody);

                // then
                resultActions.andExpect(status().isCreated());
                resultActions.andExpect(jsonPath("$.data.content").value("맛잇어용"));
        }

        @WithUserDetails(value = "jinsa", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findByUserIdToCustomerReview_test() throws Exception {
                // given
                Long userId = 3L;
                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/review/list"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);
                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.user.nickname").value("jinsa님"));
        }

        @WithUserDetails(value = "jinsa", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void deleteByUserId_test() throws Exception {
                // given
                Long userId = 3L;
                Long reviewId = 1L;
                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/user/" + userId + "/review/" + reviewId + "/delete/"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.msg").value("리뷰 삭제하기 성공"));

        }
}
