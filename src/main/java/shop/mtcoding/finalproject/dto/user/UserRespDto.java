package shop.mtcoding.finalproject.dto.user;

import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.user.User;

public class UserRespDto {

    @Setter
    @Getter
    public static class LoginRespDto {
        private Long id;
        private String username;
        private String createdAt;

        public LoginRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.createdAt = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

    }

    @Setter
    @Getter
    public static class JoinRespDto {
        private Long id;
        private String username;

        public JoinRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
        }

    }

    @Getter
    @Setter
    public static class UpdateUserRespDto {
        private Long id;
        private String username;
        private String nickname;
        private String photo;
        private String phone;
        private String createdAt;

        public UpdateUserRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.nickname = user.getNickname();
            this.photo = user.getPhoto();
            this.phone = user.getPhone();
            this.createdAt = user.getCreatedAt().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        }

    }
}
