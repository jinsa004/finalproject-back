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
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.ApplyReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.SaveStoreReqDto;
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
    private StoreRepository storeRepository;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    public void setUp() {
        User ssar = userRepository.save(newUser("ssar"));
    }

    /* ///////////// POST ///////////// */
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
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
    public void save_test() throws Exception {
        // given
        User userPS = userRepository.findByUsername("ssar").orElseThrow(
                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
        Dummy_apply(userPS);
        SaveStoreReqDto saveStoreReqDto = new SaveStoreReqDto();
        saveStoreReqDto.setCategory("치킨");
        saveStoreReqDto.setName("양념이 맛있는 치킨집");
        saveStoreReqDto.setPhone("0510001234");
        saveStoreReqDto.setThumbnail(null);
        saveStoreReqDto.setOpenTime("10");
        saveStoreReqDto.setCloseTime("10");
        saveStoreReqDto.setMinAmount("12000");
        saveStoreReqDto.setDeliveryHour("50");
        saveStoreReqDto.setDeliveryCost("3000");
        saveStoreReqDto.setIntro("맛있는 치킨집");
        saveStoreReqDto.setNotice("깨끗한 기름을 사용하여 맛있는 치킨을 만듭니다.");
        String requestBody = om.writeValueAsString(saveStoreReqDto);
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
        Dummy_apply(userPS);
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
        Dummy_apply(userPS);
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
        Dummy_apply(userPS);

        // when
        ResultActions resultActions = mvc
                .perform(get("/api/user/apply"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data.ceoName").value("테스터"));
        resultActions.andExpect(jsonPath("$.data.accept").value(false));
    }

    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void findToStoreDetail_test() throws Exception {
        // given
        User userPS = userRepository.findByUsername("ssar").orElseThrow(
                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
        Dummy_apply(userPS);

        // when
        ResultActions resultActions = mvc
                .perform(get("/api/store"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data.ceoName").value("테스터"));
    }

    public void Dummy_apply(User userPS) {
        ApplyReqDto applyReqDto = new ApplyReqDto();
        applyReqDto.setCeoName("테스터");
        applyReqDto.setBusinessAddress("부산시 부산진구 혜도빌딩 4층 423호");
        applyReqDto.setBusinessNumber("0101112222");
        storeRepository.save(applyReqDto.toEntity(applyReqDto, userPS));
    }
}
