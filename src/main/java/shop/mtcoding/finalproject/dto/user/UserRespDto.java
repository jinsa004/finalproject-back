package shop.mtcoding.finalproject.dto.user;

import java.time.format.DateTimeFormatter;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.user.User;

public class UserRespDto {

    @Getter
    @Setter
    public static class UserDto {
        private Long id;

        public UserDto(Long id) {
            this.id = id;
        }

    }

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
        private String address;
        private String nickname;
        private byte[] photo;
        private String phone;

        public UpdateUserRespDto(User user) {
            this.id = user.getId();
            this.username = user.getUsername();
            this.address = user.getAddress();
            this.nickname = user.getNickname();
            this.photo = user.getPhoto();
            this.phone = user.getPhone();
        }

    }

    @Getter
    @Setter
    public static class DetailUserRespDto {
        private String username;
        private String password;
        private String address;
        private String nickname;
        private String phone;
        private byte[] photo;

        public DetailUserRespDto(User user) {
            this.username = user.getUsername();
            this.password = user.getPassword();
            this.address = user.getAddress();
            this.nickname = user.getNickname();
            this.phone = user.getPhone();
            this.photo = user.getPhoto();
        }

    }
}
