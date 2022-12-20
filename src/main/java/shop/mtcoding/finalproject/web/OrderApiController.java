package shop.mtcoding.finalproject.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.order.OrderReqDto.InsertOrderReqDto;
import shop.mtcoding.finalproject.dto.order.OrderReqDto.UpdateToCancleOrderReqDto;
import shop.mtcoding.finalproject.dto.order.OrderRespDto.InsertOrderRespDto;
import shop.mtcoding.finalproject.dto.order.OrderRespDto.OrderHistoryListRespDto;
import shop.mtcoding.finalproject.dto.order.OrderRespDto.ShowOrderListRespDto;
import shop.mtcoding.finalproject.service.OrderService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class OrderApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final OrderService orderService;

    @PostMapping("/user/{userId}/store/{storeId}/order/insert")
    public ResponseEntity<?> getOrder(@RequestBody InsertOrderReqDto insertOrderReqDto, @PathVariable Long userId,
            @PathVariable Long storeId, @AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : 컨트롤러 타냐? ");
        InsertOrderRespDto inserOrderRespDto = orderService.주문하기(insertOrderReqDto, loginUser, storeId);
        log.debug("디버그 : 컨트롤러 나갔냐?");
        return new ResponseEntity<>(new ResponseDto<>(1, "주문하기 성공", inserOrderRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/order/{orderId}/history/detail")
    public ResponseEntity<?> getOrderHistoryDetail(@PathVariable Long orderId, @PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(
                new ResponseDto<>(1, "주문내역 상세보기 성공", orderService.detailOrderHistory(orderId, userId)),
                HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/order/{orderId}/history/delete")
    public ResponseEntity<?> deleteOrderHistory(@PathVariable Long orderId, @PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        orderService.deleteOrderHistory(orderId);
        return new ResponseEntity<>(new ResponseDto<>(1, "주문내역 삭제하기 성공", null), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/order/history/list")
    public ResponseEntity<?> getOrderHistoryList(@PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        log.debug("디버그 : 컨트롤러 응답 전");
        OrderHistoryListRespDto orderHistoryListRespDto = orderService.orderHistoryList(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "주문내역 목록보기 성공", orderHistoryListRespDto), HttpStatus.OK);
    }

    /* 승현 작업 시작 */

    @PutMapping("/user/{userId}/store/{storeId}/order/{orderId}/state")
    public ResponseEntity<?> UpdateOrderByUserIdToComplete(@PathVariable Long storeId, @PathVariable Long orderId,
            @RequestBody UpdateToCancleOrderReqDto updateToCancleOrderReqDto, @PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "주문상태 변경완료",
                orderService.updatToState(updateToCancleOrderReqDto, userId, storeId, orderId)), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/store/{storeId}/order")
    public ResponseEntity<?> findAllByStoreId(@PathVariable Long storeId, @PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        List<ShowOrderListRespDto> showOrderListRespDtoList = orderService.findAllByStoreId(storeId, userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "주문 목록보기 완료", showOrderListRespDtoList), HttpStatus.OK);
    }

    /* 승현 작업 종료 */
}
