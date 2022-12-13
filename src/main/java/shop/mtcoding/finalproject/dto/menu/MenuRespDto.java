package shop.mtcoding.finalproject.dto.menu;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.enums.MenuCategoryEnum;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.util.CustomBase64ConvertUtil;

public class MenuRespDto {

    /* 성진 작업 시작 */

    @Getter
    @Setter
    public static class CustomerDetailMenuRespDto {
        private String name;
        private String thumbnail;
        private String intro;
        private int price;

        public CustomerDetailMenuRespDto(Menu menu) {
            this.name = menu.getName();
            this.thumbnail = CustomBase64ConvertUtil.convertToString(menu.getThumbnail());
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
            private int price;
            private String thumbnail;
            private String category;

            public MenuDto(Menu menu) {
                this.name = menu.getName();
                this.intro = menu.getIntro();
                this.price = menu.getPrice();
                this.thumbnail = CustomBase64ConvertUtil.convertToString(menu.getThumbnail());
                this.category = menu.getCategory().getCategory();
            }

        }
    }

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class CeoUpdateMenuRespDto {

        private String category;
        private String name;
        private String intro;
        private int price;
        private String thumbnail;
        private boolean isClosure;

        public CeoUpdateMenuRespDto(Menu menu) {
            this.category = menu.getCategory().getCategory();
            this.name = menu.getName();
            this.intro = menu.getIntro();
            this.price = menu.getPrice();
            this.thumbnail = CustomBase64ConvertUtil.convertToString(menu.getThumbnail());
            this.isClosure = menu.isClosure();
        }

    }

    @Getter
    @Setter
    public static class CeoDetailMenuRespDto {

        private String category;
        private String name;
        private String intro;
        private int price;
        private String thumbnail;
        private boolean isClosure;

        public CeoDetailMenuRespDto(Menu menu) {
            this.category = menu.getCategory().getCategory();
            this.name = menu.getName();
            this.intro = menu.getIntro();
            this.price = menu.getPrice();
            this.thumbnail = CustomBase64ConvertUtil.convertToString(menu.getThumbnail());
            this.isClosure = menu.isClosure();
        }

    }

    @Getter
    @Setter
    public static class CeoShowMenuRespDto {

        private Long id;
        private String thumbnail;
        private String name;
        private int price;
        private boolean isClosure;

        public CeoShowMenuRespDto(Menu menu) {
            this.id = menu.getId();
            this.thumbnail = CustomBase64ConvertUtil.convertToString(menu.getThumbnail());
            this.name = menu.getName();
            this.price = menu.getPrice();
            this.isClosure = menu.isClosure;
        }

    }

    @Getter
    @Setter
    public static class CeoInsertMenuRespDto {

        private String category;
        private String name;
        private String intro;
        private int price;
        private String thumbnail;
        private boolean isClosure;

        public CeoInsertMenuRespDto(Menu menu) {
            this.category = menu.getCategory().getCategory();
            this.name = menu.getName();
            this.intro = menu.getIntro();
            this.price = menu.getPrice();
            this.thumbnail = CustomBase64ConvertUtil.convertToString(menu.getThumbnail());
            this.isClosure = menu.isClosure();
        }

    }

    /* 승현 작업 종료 */

}
