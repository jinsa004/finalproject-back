package shop.mtcoding.finalproject.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum OrderStateEnum {
    ORDER("주문완료"), CHECK("주문확인"),
    DELIVERY("배달중"), CANCEL("주문취소"), COMPLETE("배달완료");

    private String state;
}
