package shop.mtcoding.finalproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.domain.order.OrderRepository;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class OrderService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final OrderRepository orderRepository;

    // "/api/order/{userId}"
    public void 주문하기(InsertOrderReqDto insertOrderReqDto, Long userId) {
        // 1.

        //

        //
    }
}
