package shop.mtcoding.finalproject.dto.menu;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.util.CustomBase64ConvertUtil;
import shop.mtcoding.finalproject.util.CustomEnumUtil;

public class MenuReqDto {

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class CeoUpdateMenuStateReqDto {
        private boolean isClosure;
    }

    @Getter
    @Setter
    public static class CeoUpdateMenuReqDto {

        private String thumbnail;

        private String category;

        private String name;

        private int price;

        private String intro;

        private Store store;

        public Menu toEntity() {
            return Menu.builder()
                    .thumbnail(CustomBase64ConvertUtil.convertToByte(thumbnail))
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

        private String category;

        private String name;

        private int price;

        private String intro;

        private Store store;

        public Menu toEntity(Store store) {
            return Menu.builder()
                    .thumbnail(CustomBase64ConvertUtil.convertToByte(thumbnail))
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
