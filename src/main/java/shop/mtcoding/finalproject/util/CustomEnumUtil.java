package shop.mtcoding.finalproject.util;

import shop.mtcoding.finalproject.config.enums.MenuCategoryEnum;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.config.enums.ReportReasonEnum;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;

public class CustomEnumUtil {

     public static StoreCategoryEnum toStoreCategoryEnumFormat(String storeCatrgoryEnum) {
          StoreCategoryEnum[] category = StoreCategoryEnum.values();
          StoreCategoryEnum result = null;
          for (int i = 0; i < category.length; i++) {
               if (category[i].getCategory().equals(storeCatrgoryEnum)) {
                    result = category[i];
               }
          }
          return result;
     }

     public static MenuCategoryEnum toMenuCategoryEnumFormat(String menuCategoryEnum) {
          MenuCategoryEnum[] category = MenuCategoryEnum.values();
          MenuCategoryEnum result = null;
          for (int i = 0; i < category.length; i++) {
               if (category[i].getCategory().equals(menuCategoryEnum)) {
                    result = category[i];
               }
          }
          return result;
     }

     public static OrderStateEnum toOrderStateEnumFormat(String orderStateEnum) {
          OrderStateEnum[] reason = OrderStateEnum.values();
          OrderStateEnum result = null;
          for (int i = 0; i < reason.length; i++) {
               if (reason[i].getState().equals(orderStateEnum)) {
                    result = reason[i];
               }
          }
          return result;
     }

     public static ReportReasonEnum toReportReasonEnumFormat(String reportReasonEnum) {
          ReportReasonEnum[] reason = ReportReasonEnum.values();
          ReportReasonEnum result = null;
          for (int i = 0; i < reason.length; i++) {
               if (reason[i].getReason().equals(reportReasonEnum)) {
                    result = reason[i];
               }
          }
          return result;
     }

}
