package shop.mtcoding.finalproject.dto.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.enums.MenuCategoryEnum;
import shop.mtcoding.finalproject.domain.menu.Menu;

public class MenuRespDto {

    /* 성진 작업 시작 */

    @Getter
    @Setter
    public static class CustomerDetailMenuRespDto {
        private String name;
        private String thumbnail;
        private String intro;
        private String price;

        public CustomerDetailMenuRespDto(Menu menu) {
            this.name = menu.getName();
            this.thumbnail = menu.getThumbnail();
            this.intro = menu.getIntro();
            this.price = menu.getPrice();
        }

    }

    @Getter
    @Setter
    public static class CustomerMenuListRespDto {
        private List<MenuDto> menus = new ArrayList<>();

        public CustomerMenuListRespDto(List<Menu> menus) {
            this.menus = menus.stream().map((menu) -> new MenuDto(menu)).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class MenuDto {
            private String name;
            private String intro;
            private String price;
            private String thumbnail;
            private MenuCategoryEnum category;

            public MenuDto(Menu menu) {
                this.name = menu.getName();
                this.intro = menu.getIntro();
                this.price = menu.getPrice();
                this.thumbnail = menu.getThumbnail();
                this.category = menu.getCategory();
            }

        }
    }

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class UpdateMenuRespDto {

        private MenuCategoryEnum category;
        private String name;
        private String intro;
        private String price;
        private String thumbnail;
        private boolean isClosure;

        public UpdateMenuRespDto(Menu menu) {
            this.category = menu.getCategory();
            this.name = menu.getName();
            this.intro = menu.getIntro();
            this.price = menu.getPrice();
            this.thumbnail = menu.getThumbnail();
            this.isClosure = menu.isClosure();
        }

    }

    @Getter
    @Setter
    public static class DetailMenuRespDto {

        private MenuCategoryEnum category;
        private String name;
        private String intro;
        private String price;
        private String thumbnail;
        private boolean isClosure;

        public DetailMenuRespDto(Menu menu) {
            this.category = menu.getCategory();
            this.name = menu.getName();
            this.intro = menu.getIntro();
            this.price = menu.getPrice();
            this.thumbnail = menu.getThumbnail();
            this.isClosure = menu.isClosure();
        }

    }

    @Getter
    @Setter
    public static class ShowMenuRespDto {

        private Long id;
        private String thumbnail;
        private String name;
        private String price;
        private boolean isClosure;

        public ShowMenuRespDto(Menu menu) {
            this.id = menu.getId();
            this.thumbnail = menu.getThumbnail();
            this.name = menu.getName();
            this.price = menu.getPrice();
            this.isClosure = menu.isClosure;
        }

    }

    @Getter
    @Setter
    public static class InsertMenuRespDto {

        private MenuCategoryEnum category;
        private String name;
        private String intro;
        private String price;
        private String thumbnail;
        private boolean isClosure;

        public InsertMenuRespDto(Menu menu) {
            this.category = menu.getCategory();
            this.name = menu.getName();
            this.intro = menu.getIntro();
            this.price = menu.getPrice();
            this.thumbnail = menu.getThumbnail();
            this.isClosure = menu.isClosure();
        }

    }

    /* 승현 작업 종료 */

}
