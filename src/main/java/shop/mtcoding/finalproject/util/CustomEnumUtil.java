package shop.mtcoding.finalproject.util;

import org.hibernate.NullPrecedence;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;

public class CustomEnumUtil {

     private final static Logger log = LoggerFactory.getLogger(CustomEnumUtil.class);

     public static StoreCategoryEnum toCategoryEnumFormat(String storeCatrgoryEnum) {
          StoreCategoryEnum[] category = StoreCategoryEnum.values();
          StoreCategoryEnum result = null;
          for (int i = 0; i < StoreCategoryEnum.values().length; i++) {
               if (category[i].getCategory().equals(storeCatrgoryEnum)) {
                    result = category[i];
               }
          }
          return result;
     }

}
