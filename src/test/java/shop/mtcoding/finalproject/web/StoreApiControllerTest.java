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
import org.springframework.http.HttpStatus;
import org.springframework.security.test.context.support.TestExecutionEvent;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.finalproject.config.dummy.DummyEntity;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
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
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.ApplyReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.InsertStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.UpdateBusinessStateReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.UpdateStoreReqDto;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
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
        private LikeRepository likeRepository;

        @BeforeEach
        public void setUp() {
                User ssar = userRepository.save(newUser("ssar"));
                User jinsa = userRepository.save(newUser("jinsa"));
                Store store = storeRepository.save(newStore(ssar));
                Menu menu1 = menuRepository.save(newMenu(store));
                Menu menu2 = menuRepository.save(newMenu(store));
                Order order = orderRepository.save(newOrder(jinsa, store));
                CeoReview ceoReview = ceoReviewRepository.save(newCeoReview(store, order));
                CustomerReview customerReview1 = customerReviewRepository
                                .save(newCustomerReview(jinsa, order, store, ceoReview, 5.0));
                CustomerReview customerReview2 = customerReviewRepository
                                .save(newCustomerReview(jinsa, order, store, ceoReview, 4.0));
                Like like = likeRepository.save(newLike(jinsa, store));
        }

        @Test
        public void getStoreInfo_test() throws Exception {
                // given
                Long storeId = 1L;
                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/store/" + storeId + "/info"));

                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);
                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.minAmount").value("10000"));
        }

        @Test
        public void detailStoreMain_test() throws Exception {
                // given
                Long storeId = 1L;
                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/store/" + storeId + "/datail"));

                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);
                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.starPoint").value(4.5));
                resultActions.andExpect(jsonPath("$.data.menuList[0].name").value("후라이드치킨"));
        }

        @Test
        public void findStoreList_test() throws Exception {
                // given
                String address = "부산시 진구 서면 17번 길";
                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/storeList"));

                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);
                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.stores.[0].storeName").value("그린치킨"));
        }

        /* ///////////// POST ///////////// */
        @WithUserDetails(value = "jinsa", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void apply_test() throws Exception, NestedServletException {

                // given
                ApplyReqDto applyReqDto = new ApplyReqDto();
                applyReqDto.setCeoName("테스터");
                applyReqDto.setBusinessAddress("부산시 부산진구 혜도빌딩 4층 423호");
                applyReqDto.setBusinessNumber("0101112222");
                String requestBody = om.writeValueAsString(applyReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/user/apply")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isCreated());
                resultActions.andExpect(jsonPath("$.data.businessAddress").value("부산시 부산진구 혜도빌딩 4층 423호"));
                resultActions.andExpect(jsonPath("$.data.businessNumber").value("0101112222"));
                resultActions.andExpect(jsonPath("$.data.ceoName").value("테스터"));

        }

        /* ///////////// PUT ///////////// */
        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void insert_test() throws Exception {
                // given
                User userPS = userRepository.findByUsername("ssar").orElseThrow(
                                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
                InsertStoreReqDto insertStoreReqDto = new InsertStoreReqDto();
                insertStoreReqDto.setCategory("치킨");
                insertStoreReqDto.setName("양념이 맛있는 치킨집");
                insertStoreReqDto.setPhone("0510001234");
                insertStoreReqDto.setThumbnail(null);
                insertStoreReqDto.setOpenTime("10");
                insertStoreReqDto.setCloseTime("10");
                insertStoreReqDto.setMinAmount("12000");
                insertStoreReqDto.setDeliveryHour("50");
                insertStoreReqDto.setDeliveryCost("3000");
                insertStoreReqDto.setIntro("맛있는 치킨집");
                insertStoreReqDto.setNotice("깨끗한 기름을 사용하여 맛있는 치킨을 만듭니다.");
                String requestBody = om.writeValueAsString(insertStoreReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/store")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.category").value("CHICKEN"));
                resultActions.andExpect(jsonPath("$.data.name").value("양념이 맛있는 치킨집"));
                resultActions.andExpect(jsonPath("$.data.intro").value("맛있는 치킨집"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void update_test() throws Exception {
                // given
                User userPS = userRepository.findByUsername("ssar").orElseThrow(
                                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
                UpdateStoreReqDto updateStoreReqDto = new UpdateStoreReqDto();
                updateStoreReqDto.setCategory("치킨");
                updateStoreReqDto.setName("후라이드가 맛있는 치킨집");
                updateStoreReqDto.setPhone("0510001234");
                updateStoreReqDto.setThumbnail(null);
                updateStoreReqDto.setOpenTime("10");
                updateStoreReqDto.setCloseTime("10");
                updateStoreReqDto.setMinAmount("12000");
                updateStoreReqDto.setDeliveryHour("50");
                updateStoreReqDto.setDeliveryCost("3000");
                updateStoreReqDto.setIntro("후라이드 치킨이 정말 맛있는 치킨집");
                updateStoreReqDto.setNotice("깨끗한 기름을 사용하여 맛있는 치킨을 만듭니다.");
                String requestBody = om.writeValueAsString(updateStoreReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/store/info")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.category").value("CHICKEN"));
                resultActions.andExpect(jsonPath("$.data.name").value("후라이드가 맛있는 치킨집"));
                resultActions.andExpect(jsonPath("$.data.intro").value("후라이드 치킨이 정말 맛있는 치킨집"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void updateBusinessState_test() throws Exception {
                // given
                User userPS = userRepository.findByUsername("ssar").orElseThrow(
                                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
                UpdateBusinessStateReqDto updateBusinessStateReqDto = new UpdateBusinessStateReqDto();
                updateBusinessStateReqDto.setOpend(true);
                String requestBody = om.writeValueAsString(updateBusinessStateReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/store/business")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.opend").value(true));
        }

        /* ///////////// GET ///////////// */
        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findToapplyState_test() throws Exception {
                // given
                User userPS = userRepository.findByUsername("ssar").orElseThrow(
                                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/apply"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.ceoName").value("cos"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findToStoreDetail_test() throws Exception {
                // given
                User userPS = userRepository.findByUsername("ssar").orElseThrow(
                                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/store/detail"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.ceoName").value("cos"));
        }
}
