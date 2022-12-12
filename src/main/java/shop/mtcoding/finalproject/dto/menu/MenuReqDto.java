package shop.mtcoding.finalproject.dto.menu;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.util.CustomEnumUtil;

public class MenuReqDto {

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class CeoUpdateMenuStateReqDto {

        private boolean isClosure;

        public Menu toEntity() {
            return Menu.builder()
                    .isClosure(this.isClosure)
                    .build();
        }

    }

    @Getter
    @Setter
    public static class CeoUpdateMenuReqDto {

        private String thumbnail;

        @NotBlank(message = "카테고리를 선택해주세요.")
        private String category;

        @NotBlank(message = "메뉴명을 입력해주세요.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{5,60}$", message = "메뉴명은 특수문자를 포함하지 않은 1~30자리여야 합니다.")
        @Size(min = 5, max = 60, message = "메뉴명은 5~60자 사이로 입력해주세요.")
        private String name;

        @NotBlank(message = "메뉴의 가격을 입력해주세요.")
        @Pattern(regexp = "^[0-9]{3,6}$")
        @Size(min = 3, max = 6)
        private String price;

        @NotBlank(message = "메뉴에 대한 설명을 입력해주세요.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{5,100}$", message = "메뉴소개는 특수문자를 포함하지 않은 1~30자리여야 합니다.")
        @Size(min = 5, max = 100, message = "메뉴소개는 5~100자 사이로 입력해주세요.")
        private String intro;

        private Store store;

        public Menu toEntity() {
            return Menu.builder()
                    .thumbnail(thumbnail)
                    .name(name)
                    .category(CustomEnumUtil.toMenuCategoryEnumFormat(category))
                    .price(price)
                    .intro(intro)
                    .store(store)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class CeoInsertMenuReqDto {

        private String thumbnail;

        @NotBlank(message = "카테고리를 선택해주세요.")
        private String category;

        @NotBlank(message = "메뉴명을 입력해주세요.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{5,60}$", message = "메뉴명은 특수문자를 포함하지 않은 1~30자리여야 합니다.")
        @Size(min = 5, max = 60, message = "메뉴명은 5~60자 사이로 입력해주세요.")
        private String name;

        @NotBlank(message = "메뉴의 가격을 입력해주세요.")
        @Pattern(regexp = "^[0-9]{3,6}$")
        @Size(min = 3, max = 6)
        private String price;

        @NotBlank(message = "메뉴에 대한 설명을 입력해주세요.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{5,100}$", message = "메뉴소개는 특수문자를 포함하지 않은 1~30자리여야 합니다.")
        @Size(min = 5, max = 100, message = "메뉴소개는 5~100자 사이로 입력해주세요.")
        private String intro;

        private Store store;

        public Menu toEntity(Store store) {
            return Menu.builder()
                    .thumbnail(thumbnail)
                    .name(name)
                    .category(CustomEnumUtil.toMenuCategoryEnumFormat(category))
                    .price(price)
                    .intro(intro)
                    .store(store)
                    .isClosure(false)
                    .build();
        }
    }

    /* 승현 작업 종료 */

}
