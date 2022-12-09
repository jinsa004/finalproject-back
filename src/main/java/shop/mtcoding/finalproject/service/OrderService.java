package shop.mtcoding.finalproject.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetailRepository;
import shop.mtcoding.finalproject.domain.payment.Payment;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.dto.order.OrderReqDto.UpdateToCancleOrderReqDto;
import shop.mtcoding.finalproject.dto.order.OrderRespDto.ShowOrderListRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final StoreRepository storeRepository;

    // "/api/order/{userId}"
    @Transactional
    public void 주문하기(InsertOrderReqDto insertOrderReqDto, LoginUser loginUser, Long storeId, Payment payment) {
        Store storePS = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomApiException("가게 정보가 없습니다.", HttpStatus.BAD_REQUEST));
        orderRepository.save(insertOrderReqDto.toEntity(loginUser.getUser(), storePS, payment));
    }

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

    @Transactional
    public String updatToState(UpdateToCancleOrderReqDto updateToCancleOrderReqDto) {

        // 1. 가게 주인이 맞는지 체크하기
        Store storePS = storeRepository.findById(updateToCancleOrderReqDto.getStoreId()).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        if (storePS.getUser().getId() != updateToCancleOrderReqDto.getUserId()) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. 주문 상태 확인하기
        Order order = orderRepository.findById(updateToCancleOrderReqDto.getOrderId()).orElseThrow(
                () -> new CustomApiException("해당 주문이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        // 3. 완료상태인지 체크하기
        LocalDateTime complateTime = null;
        if (updateToCancleOrderReqDto.getState().equals(OrderStateEnum.COMPLETE.getState())) {
            log.debug("디버그 : 통과함");
            complateTime = LocalDateTime.now();
        }

        // 4. 업데이트 하기
        Order orderPS = orderRepository.save(order.update(updateToCancleOrderReqDto.toEntity(complateTime)));

        return orderPS.getState().getState();
    }

    public List<ShowOrderListRespDto> findAllByStoreId(Long storeId, Long id) {

        // 1. 가게주인이 맞는지 체크하기
        Store storePS = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        if (storePS.getUser().getId() != id) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. 목록 받아오기
        List<Order> orderPS = orderRepository.findAllByStoreId(storeId);
        // List<OrderDetail> orderDetails =
        // orderDetailRepository.findAllByOrderId(orderPS.get(0).getId());

        // 3. Dto에 담기
        List<ShowOrderListRespDto> showOrderListRespDtos = new ArrayList<>();
        for (int i = 0; i < orderPS.size(); i++) {
            List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(orderPS.get(i).getId());
            showOrderListRespDtos.add(i, new ShowOrderListRespDto(orderPS.get(i), null, orderDetails));
            // log.debug("디버그 : " + showOrderListRespDtos.get(i).getOrderList().get(0));
        }

        return showOrderListRespDtos;
    }

    /* 승현 작업 종료 */
}
