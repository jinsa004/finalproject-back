package shop.mtcoding.finalproject.service;

import java.util.List;
import java.util.Optional;

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

        // 가게상세보기 -> 가게리뷰 목록보기 기능
        public StoreReviewListRespDto storeCustomerReviewList(Long storeId) {
                // 1. 가게에 맞는 리뷰 정보(작성한 유저정보 포함) + 사장님 답글 정보
                log.debug("디버그 : 서비스 진입");
                List<CustomerReviewInterface> customerReviewDtoList = customerReviewRepository
                                .findByCustomerReviewToStoreId(storeId);

                log.debug("디버그 : 가게 리뷰 개수 : " + customerReviewDtoList.size());
                // 2. 해당 리뷰에 맞는 메뉴명 뿌리기
                log.debug("디버그 : 메뉴 리스트 셀렉 전 : ");
                List<CustomerMenuInterface> customerMenuDtoList = customerReviewRepository
                                .findByMenuNameToStoreId(storeId);
                log.debug("디버그 : 메뉴 리스트 크기 : " + customerMenuDtoList.toArray());
                if (customerReviewDtoList.size() == 0 && customerMenuDtoList.size() == 0) {
                        throw new CustomApiException("리뷰가 없습니다", HttpStatus.BAD_REQUEST);
                }
                // 3. DTO 응답
                log.debug("디버그 : DTO응답 진입전");
                StoreReviewListRespDto storeReviewListRespDto = new StoreReviewListRespDto(
                                customerReviewDtoList,
                                customerMenuDtoList);
                log.debug("디버그 : DTO응답 나왔냐 ");
                try {
                        return storeReviewListRespDto;
                } catch (NoResultException e) {
                        return null;
                }
        }

        @Transactional // 고객 리뷰 등록하기 기능
        public InsertCustomerReviewRespDto saveCustomerReview(InsertCustomerReviewReqDto insertCustomerReviewReqDto,
                        Long storeId, Long orderId, Long userId) {
                // 0. 해당 유저가 존재하는지 검증
                log.debug("디버그 : 유저 인증 전");
                User userPS = userRepository.findById(userId)
                                .orElseThrow(() -> new CustomApiException("해당 유저가 존재하지 않았습니다.",
                                                HttpStatus.BAD_REQUEST));
                log.debug("디버그 : 유저 인증 후");
                // 1. 해당 가게가 있는지 검증
                log.debug("디버그 : 유저 가게 전");
                Store storePS = storeRepository.findById(storeId)
                                .orElseThrow(() -> new CustomApiException("해당 가게가 존재하지 않았습니다.",
                                                HttpStatus.BAD_REQUEST));
                log.debug("디버그 : 유저 가게 후");
                // 2. 해당 가게에 order를 했는지 검증
                log.debug("디버그 : 유저 오더 전");
                Order orderPS = orderRepository.findById(orderId)
                                .orElseThrow(() -> new CustomApiException("해당 가게에 주문하지 않았습니다.",
                                                HttpStatus.BAD_REQUEST));
                log.debug("디버그 : 유저 오더 후");
                // 3. order의 userId와 세션의 userId가 같은지 검증
                log.debug("디버그 : 유저 오더 권한체크 전");
                if (orderPS.getUser().getId() != userId) {
                        throw new CustomApiException("리뷰를 작성할 권한이 없습니다.", HttpStatus.FORBIDDEN);
                }
                log.debug("디버그 : 유저 오더 권한체크 후");
                // 4. 배달완료가 되었는지 체크
                log.debug("디버그 : 유저 배달 완료 체크 전");
                if (OrderStateEnum.valueOf(orderPS.getState().toString()) != OrderStateEnum.COMPLETE) {
                        throw new CustomApiException("배달이 완료되지 않았습니다.", HttpStatus.FORBIDDEN);
                }
                log.debug("디버그 : 유저 배달 완료 체크 후");
                // 5. 해당 주문에 중복 리뷰 작성을 막는 로직
                Optional<CustomerReview> customerReviewOP = customerReviewRepository.findByOrderId(orderPS.getId());
                if (customerReviewOP.isPresent()) {
                        throw new CustomApiException("리뷰를 중복해서 작성하실 수 없습니다.", HttpStatus.BAD_REQUEST);
                }
                // 6. 핵심로직
                CustomerReview customerReview = insertCustomerReviewReqDto.toEntity(userPS, storePS, orderPS);
                log.debug("디버그 : 리뷰에 담기는 내용 : " + customerReview.getContent());
                CustomerReview customerReviewPS = customerReviewRepository.save(customerReview);
                log.debug("디버그 : 리뷰에 저장되는 내용 : " + customerReviewPS.getContent());
                try {
                        return new InsertCustomerReviewRespDto(customerReviewPS);
                } catch (NoResultException e) {
                        return null;
                }
        }

        // 내 리뷰 목록보기 기능(앱 사용자입장)
        public CustomerReviewListRespDto myCustomerReviewList(Long userId, LoginUser loginUser) {
                // 1 해당 유저의 review가 있는지 체크
                log.debug("디버그 : 유저정보 체크 전");
                User userPS = userRepository.findById(userId)
                                .orElseThrow(() -> new CustomApiException("유저정보가 없습니다.",
                                                HttpStatus.BAD_REQUEST));
                // 2 주문 내역체크
                log.debug("디버그 : 주문정보 체크 전");
                Order orderPS = orderRepository.findById(userId)
                                .orElseThrow(() -> new CustomApiException("주문내역이 없습니다.", HttpStatus.BAD_REQUEST));
                // 3 핵심로직 내 리뷰 목록보기
                log.debug("디버그 : 리뷰정보 체크 전");
                List<CustomerReview> customerReviewList = customerReviewRepository
                                .findReviewListByUserId(userId);
                log.debug("디버그 : 리뷰 목록리스트 개수 : " + customerReviewList.size());
                log.debug("디버그 : DTO 응답 체크 전");
                CustomerReviewListRespDto customerReviewListRespDto = new CustomerReviewListRespDto(customerReviewList,
                                userPS);
                log.debug("디버그 : dto 정보 체크 : " + customerReviewListRespDto.getCustomerReviews().size());
                return customerReviewListRespDto;

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
