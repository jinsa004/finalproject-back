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
import org.springframework.web.util.NestedServletException;

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.finalproject.config.dummy.DummyEntity;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewRepository;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.menu.MenuRepository;
import shop.mtcoding.finalproject.domain.order.OrderRepository;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetailRepository;
import shop.mtcoding.finalproject.domain.reportReview.ReportReviewRepository;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.CeoInsertMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.CeoUpdateMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.CeoUpdateMenuStateReqDto;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class MenuApiControllerTest extends DummyEntity {
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
                Store store1 = storeRepository.save(newStore(ssar, "그린치킨", StoreCategoryEnum.CHICKEN));
                Store store2 = storeRepository.save(newStore(cos, "그린치킨", StoreCategoryEnum.CHICKEN));
                Store store3 = storeRepository.save(newApplyStore(hoho));
                Store store4 = storeRepository.save(newStore(haha, "그린피자", StoreCategoryEnum.PIZZA));
                Menu menu1 = menuRepository.save(newMenu(store1, "후라이드"));
                Menu menu2 = menuRepository.save(newMenu(store2, "간장치킨"));
                Menu menu4 = menuRepository.save(newMenu(store1, "간장치킨"));
                Menu menu3 = menuRepository.save(newMenu(store4, "페퍼로니피자"));
        }

        @WithUserDetails(value = "jinsa", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void getDetailMenu_test() throws Exception {
                // given
                Long userId = 3L;
                Long storeId = 1L;
                Long menuId = 1L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/" + storeId + "/menu/" + menuId
                                                + "/detail"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.name").value("후라이드"));
        }

        @WithUserDetails(value = "jinsa", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void getMenuList_test() throws Exception {
                // given
                Long userId = 3L;
                Long storeId = 1L;
                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/" + storeId + "/menu/list"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.menus[0].name").value("후라이드"));
                resultActions.andExpect(jsonPath("$.data.menus[1].name").value("간장치킨"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findAll_test() throws Exception {
                // given
                Long userId = 1L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/menu/list"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.[0].name").value("후라이드"));
                resultActions.andExpect(jsonPath("$.data.[1].name").value("간장치킨"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void findById_test() throws Exception {
                // given
                Long userId = 1L;
                Long menuId = 2L;

                // when
                ResultActions resultActions = mvc
                                .perform(get("/api/user/" + userId + "/store/menu/" + menuId + "/info"));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.name").value("간장치킨"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void updateByMenuIdToState() throws Exception {
                // given
                Long userId = 1L;
                Long menuId = 1L;
                CeoUpdateMenuStateReqDto ceoUpdateMenuStateReqDto = new CeoUpdateMenuStateReqDto();
                ceoUpdateMenuStateReqDto.setClosure(true);
                String requestBody = om.writeValueAsString(ceoUpdateMenuStateReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/user/" + userId + "/store/menu/" + menuId + "/update/state")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void updateByMenuId_test() throws Exception {
                // given
                Long userId = 1L;
                Long menuId = 1L;
                CeoUpdateMenuReqDto ceoUpdateMenuReqDto = new CeoUpdateMenuReqDto();
                ceoUpdateMenuReqDto.setThumbnail(null);
                ceoUpdateMenuReqDto.setName("간장치킨");
                ceoUpdateMenuReqDto.setIntro("간장치킨 소개글");
                ceoUpdateMenuReqDto.setCategory("메인 메뉴");
                ceoUpdateMenuReqDto.setPrice(21500);
                String requestBody = om.writeValueAsString(ceoUpdateMenuReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(put("/api/user/" + userId + "/store/menu/" + menuId + "/update")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isOk());
                resultActions.andExpect(jsonPath("$.data.name").value("간장치킨"));
                resultActions.andExpect(jsonPath("$.data.intro").value("간장치킨 소개글"));
                resultActions.andExpect(jsonPath("$.data.price").value("21500"));
        }

        @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
        @Test
        public void insert_test() throws Exception, NestedServletException {
                // given
                Long userId = 1L;
                CeoInsertMenuReqDto ceoInsertMenuReqDto = new CeoInsertMenuReqDto();
                ceoInsertMenuReqDto.setThumbnail(null);
                ceoInsertMenuReqDto.setName("간장치킨");
                ceoInsertMenuReqDto.setIntro("간장치킨 소개글");
                ceoInsertMenuReqDto.setCategory("메인 메뉴");
                ceoInsertMenuReqDto.setPrice(21000);
                String requestBody = om.writeValueAsString(ceoInsertMenuReqDto);
                System.out.println("테스트 : " + requestBody);

                // when
                ResultActions resultActions = mvc
                                .perform(post("/api/user/" + userId + "/store/menu/save")
                                                .content(requestBody)
                                                .contentType(APPLICATION_JSON_UTF8));
                String responseBody = resultActions.andReturn().getResponse().getContentAsString();
                System.out.println("테스트 : " + responseBody);

                // then
                resultActions.andExpect(status().isCreated());
                resultActions.andExpect(jsonPath("$.data.name").value("간장치킨"));
                resultActions.andExpect(jsonPath("$.data.intro").value("간장치킨 소개글"));
                resultActions.andExpect(jsonPath("$.data.price").value("21000"));

        }
}
