package shop.mtcoding.finalproject.util;

import org.junit.jupiter.api.Test;

import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.config.jwt.JwtProcess;
import shop.mtcoding.finalproject.domain.user.User;

public class JwtProcessTest {

    @Test
    public void create_test() {
        // given
        User user = User.builder().id(1L).role(UserEnum.CUSTOMER).build();
        LoginUser loginUser = new LoginUser(user);
        String token = JwtProcess.create(loginUser);
        System.out.println(token);
    }

}
