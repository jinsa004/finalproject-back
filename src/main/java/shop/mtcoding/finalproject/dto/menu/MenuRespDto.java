package shop.mtcoding.finalproject.dto.menu;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.enums.MenuCategoryEnum;
import shop.mtcoding.finalproject.domain.menu.Menu;

public class MenuRespDto {

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class InsertMenuRespDto {
        private MenuCategoryEnum category;
        private String name;
        private String intro;
        private String price;
        private String thumbnail;

        public InsertMenuRespDto(Menu menu) {
            this.category = menu.getCategory();
            this.name = menu.getName();
            this.intro = menu.getIntro();
            this.price = menu.getPrice();
            this.thumbnail = menu.getThumbnail();
        }

    }

    /* 승현 작업 종료 */

}
