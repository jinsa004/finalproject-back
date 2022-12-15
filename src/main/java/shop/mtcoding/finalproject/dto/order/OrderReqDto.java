package shop.mtcoding.finalproject.dto.order;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetail;
import shop.mtcoding.finalproject.domain.payment.Payment;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.util.CustomEnumUtil;

public class OrderReqDto {

    /* 성진 작업 시작 */
    @Getter
    @Setter
    public static class InsertOrderReqDto {
        // 결제수단 1(카카오페이), 메뉴/수량(오더디테일리스트),
        private String comment;
        private String paymentName;
        private List<OrderDetail> orderDetailList;

        public Order toEntity(User user, Store store, Payment payment) {
            return Order.builder()
                    .comment(comment)
                    .state(OrderStateEnum.ORDER)
                    .reason(null)
                    .user(user)
                    .store(store)
                    .payment(payment)
                    .build();
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
