package shop.mtcoding.finalproject.domain.user;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

import org.springframework.http.HttpStatus;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.AudingTime;
import shop.mtcoding.finalproject.dto.user.UserReqDto.UpdateUserReqDto;
import shop.mtcoding.finalproject.util.CustomBase64ConvertUtil;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
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

    @Lob
    @Basic(fetch = FetchType.LAZY)
    @Column(nullable = true, columnDefinition = "LONGBLOB")
    private byte[] photo;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserEnum role; // ADMIN, CUSTOMER, CEO

    // 커멜케이스는 DB에 언더스코어로 생성된다.
    @Column(nullable = false)
    private boolean isActive; // 계정활성화여부

    @Builder
    public User(Long id, String address, String username, String password, String nickname, String phone, byte[] photo,
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

    public void 회원수정(UpdateUserReqDto updateUserReqDto) {
        this.password = updateUserReqDto.getNewPassword();
        this.address = updateUserReqDto.getAddress();
        this.nickname = updateUserReqDto.getNickname();
        this.phone = updateUserReqDto.getPhone();
        this.photo = CustomBase64ConvertUtil.convertToByte(updateUserReqDto.getPhoto());
    }

    public void 회원비활성화() {
        this.isActive = false;
    }

    public void updateRole(Boolean isAccept) {
        if (isAccept)
            this.role = UserEnum.CEO;
        this.role = UserEnum.CUSTOMER;
    }

    public void checkUser(Long userId) {
        if (!this.id.equals(userId)) {
            throw new CustomApiException("권한이 없습니다", HttpStatus.BAD_REQUEST);
        }
    }

    public void checkRole() {
        if (!this.role.equals(UserEnum.ADMIN)) {
            throw new CustomApiException("권한이 없습니다", HttpStatus.BAD_REQUEST);
        }
    }
}