package shop.mtcoding.finalproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.order.OrderRepository;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewReqDto.InsertCustomerReviewReqDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewRespDto.InsertCustomerReviewRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CustomerReviewService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final CustomerReviewRepository customerReviewRepository;
    private final OrderRepository orderRepository;

    public InsertCustomerReviewRespDto 고객리뷰_등록하기(InsertCustomerReviewReqDto insertCustomerReviewReqDto, Long orderId,
            LoginUser loginUser) {
        // 1. 해당 가게에 order를 했는지 검증
        Order orderPS = orderRepository.findById(orderId)
                .orElseThrow(() -> new CustomApiException("해당 가게에 주문하지 않았습니다.", HttpStatus.BAD_REQUEST));
        // 2. order의 userId와 세션의 userId가 같은지 검증
        if (orderPS.getUser().getId() != loginUser.getUser().getId()) {
            throw new CustomApiException("리뷰를 작성할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        // 3. 배달완료가 되었는지 체크
        if (OrderStateEnum.valueOf(orderPS.getState().toString()) != OrderStateEnum.COMPLETE) {
            throw new CustomApiException("배달이 완료되지 않았습니다.", HttpStatus.FORBIDDEN);
        }
        // 4. 핵심로직
        CustomerReview customerReview = insertCustomerReviewReqDto.toEntity(orderPS);
        CustomerReview customerReviewPS = customerReviewRepository.save(customerReview);

        return new InsertCustomerReviewRespDto(customerReviewPS);
    }

}
