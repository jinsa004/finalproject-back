package shop.mtcoding.finalproject.util;

import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;

public class CustomEnumUtil {

     public static StoreCategoryEnum toCategoryEnumFormat(String storeCatrgoryEnum) {
          return StoreCategoryEnum.valueOf(storeCatrgoryEnum);
     }

}
