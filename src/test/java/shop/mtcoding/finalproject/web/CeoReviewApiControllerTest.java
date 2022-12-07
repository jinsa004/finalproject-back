package shop.mtcoding.finalproject.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
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

import com.fasterxml.jackson.databind.ObjectMapper;

import shop.mtcoding.finalproject.config.dummy.DummyEntity;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewRepository;
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
import shop.mtcoding.finalproject.dto.ceoReview.CeoReviewReqDto.InsertCeoReviewReqDto;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class CeoReviewApiControllerTest extends DummyEntity {
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

    @BeforeEach
    public void setUp() {
        User ssar = userRepository.save(newUser("ssar"));
        User jinsa = userRepository.save(newUser("jinsa"));
        Store store = storeRepository.save(newStore(ssar));
        Menu menu = menuRepository.save(newMenu(store));
        Order order = orderRepository.save(newOrder(jinsa, store));
        CeoReview ceoReview = ceoReviewRepository.save(newCeoReview(store, order));
        CustomerReview customerReview = customerReviewRepository.save(newCustomerReview(jinsa, store, ceoReview));
        CustomerReview customerReview2 = customerReviewRepository.save(newCustomerReview(jinsa, store, null));
    }

    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void insertCeoReviewByCustomerId_test() throws Exception {
        // given
        User userPS = userRepository.findByUsername("ssar").orElseThrow(
                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));

        Long customerReviewId = 2L;
        InsertCeoReviewReqDto insertCeoReviewReqDto = new InsertCeoReviewReqDto();
        insertCeoReviewReqDto.setContent("맛있게 드셨다니 다행입니다^^");
        insertCeoReviewReqDto.setUserId(userPS.getId());
        insertCeoReviewReqDto.setCustomerReviewId(customerReviewId);

        CustomerReview customerReviewPS = customerReviewRepository.findById(insertCeoReviewReqDto.getCustomerReviewId())
                .orElseThrow(() -> new CustomApiException("해당 리뷰가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        insertCeoReviewReqDto.setCustomerReviewId(customerReviewId);

        String requestBody = om.writeValueAsString(insertCeoReviewReqDto);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(post("/api/store/" + customerReviewId + "/review")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON_UTF8));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : 응답데이터 : " + responseBody);

        // then
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.data.content").value("맛있게 드셨다니 다행입니다^^"));
    }

    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void findAllReviewByStoreId_test() throws Exception {
        // given
        Long storeId = 1L;

        // when
        ResultActions resultActions = mvc.perform(get("/api/store/" + storeId + "/review"));

        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }

}
