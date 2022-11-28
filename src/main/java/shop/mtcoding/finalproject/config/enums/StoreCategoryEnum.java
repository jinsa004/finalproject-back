package shop.mtcoding.finalproject.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StoreCategoryEnum {
    CHICKEN("치킨"), PIZZA("피자"), BURGER("버거"), SCHOOLFOOD("분식"),
    KRFOOD("한식"), CNFOOD("중식"), JPFOOD("일식"), BOSSAM("보쌈"), PORRIDGE("죽");

    private String category;
}
