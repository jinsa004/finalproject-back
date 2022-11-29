package shop.mtcoding.finalproject.web;

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
import shop.mtcoding.finalproject.domain.deliveryAddress.DeliveryAddress;
import shop.mtcoding.finalproject.domain.deliveryAddress.DeliveryAddressRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.user.UserReqDto.JoinReqDto;
import shop.mtcoding.finalproject.dto.user.UserReqDto.UpdateUserReqDto;

@Sql("classpath:db/truncate.sql") // 롤백 대신 사용 (auto_increment 초기화 + 데이터 비우기)
@ActiveProfiles("test")
@AutoConfigureMockMvc
@SpringBootTest(webEnvironment = WebEnvironment.MOCK)
public class UserApiControllerTest extends DummyEntity {

    private static final String APPLICATION_JSON_UTF8 = "application/json; charset=utf-8";
    private static final String APPLICATION_FORM_URLENCODED = "application/x-www-form-urlencoded; charset=utf-8";

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper om;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private DeliveryAddressRepository deliveryAddressRepository;

    @BeforeEach
    public void setUp() {
        User ssar = userRepository.save(newUser("ssar"));
        DeliveryAddress deliveryAddress = deliveryAddressRepository.save(newDeliveryAddress("부산시 진구 서면로 17번 길", ssar));
    }

    @Test
    public void join_test() throws Exception {
        // given
        JoinReqDto joinReqDto = new JoinReqDto();
        joinReqDto.setUsername("jinsa");
        joinReqDto.setPassword("1234");

        String requestBody = om.writeValueAsString(joinReqDto);

        // when
        ResultActions resultActions = mvc
                .perform(post("/api/join").content(requestBody)
                        .contentType(APPLICATION_JSON_UTF8));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("디버그 : " + responseBody);

        // then
        resultActions.andExpect(status().isCreated());
        resultActions.andExpect(jsonPath("$.data.username").value("jinsa"));
    }

    @WithUserDetails(value = "ssar", setupBefore = TestExecutionEvent.TEST_EXECUTION)
    @Test
    public void updateByUserId_test() throws Exception {
        // given
        Long userId = 1L;
        UpdateUserReqDto updateUserReqDto = new UpdateUserReqDto();
        updateUserReqDto.setPassword("1234");
        updateUserReqDto.setPhone("01071649311");
        updateUserReqDto.setNickname("mil");
        updateUserReqDto.setEmail("jina@nate.com");

        String requestBody = om.writeValueAsString(updateUserReqDto);
        System.out.println("테스트 : " + requestBody);

        // when
        ResultActions resultActions = mvc
                .perform(put("/api/user/" + userId).content(requestBody)
                        .contentType(APPLICATION_JSON_UTF8));
        String responseBody = resultActions.andReturn().getResponse().getContentAsString();
        System.out.println("테스트 : " + responseBody);

        // // then
        resultActions.andExpect(status().isOk());
        resultActions.andExpect(jsonPath("$.data.nickname").value("mil"));
    }
}