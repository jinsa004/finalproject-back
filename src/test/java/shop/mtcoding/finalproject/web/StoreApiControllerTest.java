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
import shop.mtcoding.finalproject.config.enums.MenuCategoryEnum;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
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
                dummy_init();
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
                Long userId = 3L;
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
                resultActions.andExpect(jsonPath("$.msg").value("가게있는 유저"));
        }

        @WithUserDetails(value = "busan", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void getLikeStroeList_test() throws Exception {
                // given
                Long userId = 14L;

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
                resultActions.andExpect(jsonPath("$.data.starPoint").value(4.0));
                resultActions.andExpect(jsonPath("$.data.menuList[0].name").value("레드마블치킨"));
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
                resultActions.andExpect(jsonPath("$.data.stores.[0].storeName").value("네네치킨"));
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
                resultActions.andExpect(jsonPath("$.data.ceoName").value("네네치킨님"));
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
                Long userId = 3L;
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
                resultActions.andExpect(jsonPath("$.data.ceoName").value("네네치킨님"));
                resultActions.andExpect(jsonPath("$.data.businessNumber").value("112233"));
        }

        @WithUserDetails(value = "hoho", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void update_test() throws Exception {
                // given
                Long userId = 3L;
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
                resultActions.andExpect(jsonPath("$.data.ceoName").value("피자헛님"));
                resultActions.andExpect(jsonPath("$.data.businessNumber").value("112233"));
        }

        @WithUserDetails(value = "admin", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void apply_test() throws Exception {
                // given
                Long userId = 15L;
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

        private void dummy_init() {
                User ssar = userRepository.save(newUser("ssar", UserEnum.CEO));
                User cos = userRepository.save(newUser("cos", UserEnum.CEO));
                User hoho = userRepository.save(newUser("hoho", UserEnum.CEO));
                User haha = userRepository.save(newUser("haha", UserEnum.CEO));
                User koko = userRepository.save(newUser("koko", UserEnum.CEO));
                User kaka = userRepository.save(newUser("kaka", UserEnum.CEO));
                User hihi = userRepository.save(newUser("hihi", UserEnum.CEO));
                User kiki = userRepository.save(newUser("kiki", UserEnum.CEO));
                User papa = userRepository.save(newUser("papa", UserEnum.CEO));
                User popo = userRepository.save(newUser("popo", UserEnum.CEO));
                User pepe = userRepository.save(newUser("pepe", UserEnum.CEO));
                User pipi = userRepository.save(newUser("pipi", UserEnum.CEO));
                User gimhae = userRepository.save(newGimhaeUser("gimhae", UserEnum.CUSTOMER));
                User busan = userRepository.save(newUser("busan", UserEnum.CUSTOMER));
                User admin = userRepository.save(newUser("admin", UserEnum.ADMIN));
                Store store1 = storeRepository.save(newStore(ssar, "네네치킨", StoreCategoryEnum.CHICKEN,
                                "assets/images/store_thumbnail/네네치킨.png", "부산 네네치킨입니다."));
                Store store2 = storeRepository.save(newStore(cos, "희치킨", StoreCategoryEnum.CHICKEN,
                                "assets/images/store_thumbnail/희치킨.png", "부산 희치킨입니다."));
                Store store3 = storeRepository.save(newStore(hoho, "피자헛", StoreCategoryEnum.PIZZA,
                                "assets/images/store_thumbnail/피자.jpg", "부산 피자헛입니다."));
                Store store4 = storeRepository.save(newStore(haha, "버거킹", StoreCategoryEnum.BURGER,
                                "assets/images/store_thumbnail/로고.png", "부산 버거킹입니다."));
                Store store5 = storeRepository.save(newStore(koko, "조아분식", StoreCategoryEnum.SCHOOLFOOD,
                                "assets/images/store_thumbnail/분식.jpg", "부산 조아분식입니다."));
                Store store6 = storeRepository.save(newStore(kaka, "모던한식당", StoreCategoryEnum.KRFOOD,
                                "assets/images/store_thumbnail/한식.jpg", "부산 모던한식당입니다."));
                Store store7 = storeRepository.save(newStore(kiki, "라이친", StoreCategoryEnum.CNFOOD,
                                "assets/images/store_thumbnail/중식.jpg", "부산 라이친입니다."));
                Store store8 = storeRepository.save(newStore(hihi, "카츠오우", StoreCategoryEnum.JPFOOD,
                                "assets/images/store_thumbnail/일식.jpg", "부산 카츠오우입니다."));
                Store store9 = storeRepository.save(newStore(papa, "진짜보쌈", StoreCategoryEnum.BOSSAM,
                                "assets/images/store_thumbnail/보쌈.jpg", "부산 진짜보쌈입니다."));
                Store store10 = storeRepository.save(newStore(popo, "본죽", StoreCategoryEnum.PORRIDGE,
                                "assets/images/store_thumbnail/죽.jpg", "부산 본죽입니다."));
                Store store11 = storeRepository.save(newApplyStore(pepe));
                Store store12 = storeRepository.save(newSaveStore(pipi));
                Store storeG1 = storeRepository
                                .save(newGimehaeStore(gimhae, "김해 피자헛", StoreCategoryEnum.PIZZA,
                                                "assets/images/store_thumbnail/피자.jpg", "김해 피자헛입니다."));
                Menu menu1 = menuRepository.save(
                                newMenu(store1, "레드마블치킨", "assets/images/store_thumbnail/레드마블치킨.jpg",
                                                "알싸한 매운 양념에 마블소스를 듬뿍!!",
                                                MenuCategoryEnum.MAIN, 20000));
                Menu menu2 = menuRepository.save(
                                newMenu(store1, "청양마요치킨", "assets/images/store_thumbnail/청양마요치킨.jpg",
                                                "청양고추의 알싸함과 마요네즈소스!",
                                                MenuCategoryEnum.MAIN, 20000));
                Menu menu3 = menuRepository.save(
                                newMenu(store1, "치즈스틱", "assets/images/store_thumbnail/치즈스틱.jpg",
                                                "겉은 바삭 속은 치즈?~!",
                                                MenuCategoryEnum.SIDE, 2500));
                Menu menu4 = menuRepository.save(
                                newMenu(store2, "후라이드치킨", "assets/images/store_thumbnail/후라이드치킨.jpg",
                                                "바삭한 튀김 옷으로 승부한다!",
                                                MenuCategoryEnum.MAIN, 18000));
                Menu menu5 = menuRepository.save(
                                newMenu(store2, "마늘순살샐러드", "assets/images/store_thumbnail/마늘순살샐러드.jpg",
                                                "드레싱 소스가 듬뿍", MenuCategoryEnum.SIDE, 10000));
                Menu menu6 = menuRepository
                                .save(newMenu(store2, "콜라", "assets/images/store_thumbnail/콜라.jpg", "코카콜라",
                                                MenuCategoryEnum.DRINK, 2000));
                Menu menu7 = menuRepository.save(
                                newMenu(store3, "돈마호크", "assets/images/store_thumbnail/돈마호크.png",
                                                "돈마호크 고기를 사용한 피자",
                                                MenuCategoryEnum.MAIN, 25000));
                Menu menu8 = menuRepository.save(
                                newMenu(store3, "치즈오븐스파게티", "assets/images/store_thumbnail/치즈오븐스파게티.jpg",
                                                "180도 오븐에 5분간 스윽~",
                                                MenuCategoryEnum.SIDE, 5000));
                Menu menu9 = menuRepository
                                .save(newMenu(store3, "콜라", "assets/images/store_thumbnail/콜라.jpg", "코카콜라",
                                                MenuCategoryEnum.DRINK, 2000));
                Menu menu10 = menuRepository.save(
                                newMenu(store4, "기네스와퍼", "assets/images/store_thumbnail/기네스와퍼.png",
                                                "기네스 맥주를 이용한 번에 와퍼를?!",
                                                MenuCategoryEnum.MAIN, 10200));
                Menu menu11 = menuRepository.save(
                                newMenu(store4, "치즈프라이", "assets/images/store_thumbnail/치즈프라이.png",
                                                "감자튀김과 치즈의 만남",
                                                MenuCategoryEnum.SIDE, 2800));
                Menu menu12 = menuRepository
                                .save(newMenu(store4, "콜라", "assets/images/store_thumbnail/콜라.png", "코카콜라",
                                                MenuCategoryEnum.DRINK, 2000));
                Menu menu14 = menuRepository
                                .save(newMenu(store5, "떡볶이", "assets/images/store_thumbnail/떡볶이.jpg",
                                                "한국인은 먹을 수 밖에 없는 맛",
                                                MenuCategoryEnum.MAIN, 8000));
                Menu menu15 = menuRepository.save(
                                newMenu(store5, "쥬시쿨", "assets/images/store_thumbnail/쥬시쿨.jpg", "맛있는 자두맛 쥬시쿨",
                                                MenuCategoryEnum.DRINK, 1500));
                Menu menu16 = menuRepository
                                .save(newMenu(store6, "비빔밥", "assets/images/store_thumbnail/비빔밥.jpg",
                                                "비빔밥을 비벼봐요~",
                                                MenuCategoryEnum.MAIN, 6500));
                Menu menu17 = menuRepository.save(
                                newMenu(store6, "해물파전", "assets/images/store_thumbnail/해물파전.jpg",
                                                "각종 야채와 해물 만남의 전",
                                                MenuCategoryEnum.SIDE, 10000));
                Menu menu18 = menuRepository
                                .save(newMenu(store6, "콜라", "assets/images/store_thumbnail/콜라.jpg", "코카콜라",
                                                MenuCategoryEnum.DRINK, 2000));
                Menu menu19 = menuRepository
                                .save(newMenu(store7, "짜장면", "assets/images/store_thumbnail/짜장면.jpg",
                                                "달콤짭짤 춘장과 면의 만남",
                                                MenuCategoryEnum.MAIN, 5000));
                Menu menu20 = menuRepository
                                .save(newMenu(store7, "짬뽕", "assets/images/store_thumbnail/짬뽕.jpg",
                                                "시원한 해물육수 베이스의 짬뽕!!",
                                                MenuCategoryEnum.MAIN, 6000));
                Menu menu21 = menuRepository
                                .save(newMenu(store7, "콜라", "assets/images/store_thumbnail/콜라.jpg", "코카콜라",
                                                MenuCategoryEnum.DRINK, 2000));
                Menu menu22 = menuRepository
                                .save(newMenu(store8, "돈까스", "assets/images/store_thumbnail/돈까스.jpg",
                                                "깨끗한 기름으로 깔끔하게",
                                                MenuCategoryEnum.MAIN, 7000));
                Menu menu23 = menuRepository.save(
                                newMenu(store8, "야끼만두", "assets/images/store_thumbnail/야끼만두.jpg", "야끼만두는 인정이지~",
                                                MenuCategoryEnum.SIDE, 4000));
                Menu menu24 = menuRepository
                                .save(newMenu(store8, "콜라", "assets/images/store_thumbnail/콜라.jpg", "코카콜라",
                                                MenuCategoryEnum.DRINK, 2000));
                Menu menu25 = menuRepository
                                .save(newMenu(store9, "보쌈", "assets/images/store_thumbnail/보쌈.png",
                                                "보쌈을 쌈에 싸서 드셔보아요",
                                                MenuCategoryEnum.MAIN, 50000));
                Menu menu26 = menuRepository
                                .save(newMenu(store9, "막국수", "assets/images/store_thumbnail/막국수.jpg",
                                                "시원한 막국수와 보쌈을 함께!",
                                                MenuCategoryEnum.SIDE, 12000));
                Menu menu27 = menuRepository
                                .save(newMenu(store9, "소주", "assets/images/store_thumbnail/소주.jpg", "어른의 음료",
                                                MenuCategoryEnum.DRINK, 4000));
                Menu menu28 = menuRepository.save(
                                newMenu(store10, "전복죽", "assets/images/store_thumbnail/전복죽.jpg",
                                                "아픈 사람에게 영양만점 전복죽",
                                                MenuCategoryEnum.MAIN, 10500));
                Menu menu29 = menuRepository.save(
                                newMenu(store10, "소고기장조림", "assets/images/store_thumbnail/소고기장조림.jpg",
                                                "죽이 더 맛있어지는 비결",
                                                MenuCategoryEnum.SIDE, 4000));
                Menu menu30 = menuRepository.save(
                                newMenu(store10, "콜라", "assets/images/store_thumbnail/콜라.jpg", "코카콜라",
                                                MenuCategoryEnum.DRINK, 2000));
                Menu menu31 = menuRepository
                                .save(newMenu(store1, "콜라", "assets/images/store_thumbnail/콜라.jpg", "코카콜라",
                                                MenuCategoryEnum.DRINK, 2000));
                Order order1 = orderRepository.save(
                                newOrder(busan, store1, OrderStateEnum.ORDER, DeliveryStateEnum.DELIVERY));
                Order order2 = orderRepository.save(
                                newOrder(busan, store1, OrderStateEnum.COMPLETE, DeliveryStateEnum.TAKEOUT));
                Order order3 = orderRepository.save(
                                newOrder(busan, store1, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order4 = orderRepository.save(
                                newOrder(busan, store2, OrderStateEnum.COMPLETE, DeliveryStateEnum.TAKEOUT));
                Order order5 = orderRepository.save(
                                newOrder(busan, store3, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order6 = orderRepository.save(
                                newOrder(busan, store4, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order7 = orderRepository.save(
                                newOrder(busan, store5, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order8 = orderRepository.save(
                                newOrder(busan, store6, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order9 = orderRepository.save(
                                newOrder(busan, store7, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order10 = orderRepository.save(
                                newOrder(busan, store8, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order11 = orderRepository.save(
                                newOrder(busan, store9, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order12 = orderRepository.save(
                                newOrder(busan, store10, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                Order order13 = orderRepository.save(
                                newOrder(busan, store10, OrderStateEnum.COMPLETE, DeliveryStateEnum.DELIVERY));
                OrderDetail orderDetail1 = orderDetailRepository.save(newOrderDetail(order1, menu1));
                OrderDetail orderDetail2 = orderDetailRepository.save(newOrderDetail(order1, menu3));
                OrderDetail orderDetail3 = orderDetailRepository.save(newOrderDetail(order2, menu2));
                OrderDetail orderDetail4 = orderDetailRepository.save(newOrderDetail(order3, menu1));
                OrderDetail orderDetail5 = orderDetailRepository.save(newOrderDetail(order4, menu4));
                OrderDetail orderDetail6 = orderDetailRepository.save(newOrderDetail(order5, menu7));
                OrderDetail orderDetail7 = orderDetailRepository.save(newOrderDetail(order5, menu9));
                OrderDetail orderDetail8 = orderDetailRepository.save(newOrderDetail(order6, menu10));
                OrderDetail orderDetail9 = orderDetailRepository.save(newOrderDetail(order7, menu14));
                OrderDetail orderDetail10 = orderDetailRepository.save(newOrderDetail(order8, menu16));
                OrderDetail orderDetail11 = orderDetailRepository.save(newOrderDetail(order9, menu19));
                OrderDetail orderDetail12 = orderDetailRepository.save(newOrderDetail(order10, menu22));
                OrderDetail orderDetail13 = orderDetailRepository.save(newOrderDetail(order11, menu25));
                OrderDetail orderDetail14 = orderDetailRepository.save(newOrderDetail(order12, menu28));
                OrderDetail orderDetail15 = orderDetailRepository.save(newOrderDetail(order13, menu29));
                CeoReview ceoReview = ceoReviewRepository.save(newCeoReview(store1, order1));
                CeoReview ceoReview2 = ceoReviewRepository.save(newCeoReview(store2, order4));
                CustomerReview customerReview = customerReviewRepository
                                .save(newCustomerReview(busan, order1, store1, ceoReview, 5.0));
                CustomerReview customerReview2 = customerReviewRepository
                                .save(newCustomerReview(busan, order2, store1, null, 3.0));
                CustomerReview customerReview3 = customerReviewRepository
                                .save(newCustomerReview(busan, order4, store2, ceoReview2, 4.0));
                CustomerReview customerReview4 = customerReviewRepository
                                .save(newCustomerReview(busan, order5, store3, null, 3.0));
                CustomerReview customerReview5 = customerReviewRepository
                                .save(newCustomerReview(busan, order6, store4, null, 5.0));
                Like like1 = likeRepository.save(newLike(busan, store1));
                Like like2 = likeRepository.save(newLike(busan, store2));
                Like like3 = likeRepository.save(newLike(busan, store4));
                ReportReview reportReview1 = reportReviewRepository
                                .save(newReportReview(ssar, customerReview, ceoReview, "명예훼손으로 인한 수용처리", true));
                ReportReview reportReview2 = reportReviewRepository
                                .save(newReportReview(busan, customerReview2, ceoReview2, null, false));
        }
}
