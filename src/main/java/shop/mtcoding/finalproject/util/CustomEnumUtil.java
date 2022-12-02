package shop.mtcoding.finalproject.util;

import shop.mtcoding.finalproject.config.enums.MenuCategoryEnum;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;

public class CustomEnumUtil {

     public static StoreCategoryEnum toStoreCategoryEnumFormat(String storeCatrgoryEnum) {
          StoreCategoryEnum[] category = StoreCategoryEnum.values();
          StoreCategoryEnum result = null;
          for (int i = 0; i < StoreCategoryEnum.values().length; i++) {
               if (category[i].getCategory().equals(storeCatrgoryEnum)) {
                    result = category[i];
               }
          }
          return result;
     }

     public static MenuCategoryEnum toMenuCategoryEnumFormat(String menuCategoryEnum) {
          MenuCategoryEnum[] category = MenuCategoryEnum.values();
          MenuCategoryEnum result = null;
          for (int i = 0; i < MenuCategoryEnum.values().length; i++) {
               if (category[i].getCategory().equals(menuCategoryEnum)) {
                    result = category[i];
               }
          }
          return result;
     }

}
