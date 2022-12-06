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
import shop.mtcoding.finalproject.domain.menu.MenuRepository;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.InsertMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.UpdateMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.UpdateMenuStateReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.ApplyReqDto;

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
    private MenuRepository menuRepository;

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
    public void insert_test() throws Exception, NestedServletException {

        // given
        User userPS = userRepository.findByUsername("ssar").orElseThrow(
                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
        Dummy_apply(userPS);

        InsertMenuReqDto insertMenuReqDto = new InsertMenuReqDto();
        insertMenuReqDto.setThumbnail("test");
        insertMenuReqDto.setName("후라이드 치킨");
        insertMenuReqDto.setIntro("후라이드 치킨 소개글");
        insertMenuReqDto.setCategory("메인 메뉴");
        insertMenuReqDto.setPrice("19000");
        String requestBody = om.writeValueAsString(insertMenuReqDto);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(post("/api/store/menu")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON_UTF8));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.data.name").value("후라이드 치킨"));
        resultActions.andExpect(jsonPath("$.data.intro").value("후라이드 치킨 소개글"));
        resultActions.andExpect(jsonPath("$.data.price").value("19000"));

    }

    /* ///////////// PUT ///////////// */

    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void updateByMenuId_test() throws Exception {

        // given
        User userPS = userRepository.findByUsername("ssar").orElseThrow(
                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
        Store storePS = Dummy_apply(userPS);

        InsertMenuReqDto insertMenuReqDto = new InsertMenuReqDto();
        insertMenuReqDto.setThumbnail("test");
        insertMenuReqDto.setName("후라이드 치킨");
        insertMenuReqDto.setIntro("후라이드 치킨 소개글");
        insertMenuReqDto.setCategory("메인 메뉴");
        insertMenuReqDto.setPrice("19000");
        insertMenuReqDto.setUserId(userPS.getId());
        menuRepository.save(insertMenuReqDto.toEntity(storePS));

        Long menuId = 1L;
        UpdateMenuReqDto updateMenuReqDto = new UpdateMenuReqDto();
        updateMenuReqDto.setThumbnail("not null");
        updateMenuReqDto.setThumbnail("test");
        updateMenuReqDto.setName("양념 치킨");
        updateMenuReqDto.setIntro("양념 치킨 소개글");
        updateMenuReqDto.setCategory("메인 메뉴");
        updateMenuReqDto.setPrice("21500");
        updateMenuReqDto.setId(menuId);
        updateMenuReqDto.setUserId(userPS.getId());
        String requestBody = om.writeValueAsString(updateMenuReqDto);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(put("/api/store/menu/" + menuId)
                        .content(requestBody)
                        .contentType(APPLICATION_JSON_UTF8));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data.name").value("양념 치킨"));
        resultActions.andExpect(jsonPath("$.data.intro").value("양념 치킨 소개글"));
        resultActions.andExpect(jsonPath("$.data.price").value("21500"));
    }

    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void updateByMenuIdToState() throws Exception {
        // given
        User userPS = userRepository.findByUsername("ssar").orElseThrow(
                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
        Store storePS = Dummy_apply(userPS);

        InsertMenuReqDto insertMenuReqDto = new InsertMenuReqDto();
        insertMenuReqDto.setThumbnail("test");
        insertMenuReqDto.setName("후라이드 치킨");
        insertMenuReqDto.setIntro("후라이드 치킨 소개글");
        insertMenuReqDto.setCategory("메인 메뉴");
        insertMenuReqDto.setPrice("19000");
        insertMenuReqDto.setUserId(userPS.getId());
        menuRepository.save(insertMenuReqDto.toEntity(storePS));

        Long menuId = 1L;
        UpdateMenuStateReqDto updateMenuStateReqDto = new UpdateMenuStateReqDto();
        updateMenuStateReqDto.setId(menuId);
        updateMenuStateReqDto.setClosure(true);
        updateMenuStateReqDto.setUserId(userPS.getId());
        String requestBody = om.writeValueAsString(updateMenuStateReqDto);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(put("/api/store/menu/" + menuId + "/state")
                        .content(requestBody)
                        .contentType(APPLICATION_JSON_UTF8));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
    }

    /* ///////////// GET ///////////// */
    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void findById_test() throws Exception {
        // given
        User userPS = userRepository.findByUsername("ssar").orElseThrow(
                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
        Store storePS = Dummy_apply(userPS);

        InsertMenuReqDto insertMenuReqDto = new InsertMenuReqDto();
        insertMenuReqDto.setThumbnail("test");
        insertMenuReqDto.setName("후라이드 치킨");
        insertMenuReqDto.setIntro("후라이드 치킨 소개글");
        insertMenuReqDto.setCategory("메인 메뉴");
        insertMenuReqDto.setPrice("19000");
        insertMenuReqDto.setUserId(userPS.getId());
        menuRepository.save(insertMenuReqDto.toEntity(storePS));

        Long menuId = 1L;

        // when
        ResultActions resultActions = mvc
                .perform(get("/api/store/menu/" + menuId));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data.name").value("후라이드 치킨"));
        resultActions.andExpect(jsonPath("$.data.intro").value("후라이드 치킨 소개글"));
        resultActions.andExpect(jsonPath("$.data.price").value("19000"));
    }

    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void findAll_test() throws Exception {
        // given
        User userPS = userRepository.findByUsername("ssar").orElseThrow(
                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
        Store storePS = Dummy_apply(userPS);

        InsertMenuReqDto insertMenuReqDto = new InsertMenuReqDto();
        insertMenuReqDto.setThumbnail("test");
        insertMenuReqDto.setName("후라이드 치킨");
        insertMenuReqDto.setIntro("후라이드 치킨 소개글");
        insertMenuReqDto.setCategory("메인 메뉴");
        insertMenuReqDto.setPrice("19000");
        insertMenuReqDto.setUserId(userPS.getId());
        for (int i = 0; i < 3; i++) {
            menuRepository.save(insertMenuReqDto.toEntity(storePS));
        }

        // when
        ResultActions resultActions = mvc
                .perform(get("/api/store/menu"));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data.[0].name").value("후라이드 치킨"));
        resultActions.andExpect(jsonPath("$.data.[1].name").value("후라이드 치킨"));
        resultActions.andExpect(jsonPath("$.data.[2].name").value("후라이드 치킨"));
    }

    public Store Dummy_apply(User userPS) {
        ApplyReqDto applyReqDto = new ApplyReqDto();
        applyReqDto.setCeoName("테스터");
        applyReqDto.setBusinessAddress("부산시 부산진구 혜도빌딩 4층 423호");
        applyReqDto.setBusinessNumber("0101112222");
        Store storePS = storeRepository.save(applyReqDto.toEntity(applyReqDto, userPS));
        return storePS;
    }
}