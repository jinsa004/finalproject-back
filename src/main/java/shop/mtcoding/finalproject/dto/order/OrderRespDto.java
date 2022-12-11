package shop.mtcoding.finalproject.dto.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetail;
import shop.mtcoding.finalproject.util.CustomDateUtil;

public class OrderRespDto {

    /* 성진 작업 시작 */
    @Getter
    @Setter
    public static class OrderHistoryListRespDto {
        private List<OrderDto> orders;

        public OrderHistoryListRespDto(List<Order> orders) {
            this.orders = orders.stream().map((order) -> new OrderDto(order)).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class OrderDto {
            private String name;
            private String intro;
            private String thumbnail;
            private String deliveryState;
            private String createdAt;

            public OrderDto(Order order) {
                this.name = order.getStore().getName();
                this.intro = order.getStore().getIntro();
                this.thumbnail = order.getStore().getThumbnail();
                this.deliveryState = order.getDeliveryStateEnum().getState();
                this.createdAt = CustomDateUtil.toStringFormat(order.getCreatedAt());
            }

        }

    }

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class ShowOrderListRespDto {

        private Long id;
        private String payment;
        private List<orderDetailRespDto> orderList;
        private String deliveryPrice;
        private String orderComment;
        private String deliveryState;
        private String userAdress;
        private String userPhone;
        private String orderTime;
        private String deliveryHour;
        private String orderState;
        private String completeTime;

        @Getter
        @Setter
        public static class orderDetailRespDto {
            private Long id;
            private int count;
            private String menuName;
            private String price;

            public orderDetailRespDto(OrderDetail orderDetail) {
                this.id = orderDetail.getId();
                this.count = orderDetail.getCount();
                this.menuName = orderDetail.getMenu().getName();
                this.price = orderDetail.getMenu().getPrice();
            }
        }

        public ShowOrderListRespDto(Order order, String price, List<OrderDetail> orderDetailPS) {
            this.id = order.getId();
            if (order.getPayment() == null) {
                this.payment = "";
            } else {
                this.payment = order.getPayment().getContent();
            }
            List<orderDetailRespDto> detailRespDtos = new ArrayList<>();
            for (int i = 0; i < orderDetailPS.size(); i++) {
                detailRespDtos.add(i, new orderDetailRespDto(orderDetailPS.get(i)));
            }
            this.orderList = detailRespDtos;
            this.deliveryPrice = order.getStore().getDeliveryCost();
            this.orderComment = order.getComment();
            this.userAdress = order.getUser().getAddress();
            this.userPhone = order.getUser().getPhone();
            this.orderTime = order.getCreatedAt().toString();
            this.deliveryHour = order.getStore().getDeliveryHour();
            this.deliveryState = order.getState().getState();
            if (order.getCompleteTime() == null) {
                this.completeTime = "";
            } else {
                this.completeTime = order.getCompleteTime().toString();
            }
        }

    }
    /* 승현 작업 완료 */
}
