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
import org.springframework.http.HttpStatus;
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
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
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
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewReqDto.InsertReportReviewReqDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewReqDto.ResolveReportReviewReqDto;

@Sql("classpath:db/truncate.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class ReportReviewControllerTest extends DummyEntity {

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
        public void serUp() {
                User ssar = userRepository.save(newUser("ssar", UserEnum.CEO));
                User cos = userRepository.save(newUser("cos", UserEnum.CEO));
                User jinsa = userRepository.save(newUser("jinsa", UserEnum.CUSTOMER));
                User jina = userRepository.save(newUser("jina", UserEnum.ADMIN));
                Store store1 = storeRepository.save(newStore(ssar));
                Store store2 = storeRepository.save(newStore(cos));
                Menu menu1 = menuRepository.save(newMenu(store1, "후라이드치킨"));
                Menu menu2 = menuRepository.save(newMenu(store2, "간장치킨"));
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

        @WithUserDetails(value = "jina", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void resolveReportReview_test() throws Exception {
                // given
                Long reportReviewId = 1L;
                ResolveReportReviewReqDto resolveReportReviewReqDto = new ResolveReportReviewReqDto();
                resolveReportReviewReqDto.setAdminComment("쌍방 욕설로 인한 기각처리");

                String requestBody = om.writeValueAsString(resolveReportReviewReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/admin/review/" + reportReviewId + "/resolve").content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.adminComment").value("쌍방 욕설로 인한 기각처리"));
                resultActions.andExpect(jsonPath("$.data.resolve").value(true));
        }

        @WithUserDetails(value = "jina", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void getDetailReportReview_test() throws Exception {
                // given
                Long reportReviewId = 1L;
                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/admin/review/" + reportReviewId + "/report/detail"));

                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.reason").value("HONOR"));

        }

        @WithUserDetails(value = "jina", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void getReportReviewList_test() throws Exception {
                // given

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/admin/review/report/list"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.reportReviews.[0].reason").value("명예훼손"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findAllByStoreId_test() throws Exception {
                // given
                Long storeId = 1L;
                Long userId = 1L;
                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/" + storeId + "/review/report"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void insert_test() throws Exception {
                // given
                Long reviewId = 1L;
                Long userId = 1L;
                User userPS = userRepository.findByUsername("ssar")
                                .orElseThrow(() -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
                System.out.println("테스트 : " + userPS.getId());
                System.out.println("테스트 : " + userPS.getUsername());
                System.out.println("테스트 : " + userPS.getRole().getValue());

                InsertReportReviewReqDto insertReportReviewReqDto = new InsertReportReviewReqDto();
                insertReportReviewReqDto.setReason("명예훼손");
                insertReportReviewReqDto.setUserKind("사업자 회원");
                insertReportReviewReqDto.setUserId(userPS.getId());
                insertReportReviewReqDto.setReviewId(reviewId);

                String requestBody = om.writeValueAsString(insertReportReviewReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/user/" + userId + "/review/" + reviewId + "/report")
                                                .content(requestBody).contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();

                // then
                resultActions.andExpect(status().isCreated());
        }
}
