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
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
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
import shop.mtcoding.finalproject.dto.order.OrderReqDto.FindStatsReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.AdminUpdateStoreApplyAcceptReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoApplyStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoInsertStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoUpdateStoreBusinessStateReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoUpdateStoreReqDto;

@Sql("classpath:db/truncate.sql")
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class StoreApiControllerTest extends DummyEntity {
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

        @Autowired
        private LikeRepository likeRepository;

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
                Menu menu3 = menuRepository.save(newMenu(store4, "페퍼로니피자"));
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
                Like like1 = likeRepository.save(newLike(jinsa, store2));
                Like like2 = likeRepository.save(newLike(jinsa, store1));
                Like like3 = likeRepository.save(newLike(jinsa, store4));
                ReportReview reportReview1 = reportReviewRepository
                                .save(newReportReview(ssar, customerReview, ceoReview, "욕설확인으로 인한 신고처리"));
                ReportReview reportReview2 = reportReviewRepository
                                .save(newReportReview(jinsa, customerReview2, ceoReview, null));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void getCategoryStoreList_test() throws Exception {
                // given
                Long userId = 1L;
                String category = "CHICKEN";

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/" + category + "/list"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.code").value(1));
        }

        @WithUserDetails(value = "hoho", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void getStoreName_test() throws Exception {
                // given
                Long userId = 5L;
                Store storePS = storeRepository.findByUserId(userId).get();
                storePS.updateAccept(true);
                storeRepository.save(storePS);

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/name"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.code").value(1));
                resultActions.andExpect(jsonPath("$.msg").value("가게없는 유저"));
        }

        @WithUserDetails(value = "jinsa", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void getLikeStroeList_test() throws Exception {
                // given
                Long userId = 3L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/like/store/list"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
        }

        @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void getStoreInfo_test() throws Exception {
                // given
                Long storeId = 1L;
                Long userId = 2L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/" + storeId + "/info"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.minAmount").value("10000"));
        }

        @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void detailStoreMain_test() throws Exception {
                // given
                Long storeId = 1L;
                Long userId = 2L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/" + storeId + "/detail"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.starPoint").value(5.0));
                resultActions.andExpect(jsonPath("$.data.menuList[0].name").value("후라이드"));
        }

        @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findStoreList_test() throws Exception {
                // given
                String adress = "부산시 진구 서면 17번 길";
                Long storeId = 1L;
                Long userId = 2L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/list"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.stores.[0].storeName").value("그린치킨"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findByUserIdToApply_test() throws Exception {
                // given
                Long userId = 1L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/apply"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.accept").value(true));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findByUserId_test() throws Exception {
                // given
                Long userId = 1L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/info"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.ceoName").value("cos"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findStatsByStoreId_test() throws Exception {
                // given
                Long userId = 1L;
                Long storeId = 1L;
                FindStatsReqDto findStatsReqDto = new FindStatsReqDto();
                findStatsReqDto.setStartTime("2022-12-09");
                findStatsReqDto.setEndTime("2022-12-09");
                findStatsReqDto.setStoreId(storeId);
                String requestBody = om.writeValueAsString(findStatsReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/user/" + userId + "/store/info/stats")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
        }

        @WithUserDetails(value = "admin", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findAllToApplyList_test() throws Exception {
                // given

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/admin/store/apply/list"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.[0].username").value("ssar"));
                resultActions.andExpect(jsonPath("$.data.[0].accept").value(true));
                resultActions.andExpect(jsonPath("$.data.[1].username").value("cos"));
                resultActions.andExpect(jsonPath("$.data.[1].accept").value(true));
        }

        @WithUserDetails(value = "admin", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void updateByStoreIdToAccept_test() throws Exception {
                // given
                AdminUpdateStoreApplyAcceptReqDto adminUpdateStoreApplyAcceptReqDto = new AdminUpdateStoreApplyAcceptReqDto();
                adminUpdateStoreApplyAcceptReqDto.setAccept(true);
                String requestBody = om.writeValueAsString(adminUpdateStoreApplyAcceptReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/admin/store/apply/list"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
        }

        @WithUserDetails(value = "hoho", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findByIdToSave_test() throws Exception {
                // given
                Long userId = 5L;
                Store storePS = storeRepository.findByUserId(userId).get();
                storePS.updateAccept(true);
                storeRepository.save(storePS);

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/save/info"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void delete_test() throws Exception {
                // given
                Long userId = 1L;
                Long storeId = 1L;

                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/user/" + userId + "/store/update/close"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void updateToBusiness_test() throws Exception {
                // given
                Long userId = 1L;
                CeoUpdateStoreBusinessStateReqDto ceoUpdateStoreBusinessStateReqDto = new CeoUpdateStoreBusinessStateReqDto();
                ceoUpdateStoreBusinessStateReqDto.setOpend(true);
                String requestBody = om.writeValueAsString(ceoUpdateStoreBusinessStateReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/user/" + userId + "/store/update/business")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void save_test() throws Exception {
                // given
                Long userId = 1L;
                CeoInsertStoreReqDto ceoInsertStoreReqDto = new CeoInsertStoreReqDto();
                ceoInsertStoreReqDto.setCategory("치킨");
                ceoInsertStoreReqDto.setName("양념이 맛있는 치킨집");
                ceoInsertStoreReqDto.setPhone("0510001234");
                ceoInsertStoreReqDto.setThumbnail("AAAAGElEQVQoU2NkYGD4z0AEYBxViC+UqB88AKk6CgERnGWPAAAAAElFTkSuQmCC");
                ceoInsertStoreReqDto.setOpenTime("10");
                ceoInsertStoreReqDto.setCloseTime("10");
                ceoInsertStoreReqDto.setMinAmount(12000);
                ceoInsertStoreReqDto.setDeliveryHour("50");
                ceoInsertStoreReqDto.setDeliveryCost(3000);
                ceoInsertStoreReqDto.setIntro("맛있는 치킨집");
                ceoInsertStoreReqDto.setNotice("깨끗한 기름을 사용하여 맛있는 치킨을 만듭니다.");
                String requestBody = om.writeValueAsString(ceoInsertStoreReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/user/" + userId + "/store/save")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.category").value("치킨"));
                resultActions.andExpect(jsonPath("$.data.name").value("양념이 맛있는 치킨집"));
                resultActions.andExpect(jsonPath("$.data.thumbnail")
                                .value("AAAAGElEQVQoU2NkYGD4z0AEYBxViC+UqB88AKk6CgERnGWPAAAAAElFTkSuQmCC"));
                resultActions.andExpect(jsonPath("$.data.intro").value("맛있는 치킨집"));
                resultActions.andExpect(jsonPath("$.data.ceoName").value("cos"));
                resultActions.andExpect(jsonPath("$.data.businessNumber").value("112233"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void update_test() throws Exception {
                // given
                Long userId = 1L;
                CeoUpdateStoreReqDto ceoUpdateStoreReqDto = new CeoUpdateStoreReqDto();
                ceoUpdateStoreReqDto.setName("맛좋은 피자집");
                ceoUpdateStoreReqDto.setCategory("피자");
                ceoUpdateStoreReqDto.setPhone("0510001234");
                ceoUpdateStoreReqDto.setThumbnail("AAAAGElEQVQoU2NkYGD4z0AEYBxViC+UqB88AKk6CgERnGWPAAAAAElFTkSuQmCC");
                ceoUpdateStoreReqDto.setOpenTime("10");
                ceoUpdateStoreReqDto.setCloseTime("10");
                ceoUpdateStoreReqDto.setMinAmount(12000);
                ceoUpdateStoreReqDto.setDeliveryHour("50");
                ceoUpdateStoreReqDto.setDeliveryCost(3000);
                ceoUpdateStoreReqDto.setIntro("치즈가 쭉쭉 늘어나는 맛좋은 피자집");
                ceoUpdateStoreReqDto.setNotice("직접 손수 만든 반죽으로 맛있는 피자를 만듭니다.");
                String requestBody = om.writeValueAsString(ceoUpdateStoreReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/user/" + userId + "/store/update")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.category").value("피자"));
                resultActions.andExpect(jsonPath("$.data.name").value("맛좋은 피자집"));
                resultActions.andExpect(jsonPath("$.data.intro").value("치즈가 쭉쭉 늘어나는 맛좋은 피자집"));
                resultActions.andExpect(jsonPath("$.data.ceoName").value("cos"));
                resultActions.andExpect(jsonPath("$.data.businessNumber").value("112233"));
        }

        @WithUserDetails(value = "admin", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void apply_test() throws Exception {
                // given
                Long userId = 4L;
                CeoApplyStoreReqDto ceoApplyStoreReqDto = new CeoApplyStoreReqDto();
                ceoApplyStoreReqDto.setCeoName("admin");
                ceoApplyStoreReqDto.setBusinessAddress("부산시 부산진구 혜도빌딩 4층 423호");
                ceoApplyStoreReqDto.setBusinessNumber("0101112222");
                String requestBody = om.writeValueAsString(ceoApplyStoreReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/user/" + userId + "/store/apply")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isCreated());
                resultActions.andExpect(jsonPath("$.data.businessAddress").value("부산시 부산진구 혜도빌딩 4층 423호"));
                resultActions.andExpect(jsonPath("$.data.businessNumber").value("0101112222"));
                resultActions.andExpect(jsonPath("$.data.ceoName").value("admin"));
        }

        @WithUserDetails(value = "cos", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void insertLike_test() throws Exception {
                // given
                Long userId = 2L;
                Long storeId = 1L;

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/user/" + userId + "/store/" + storeId + "/like"));

                // then
                resultActions.andExpect(status().isCreated());
        }
}
