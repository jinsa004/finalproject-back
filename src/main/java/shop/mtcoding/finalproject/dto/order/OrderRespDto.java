package shop.mtcoding.finalproject.dto.order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetail;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.util.CustomBase64ConvertUtil;
import shop.mtcoding.finalproject.util.CustomDateUtil;

public class OrderRespDto {

    /* 성진 작업 시작 */

    @Getter
    @Setter
    public static class DetailOrderHistoryRespDto {// 나중에 페이먼트 필요함
        private String customerAddress;
        private String customerPhone;
        private String storeName;
        private String storePhone;
        private int deliveryCost;
        private Long orderId;
        private List<orderDetailDto> orderDetailDtos = new ArrayList<>();

        public DetailOrderHistoryRespDto(User user, Order order, List<OrderDetail> orderDetailList) {
            this.customerAddress = user.getAddress();
            this.customerPhone = user.getPhone();
            this.storeName = order.getStore().getName();
            this.storePhone = order.getStore().getPhone();
            this.deliveryCost = order.getStore().getDeliveryCost();
            this.orderId = order.getId();
            this.orderDetailDtos = orderDetailList.stream().map((orderDetail) -> new orderDetailDto(orderDetail))
                    .collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class orderDetailDto {
            private String orderState;
            private String createdAt;
            private String comment;
            private String menuName;
            private int price;
            private int count;

            public orderDetailDto(OrderDetail orderDetail) {
                this.orderState = orderDetail.getOrder().getState().getState();
                this.createdAt = CustomDateUtil.toStringFormat(orderDetail.getOrder().getCreatedAt());
                this.comment = orderDetail.getOrder().getComment();
                this.menuName = orderDetail.getMenu().getName();
                this.price = orderDetail.getMenu().getPrice();
                this.count = orderDetail.getCount();
            }

        }
    }

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
                this.thumbnail = CustomBase64ConvertUtil.convertToString(order.getStore().getThumbnail());
                this.deliveryState = order.getDeliveryStateEnum().getState();
                this.createdAt = CustomDateUtil.toStringFormat(order.getCreatedAt());
            }

        }

    }

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class DetailOrderStateRespDto {
        private String deliveryTime;
        private String orderState;
        private String reason;

        public DetailOrderStateRespDto(Order order) {
            this.deliveryTime = order.getDeliveryTime();
            this.orderState = order.getState().getState();
            this.reason = order.getReason();
        }

    }

    @Getter
    @Setter
    public static class ShowOrderListRespDto {

        private Long id;
        private String payment;
        private List<orderDetailRespDto> orderList;
        private int deliveryPrice;
        private String orderComment;
        private String deliveryState;
        private String userAddress;
        private String userPhone;
        private String orderTime;
        private String deliveryTime;
        private String orderState;
        private String completeTime;

        @Getter
        @Setter
        public static class orderDetailRespDto {
            private Long id;
            private int count;
            private String menuName;
            private int price;

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
            this.userAddress = order.getUser().getAddress();
            this.userPhone = order.getUser().getPhone();
            this.orderTime = order.getCreatedAt().toString();
            this.deliveryTime = order.getDeliveryTime();
            this.deliveryState = order.getDeliveryStateEnum().getState();
            this.orderState = order.getState().getState();
            if (order.getCompleteTime() == null) {
                this.completeTime = "";
            } else {
                this.completeTime = order.getCompleteTime().toString();
            }
        }

    }
    /* 승현 작업 완료 */
}
