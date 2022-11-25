package shop.mtcoding.finalproject.domain.user;

import java.sql.Timestamp;
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

@NoArgsConstructor(access = AccessLevel.PRIVATE)
@Getter
@Table(name = "users")
@Entity
public class User extends AudingTime {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 20)
    private String username;

    @Column(nullable = false, length = 60)
    private String password;

    @Column(nullable = false, length = 20)
    private String nickname;

    @Column(nullable = true)
    private String photo; // 사진 포맷 무엇으로 할지?

    @Column(nullable = false, length = 11)
    private String phone;

    @Column(nullable = false, length = 50)
    private String email;

    @Enumerated(EnumType.STRING)
    @Column(unique = true, nullable = false)
    private UserEnum role; // ADMIN, CUSTOMER, CEO

    // @Column(nullable = false)
    // private Timestamp createdTime;

    @Builder
    public User(Long id, String username, String password, String nickname, String photo, String phone, String email,
            UserEnum role, Timestamp createdTime) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nickname = nickname;
        this.photo = photo;
        this.phone = phone;
        this.email = email;
        this.role = role;
        // this.createdTime = createdTime;
    }

}