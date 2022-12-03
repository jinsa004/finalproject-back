package shop.mtcoding.finalproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.order.OrderRepository;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetail;
import shop.mtcoding.finalproject.domain.payment.Payment;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.util.CustomDateUtil;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final OrderRepository orderRepository;
    private final StoreRepository storeRepository;

    // "/api/order/{userId}"
    @Transactional
    public void 주문하기(InsertOrderReqDto insertOrderReqDto, LoginUser loginUser, Long storeId) {
        Store storePS = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomApiException("가게 정보가 없습니다.", HttpStatus.BAD_REQUEST));
        orderRepository.save(insertOrderReqDto.toEntity(loginUser.getUser(), storePS));
    }

    @Getter
    @Setter
    public static class InsertOrderReqDto {
        private String comment;
        private String state;
        private String reason;
        private String paymentName;
        private String createdAt;
        private List<OrderDetail> orderDetailList;

        public Order toEntity(User user, Store store, Payment payment) {
            return Order.builder()
                    .comment(comment)
                    .state(null)
                    .reason(reason)
                    .user(user)
                    .store(store)
                    .payment(payment)
                    .build();
        }

    }
}
