package shop.mtcoding.finalproject.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum DeliveryStateEnum {
    DELIVERY("배달"), TAKEOUT("포장");

    private String state;
}