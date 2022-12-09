package shop.mtcoding.finalproject.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
        private LikeRepository likeRepository;

        @BeforeEach
        public void setUp() {
                User ssar = userRepository.save(newUser("ssar"));
                User jinsa = userRepository.save(newUser("jinsa"));
                Store store = storeRepository.save(newStore(ssar));
                Menu menu1 = menuRepository.save(newMenu(store));
                Menu menu2 = menuRepository.save(newMenu(store));
                Order order1 = orderRepository.save(newOrder(jinsa, store, DeliveryStateEnum.DELIVERY));
                Order order2 = orderRepository.save(newOrder(jinsa, store, DeliveryStateEnum.TAKEOUT));
                CeoReview ceoReview = ceoReviewRepository.save(newCeoReview(store, order1));
                CustomerReview customerReview = customerReviewRepository
                                .save(newCustomerReview(jinsa, order1, store, ceoReview, 4.0));
                CustomerReview customerReview2 = customerReviewRepository
                                .save(newCustomerReview(jinsa, order2, store, null, 5.0));
                Like like = likeRepository.save(newLike(jinsa, store));
                OrderDetail orderDetail1 = orderDetailRepository.save(newOrderDetail(order1, menu1, 1));
                OrderDetail orderDetail2 = orderDetailRepository.save(newOrderDetail(order1, menu2, 2));
                OrderDetail orderDetail3 = orderDetailRepository.save(newOrderDetail(order2, menu1, 2));
                OrderDetail orderDetail4 = orderDetailRepository.save(newOrderDetail(order2, menu2, 3));
        }

        @Test
        public void getCustomerReviewToStore_test() throws Exception {
                // given
                Long storeId = 1L;
                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/store/" + storeId + "/reviewList"));

                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);
                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.customerReviewDtoList.[0].nickname").value("jinsa님"));
                resultActions.andExpect(jsonPath("$.data.customerReviewDtoList.[0].customerMenuDtos.[0].menuName")
                                .value("후라이드치킨"));
        }

        @WithUserDetails(value = "jinsa", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void insertCustomerReview_test() throws Exception {
                // given
                Long orderId = 1L;
                Long storeId = 1L;
                InsertCustomerReviewReqDto insertCustomerReviewReqDto = new InsertCustomerReviewReqDto();
                insertCustomerReviewReqDto.setContent("맛잇어용");
                insertCustomerReviewReqDto.setPhoto(null);
                insertCustomerReviewReqDto.setStarPoint(4.0);

                String requestBody = om.writeValueAsString(insertCustomerReviewReqDto);
                System.out.println("테스트 : " + requestBody);
                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/review/" + orderId + "/insert/" + storeId).content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : 응답데이터 : " + responseBody);

                // then
                resultActions.andExpect(status().isCreated());
                resultActions.andExpect(jsonPath("$.data.content").value("맛잇어용"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findByUserIdToCustomerReview_test() throws Exception {
                // given
                Long userId = 1L;
                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/review/" + userId));

                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);
                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.user.nickname").value("ssar님"));
        }

        @WithUserDetails(value = "jinsa", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void deleteByUserId_test() throws Exception {
                // given
                Long userId = 2L;
                Long reviewId = 1L;
                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/review/" + userId + "/delete/" + reviewId));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.msg").value("리뷰 삭제하기 성공"));

        }
}
