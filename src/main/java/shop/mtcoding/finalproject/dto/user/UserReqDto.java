package shop.mtcoding.finalproject.dto.user;


import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.domain.user.User;

public class UserReqDto {

    @Getter
    @Setter
    public static class LoginReqDto {
        private String username;
        private String password;
    }

    @ToString
    @Getter
    @Setter
    public static class JoinReqDto {
        @Size(min = 2, max = 20)
        @NotBlank(message = "유저네임은 필수입니다.")
        private String username;
        @Pattern(regexp = "^[가-힣]{4,20}", message = "비밀번호는 영문,숫자,특수문자 최소4에서 최대20까지입니다.")
        private String password;
        private String address;
        private String nickname;
        private String photo;
        private String phone;

        public User toEntity() {
            return User.builder()
                    .username(username)
                    .password(password)
                    .nickname("username")
                    .photo(null)
                    .phone("01011112222")
                    .role(UserEnum.CUSTOMER)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class UpdateUserReqDto {
        private Long id; // 서비스 로직
        private String password;
        private String address;
        private String nickname;
        private String phone;
        private String photo;
    }

}