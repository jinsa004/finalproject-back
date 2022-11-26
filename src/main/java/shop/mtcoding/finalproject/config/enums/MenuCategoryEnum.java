package shop.mtcoding.finalproject.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum MenuCategoryEnum {
    Main("메인 메뉴"), SIDE("사이드 메뉴"), DRINK("음료");

    private String category;
}
