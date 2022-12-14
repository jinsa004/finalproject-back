package shop.mtcoding.finalproject.service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.menu.MenuRepository;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.order.OrderRepository;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetail;
import shop.mtcoding.finalproject.domain.orderDetail.OrderDetailRepository;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.order.OrderReqDto.InsertOrderReqDto;
import shop.mtcoding.finalproject.dto.order.OrderReqDto.UpdateToCancleOrderReqDto;
import shop.mtcoding.finalproject.dto.order.OrderRespDto.DetailOrderHistoryRespDto;
import shop.mtcoding.finalproject.dto.order.OrderRespDto.DetailOrderStateRespDto;
import shop.mtcoding.finalproject.dto.order.OrderRespDto.InsertOrderRespDto;
import shop.mtcoding.finalproject.dto.order.OrderRespDto.OrderHistoryListRespDto;
import shop.mtcoding.finalproject.dto.order.OrderRespDto.ShowOrderListRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final MenuRepository menuRepository;

    /* 성진 작업 시작 */
    public DetailOrderHistoryRespDto detailOrderHistory(Long orderId, Long userId) {
        // 1. 유저 정보(유저 주소, 유저 번호) 셀렉
        log.debug("디버그 : 유저 셀렉 전");
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("해당 유저정보가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        log.debug("디버그 : 유저 셀렉 후" + userPS.getUsername());
        // 2. 해당 주문내역 상세보기 셀렉 (Order와 Store join fetch)
        log.debug("디버그 : 오더 셀렉 전");
        Order orderPS = orderRepository.findByOrderId(orderId);
        log.debug("디버그 : 오더 셀렉 후" + orderPS.getComment());
        // 3. orderId에 맞는 orderDetail 셀렉
        log.debug("디버그 : 오더디테일 셀렉 전");
        List<OrderDetail> orderDetailList = orderDetailRepository.findAllByOrderIdToOrderHistory(orderPS.getId());
        log.debug("디버그 : 오더디테일 셀렉 후" + orderDetailList.size());
        log.debug("디버그 : 오더디테일 셀렉 후" + orderDetailList.get(0).getMenu().getName());
        DetailOrderHistoryRespDto detailOrderHistoryRespDto = new DetailOrderHistoryRespDto(userPS, orderPS,
                orderDetailList);
        // 4. DTO 응답
        try {
            return detailOrderHistoryRespDto;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void deleteOrderHistory(Long orderId) {
        // 1. 주문 내역 셀렉
        Order orderPS = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomApiException("주문 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        // 2. 주문 내역을 가리기 isClosure false
        orderPS.delete(orderPS);
    }

    public OrderHistoryListRespDto orderHistoryList(Long userId) {
        // 1. 해당 유저id로 user정보 셀렉 1셀렉
        log.debug("디버그 : 유저 정보 셀렉 전");
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("해당 유저 정보가 없습니다.", HttpStatus.BAD_REQUEST));
        log.debug("디버그 : 유저 정보 셀렉 후");
        // 2. 해당 유저id로 Order 셀렉 2셀렉
        log.debug("디버그 : 오더 정보 셀렉 전");
        List<Order> orderList = orderRepository.findAllByUserId(userId);
        log.debug("디버그 : 오더 정보 셀렉 후");
        // 3. 주문내역이 없다면
        log.debug("디버그 : 주문 정보 검증 전");
        if (orderList.size() == 0) {
            throw new CustomApiException("주문 내역이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        log.debug("디버그 : 주문 정보 검증 후");
        // 4. DTO 응답
        log.debug("디버그 : DTO응답 전");
        OrderHistoryListRespDto orderHistoryListRespDto = new OrderHistoryListRespDto(orderList);
        log.debug("디버그 : DTO응답 후");
        try {
            return orderHistoryListRespDto;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public InsertOrderRespDto insertOrder(InsertOrderReqDto insertOrderReqDto, LoginUser loginUser, Long storeId) {

        // 1. 해당 가게가 존재하는지 검증
        Store storePS = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomApiException("가게 정보가 없습니다.", HttpStatus.BAD_REQUEST));
        // 2. 오더 테이블 저장하기(주문)
        Order orderPS = orderRepository.save(insertOrderReqDto.toEntity(loginUser.getUser(), storePS));
        // 3. 오더 디테일 저장하기(오더아이디에 맞춰서)
        for (int i = 0; i < insertOrderReqDto.getOrderDetailList().size(); i++) {
            Menu menuPS = menuRepository.findById(insertOrderReqDto.getOrderDetailList().get(i).getMenuId())
                    .orElseThrow(() -> new CustomApiException("메뉴 내역이 없습니다.", HttpStatus.BAD_REQUEST));
            OrderDetail orderDetailPS = orderDetailRepository
                    .save(insertOrderReqDto.getOrderDetailList().get(i).toEntity(orderPS, menuPS));
        }
        // 4. DTO 응답
        InsertOrderRespDto inserOrderRespDto = new InsertOrderRespDto(orderPS);
        return inserOrderRespDto;
    }

    /* 승현 작업 시작 */

    @Transactional
    public DetailOrderStateRespDto updatToState(UpdateToCancleOrderReqDto updateToCancleOrderReqDto, Long userId,
            Long storeId, Long orderId) {
        // 1. 가게 주인이 맞는지 체크하기
        Store storePS = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        if (storePS.getUser().getId() != userId) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        // 2. 주문 상태 확인하기
        Order order = orderRepository.findById(orderId).orElseThrow(
                () -> new CustomApiException("해당 주문이 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        // 3. 완료상태인지 체크하기
        LocalDateTime complateTime = null;
        if (updateToCancleOrderReqDto.getState().equals(OrderStateEnum.COMPLETE.getState())) {
            log.debug("디버그 : 통과함");
            complateTime = LocalDateTime.now();
        }
        // 4. 업데이트 하기
        Order orderPS = orderRepository.save(order.update(updateToCancleOrderReqDto.toEntity(complateTime)));
        return new DetailOrderStateRespDto(orderPS);
    }

    public List<ShowOrderListRespDto> findAllByStoreId(Long storeId, Long userId) {
        // 1. 가게주인이 맞는지 체크하기
        Store storePS = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        if (storePS.getUser().getId() != userId) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        // 2. 목록 받아오기
        LocalDateTime date = LocalDateTime.now();
        List<Order> orderPS = orderRepository.findAllByStoreId(storeId,
                date.format(DateTimeFormatter.ISO_LOCAL_DATE) + " 23:59:59");
        // 3. Dto에 담기
        List<ShowOrderListRespDto> showOrderListRespDtos = new ArrayList<>();
        for (int i = 0; i < orderPS.size(); i++) {
            List<OrderDetail> orderDetails = orderDetailRepository.findAllByOrderId(orderPS.get(i).getId());
            showOrderListRespDtos.add(i, new ShowOrderListRespDto(orderPS.get(i), null, orderDetails));
            // log.debug("디버그 : " + showOrderListRespDtos.get(i).getOrderList().get(0));
        }
        try {
            return showOrderListRespDtos;
        } catch (NoResultException e) {
            return null;
        }
    }

    /* 승현 작업 종료 */
}
