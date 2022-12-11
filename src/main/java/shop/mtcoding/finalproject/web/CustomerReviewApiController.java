package shop.mtcoding.finalproject.web;

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
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewReqDto.InsertCustomerReviewReqDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewRespDto.CustomerReviewListRespDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewRespDto.InsertCustomerReviewRespDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewRespDto.StoreReviewListRespDto;
import shop.mtcoding.finalproject.service.CustomerReviewService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CustomerReviewApiController {
        private final Logger log = LoggerFactory.getLogger(getClass());
        private final CustomerReviewService customerReviewService;

        @GetMapping("/user/{userId}/store/{storeId}/review/list")
        public ResponseEntity<?> getCustomerReviewToStore(@PathVariable Long storeId, @PathVariable Long userId,
                        @AuthenticationPrincipal LoginUser loginUser) {
                // 요청 userId값과 세션 값 비교 후 검증
                if (userId != loginUser.getUser().getId()) {
                        throw new CustomApiException("권한이 없습니다", HttpStatus.OK);
                }
                StoreReviewListRespDto storeReviewListRespDto = customerReviewService.storeCustomerReviewList(storeId);
                return new ResponseEntity<>(new ResponseDto<>("가게 리뷰 목록보기 성공", storeReviewListRespDto), HttpStatus.OK);
        }

        @PostMapping("/user/{userId}/store/{storeId}/oreder/{orderId}/review/save")
        public ResponseEntity<?> insertCustomerReview(
                        @RequestBody InsertCustomerReviewReqDto insertCustomerReviewReqDto,
                        @PathVariable Long orderId, @PathVariable Long storeId, @PathVariable Long userId,
                        @AuthenticationPrincipal LoginUser loginUser) {
                // 요청 userId값과 세션 값 비교 후 검증
                // if (userId != loginUser.getUser().getId()) {
                // throw new CustomApiException("권한이 없습니다", HttpStatus.FORBIDDEN);
                // }
                InsertCustomerReviewRespDto insertCustomerReviewRespDto = customerReviewService
                                .saveCustomerReview(insertCustomerReviewReqDto, storeId, orderId, loginUser);
                return new ResponseEntity<>(new ResponseDto<>("리뷰 등록하기 완료", insertCustomerReviewRespDto),
                                HttpStatus.CREATED);
        }

        @GetMapping("/user/{userId}/review/list")
        public ResponseEntity<?> findByUserIdToCustomerReview(@PathVariable Long userId,
                        @AuthenticationPrincipal LoginUser loginUser) {
                // 요청 userId값과 세션 값 비교 후 검증
                if (userId != loginUser.getUser().getId()) {
                        throw new CustomApiException("권한이 없습니다", HttpStatus.FORBIDDEN);
                }
                CustomerReviewListRespDto CustomerReviewListRespDto = customerReviewService.MyCustomerReviewList(userId,
                                loginUser);
                return new ResponseEntity<>(new ResponseDto<>("내 리뷰 목록보기 성공", CustomerReviewListRespDto),
                                HttpStatus.OK);
        }

        @PutMapping("/user/{userId}/review/{reviewId}/delete")
        public ResponseEntity<?> deleteCustomerReview(@PathVariable Long userId, @PathVariable Long reviewId,
                        @AuthenticationPrincipal LoginUser loginUser) {
                // 요청 userId값과 세션 값 비교 후 검증
                if (userId != loginUser.getUser().getId()) {
                        throw new CustomApiException("권한이 없습니다", HttpStatus.FORBIDDEN);
                }
                customerReviewService.deleteMyCustomerReview(reviewId, userId, loginUser);
                return new ResponseEntity<>(new ResponseDto<>("리뷰 삭제하기 성공", null), HttpStatus.OK);
        }
}
