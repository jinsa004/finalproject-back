package shop.mtcoding.finalproject.dto.order;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.util.CustomEnumUtil;

public class OrderReqDto {

    /* 승현 작업 시작 */
    @Getter
    @Setter
    public static class UpdateToCancleOrderReqDto {
        private Long userId;
        private Long storeId;
        private Long orderId;
        private String state;
        private String reason;

        public Order toEntity() {
            return Order.builder()
                    .state(CustomEnumUtil.toOrderStateEnumFormat(state))
                    .reason(reason)
                    .build();
        }
    }

    /* 승현 작업 완료 */
}
