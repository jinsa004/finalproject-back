package shop.mtcoding.finalproject.dto.order;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.enums.DeliveryStateEnum;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetail;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.util.CustomEnumUtil;

public class OrderReqDto {

    /* 성진 작업 시작 */
    @Getter
    @Setter
    public static class InsertOrderReqDto {
        private String comment;
        private String deliveryStateEnum;
        private List<OrderDetailDto> orderDetailList;

        public Order toEntity(User user, Store store) {
            return Order.builder()
                    .comment(comment)
                    .state(OrderStateEnum.ORDER)
                    .reason(null)
                    .deliveryStateEnum(DeliveryStateEnum.valueOf(deliveryStateEnum))
                    .deliveryTime(store.getDeliveryHour())
                    .user(user)
                    .store(store)
                    .build();
        }

        @Getter
        @Setter
        public static class OrderDetailDto {
            private Long menuId;
            private int count;

            public OrderDetail toEntity(Order order, Menu menu) {
                return OrderDetail.builder()
                        .count(count)
                        .order(order)
                        .menu(menu)
                        .build();
            }

        }

    }

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class FindStatsReqDto {

        private Long storeId;
        private String startTime;
        private String endTime;

    }

    @Getter
    @Setter
    public static class UpdateToCancleOrderReqDto {

        private String state;
        private String reason;
        private String deliveryTime;

        public Order toEntity(LocalDateTime localDateTime) {
            return Order.builder()
                    .state(CustomEnumUtil.toOrderStateEnumFormat(state))
                    .reason(reason)
                    .completeTime(localDateTime)
                    .deliveryTime(deliveryTime)
                    .build();
        }
    }

    /* 승현 작업 완료 */
}
