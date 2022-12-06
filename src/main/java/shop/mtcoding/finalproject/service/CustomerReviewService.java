package shop.mtcoding.finalproject.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.config.enums.OrderStateEnum;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewRepository;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.order.OrderRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewReqDto.InsertCustomerReviewReqDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewRespDto.CustomerReviewListRespDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewRespDto.InsertCustomerReviewRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CustomerReviewService {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final UserRepository userRepository;
    private final CustomerReviewRepository customerReviewRepository;
    private final OrderRepository orderRepository;
    private final CeoReviewRepository ceoReviewRepository;

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

    public CustomerReviewListRespDto 내_리뷰_목록하기(Long userId, LoginUser loginUser) {
        // 1 해당 유저의 review가 있는지 체크
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("유저정보가 없습니다.",
                        HttpStatus.BAD_REQUEST));
        // 2 유저 권한체크
        if (userPS.getId() != loginUser.getUser().getId()) {
            throw new CustomApiException("해당 리뷰를 관리할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        Order orderPS = orderRepository.findById(userPS.getId())
                .orElseThrow(() -> new CustomApiException("주문내역이 없습니다.", HttpStatus.BAD_REQUEST));
        // 3 핵심로직 내 리뷰 목록보기
        List<CustomerReview> customerReviewList = customerReviewRepository.findReviewListByUserId(userPS.getId());
        // DTO 응답
        return new CustomerReviewListRespDto(customerReviewList, orderPS, userPS);
    }

    public void 내_리뷰_삭제하기(Long reviewId, Long userId, LoginUser loginUser) {
        // 1 해당 유저의 review가 있는지 체크
        log.debug("디버그 : 유저 정보 체크 전");
        User userPS = userRepository.findById(userId)
                .orElseThrow(() -> new CustomApiException("유저 정보가 없습니다.",
                        HttpStatus.BAD_REQUEST));
        log.debug("디버그 : 유저 정보 체크 후");
        // 2 유저 권한체크
        log.debug("디버그 : 유저 권한 체크 전 ");
        if (userPS.getId() != loginUser.getUser().getId()) {
            throw new CustomApiException("해당 리뷰를 관리할 권한이 없습니다.", HttpStatus.FORBIDDEN);
        }
        log.debug("디버그 : 유저 권한 체크 후");
        // 3 핵심로직 해당 리뷰 셀렉
        log.debug("디버그 : 리뷰 정보 체크 전");
        CustomerReview customerReviewPS = customerReviewRepository.findById(reviewId)
                .orElseThrow(() -> new CustomApiException("리뷰 정보가 없습니다.",
                        HttpStatus.BAD_REQUEST));
        log.debug("디버그 : 리뷰 정보 체크 후");
        // 4 리뷰 비활성화하기
        customerReviewPS.비활성화하기();
    }
}