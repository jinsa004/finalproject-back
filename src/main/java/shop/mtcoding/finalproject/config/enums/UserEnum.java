package shop.mtcoding.finalproject.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UserEnum {
    ADMIN("관리자"), CUSTOMER("일반 회원"), CEO("사업자 회원");

    private String value;
}
