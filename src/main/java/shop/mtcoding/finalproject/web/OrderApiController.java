package shop.mtcoding.finalproject.web;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.order.OrderReqDto.UpdateToCancleOrderReqDto;
import shop.mtcoding.finalproject.dto.order.OrderRespDto.ShowOrderListRespDto;
import shop.mtcoding.finalproject.service.OrderService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class OrderApiController {

    private final OrderService orderService;

    /* 승현 작업 시작 */

    @PutMapping("/store/{storeId}/order/{orderId}/state")
    public ResponseEntity<?> UpdateOrderByUserIdToComplete(@PathVariable Long storeId, @PathVariable Long orderId,
            @RequestBody UpdateToCancleOrderReqDto updateToCancleOrderReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        updateToCancleOrderReqDto.setUserId(loginUser.getUser().getId());
        updateToCancleOrderReqDto.setOrderId(orderId);
        updateToCancleOrderReqDto.setStoreId(storeId);
        String state = orderService.updatToState(updateToCancleOrderReqDto);
        return new ResponseEntity<>(new ResponseDto<>("주문상태 변경완료", state), HttpStatus.OK);
    }

    @GetMapping("/store/{storeId}/order")
    public ResponseEntity<?> findAllByStoreId(@PathVariable Long storeId,
            @AuthenticationPrincipal LoginUser loginUser) {
        List<ShowOrderListRespDto> showOrderListRespDtoList = orderService.findAllByStoreId(storeId,
                loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("주문 목록보기 완료", showOrderListRespDtoList), HttpStatus.OK);
    }

    /* 승현 작업 종료 */
}
