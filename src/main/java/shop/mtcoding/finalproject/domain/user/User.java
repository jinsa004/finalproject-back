package shop.mtcoding.finalproject.domain.user;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.domain.AudingTime;
import shop.mtcoding.finalproject.dto.user.UserReqDto.UpdateUserReqDto;

@NoArgsConstructor
@Getter
@Table(name = "users")
@Entity
public class User extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 60)
    private String address;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = false, length = 20)
    private String phone;

    @Column(nullable = true)
    private String photo; // 사진 포맷 무엇으로 할지?

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role; // ADMIN, CUSTOMER, CEO

    // 커멜케이스는 DB에 언더스코어로 생성된다.
    @Column(nullable = false)
    private boolean isActive; // 계정활성화여부

    public void 회원수정(UpdateUserReqDto updateUserReqDto) {
        this.password = updateUserReqDto.getPassword();
        this.address = updateUserReqDto.getAddress();
        this.nickname = updateUserReqDto.getNickname();
        this.phone = updateUserReqDto.getPhone();
        this.photo = updateUserReqDto.getPhoto();
    }

    @Builder
    public User(Long id, String address, String username, String password, String nickname, String phone, String photo,
            UserEnum role, boolean isActive) {
        this.id = id;
        this.address = address;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.phone = phone;
        this.photo = photo;
        this.role = role;
        this.isActive = isActive;
    }

    public void 회원비활성화() {
        this.isActive = false;
    }

}