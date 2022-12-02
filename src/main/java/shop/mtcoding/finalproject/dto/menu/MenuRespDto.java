package shop.mtcoding.finalproject.dto.menu;

import java.util.List;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.enums.MenuCategoryEnum;
import shop.mtcoding.finalproject.domain.menu.Menu;

public class MenuRespDto {

    /* 승현 작업 시작 */

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
            this.price = menu.getName();
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
