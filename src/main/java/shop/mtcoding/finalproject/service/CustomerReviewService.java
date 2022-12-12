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
import shop.mtcoding.finalproject.domain.customerReview.CustomerMenuInterface;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewInterface;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.order.Order;
import shop.mtcoding.finalproject.domain.order.OrderRepository;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewReqDto.InsertCustomerReviewReqDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewRespDto.CustomerReviewListRespDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewRespDto.InsertCustomerReviewRespDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewRespDto.StoreReviewListRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CustomerReviewService {
        private final Logger log = LoggerFactory.getLogger(getClass());
        private final UserRepository userRepository;
        private final StoreRepository storeRepository;
        private final CustomerReviewRepository customerReviewRepository;
        private final OrderRepository orderRepository;
        private final CeoReviewRepository ceoReviewRepository;

        // 가게상세보기 -> 가게리뷰 목록보기 기능
        public StoreReviewListRespDto storeCustomerReviewList(Long storeId) {
                // 1. 가게에 맞는 리뷰 정보(작성한 유저정보 포함) + 사장님 답글 정보
                log.debug("디버그 : 서비스 진입");
                List<CustomerReviewInterface> customerReviewDtoList = customerReviewRepository
                                .findByCustomerReviewToStoreId(storeId);
                log.debug("디버그 : 가게 리뷰 목록보기 잘 가져오나? :" + customerReviewDtoList.get(0).getContent());
                // 2. 해당 리뷰에 맞는 메뉴명 뿌리기
                List<CustomerMenuInterface> customerMenuDtoList = customerReviewRepository
                                .findByMenuNameToStoreId(storeId);
                log.debug("디버그 : 메뉴명 잘뜨남? : " + customerMenuDtoList.get(0).getMenuName());
                // 3. DTO 응답
                log.debug("디버그 : DTO응답 진입전");
                StoreReviewListRespDto storeReviewListRespDto = new StoreReviewListRespDto(customerReviewDtoList,
                                customerMenuDtoList);
                log.debug("디버그 : DTO 매핑됐나? "
                                + storeReviewListRespDto.getCustomerReviewDtoList().get(0).getCustomerMenuDtos().get(1)
                                                .getMenuName());

                return storeReviewListRespDto;
        }

        @Transactional // 고객 리뷰 등록하기 기능
        public InsertCustomerReviewRespDto saveCustomerReview(InsertCustomerReviewReqDto insertCustomerReviewReqDto,
                        Long storeId, Long orderId, Long userId) {
                // 0. 해당 유저가 존재하는지 검증
                User userPS = userRepository.findById(userId)
                                .orElseThrow(() -> new CustomApiException("해당 유저가 존재하지 않았습니다.",
                                                HttpStatus.BAD_REQUEST));
                // 1. 해당 가게가 있는지 검증
                Store storePS = storeRepository.findById(storeId)
                                .orElseThrow(() -> new CustomApiException("해당 가게가 존재하지 않았습니다.",
                                                HttpStatus.BAD_REQUEST));
                // 2. 해당 가게에 order를 했는지 검증
                Order orderPS = orderRepository.findById(orderId)
                                .orElseThrow(() -> new CustomApiException("해당 가게에 주문하지 않았습니다.",
                                                HttpStatus.BAD_REQUEST));
                // 3. order의 userId와 세션의 userId가 같은지 검증
                if (orderPS.getUser().getId() != userId) {
                        throw new CustomApiException("리뷰를 작성할 권한이 없습니다.", HttpStatus.FORBIDDEN);
                }
                // 4. 배달완료가 되었는지 체크
                if (OrderStateEnum.valueOf(orderPS.getState().toString()) != OrderStateEnum.COMPLETE) {
                        throw new CustomApiException("배달이 완료되지 않았습니다.", HttpStatus.FORBIDDEN);
                }
                // 5. 핵심로직
                CustomerReview customerReview = insertCustomerReviewReqDto.toEntity(userPS, storePS);
                CustomerReview customerReviewPS = customerReviewRepository.save(customerReview);
                return new InsertCustomerReviewRespDto(customerReviewPS);
        }

        // 내 리뷰 목록보기 기능(앱 사용자입장)
        public CustomerReviewListRespDto myCustomerReviewList(Long userId, LoginUser loginUser) {
                // 1 해당 유저의 review가 있는지 체크
                User userPS = userRepository.findById(userId)
                                .orElseThrow(() -> new CustomApiException("유저정보가 없습니다.",
                                                HttpStatus.BAD_REQUEST));
                // 2 주문 내역체크
                Order orderPS = orderRepository.findById(userPS.getId())
                                .orElseThrow(() -> new CustomApiException("주문내역이 없습니다.", HttpStatus.BAD_REQUEST));
                // 3 핵심로직 내 리뷰 목록보기
                List<CustomerReview> customerReviewList = customerReviewRepository
                                .findReviewListByUserId(userPS.getId());
                // DTO 응답
                return new CustomerReviewListRespDto(customerReviewList, orderPS, userPS);
        }

        // 내 리뷰 삭제하기(앱 사용자 입장)
        public void deleteMyCustomerReview(Long reviewId, Long userId, LoginUser loginUser) {
                // 1 핵심로직 해당 리뷰 셀렉
                log.debug("디버그 : 리뷰 정보 체크 전");
                CustomerReview customerReviewPS = customerReviewRepository.findById(reviewId)
                                .orElseThrow(() -> new CustomApiException("리뷰 정보가 없습니다.",
                                                HttpStatus.BAD_REQUEST));
                log.debug("디버그 : 리뷰 정보 체크 후");
                // 2 리뷰 비활성화하기
                customerReviewPS.비활성화하기();
        }
}
