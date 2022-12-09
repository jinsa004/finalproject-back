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

        @GetMapping("/store/{storeId}/reviewList")
        public ResponseEntity<?> getCustomerReviewToStore(@PathVariable Long storeId) {
                StoreReviewListRespDto storeReviewListRespDto = customerReviewService.가게리뷰_목록보기(storeId);
                return new ResponseEntity<>(new ResponseDto<>("가게 리뷰 목록보기 성공", storeReviewListRespDto), HttpStatus.OK);
        }

        @PostMapping("/review/{orderId}/insert/{storeId}")
        public ResponseEntity<?> insertCustomerReview(
                        @RequestBody InsertCustomerReviewReqDto insertCustomerReviewReqDto,
                        @PathVariable Long orderId, @PathVariable Long storeId,
                        @AuthenticationPrincipal LoginUser loginUser) {
                InsertCustomerReviewRespDto insertCustomerReviewRespDto = customerReviewService
                                .고객리뷰_등록하기(insertCustomerReviewReqDto, storeId, orderId, loginUser);
                return new ResponseEntity<>(new ResponseDto<>("리뷰 등록하기 완료", insertCustomerReviewRespDto),
                                HttpStatus.CREATED);
        }

        @GetMapping("/review/{userId}")
        public ResponseEntity<?> findByUserIdToCustomerReview(@PathVariable Long userId,
                        @AuthenticationPrincipal LoginUser loginUser) {
                CustomerReviewListRespDto CustomerReviewListRespDto = customerReviewService.내_리뷰_목록보기(userId,
                                loginUser);
                return new ResponseEntity<>(new ResponseDto<>("내 리뷰 목록보기 성공",
                                CustomerReviewListRespDto), HttpStatus.OK);
        }

        @PutMapping("/review/{userId}/delete/{reviewId}")
        public ResponseEntity<?> deleteCustomerReview(@PathVariable Long userId, @PathVariable Long reviewId,
                        @AuthenticationPrincipal LoginUser loginUser) {
                customerReviewService.내_리뷰_삭제하기(reviewId, userId, loginUser);
                return new ResponseEntity<>(new ResponseDto<>("리뷰 삭제하기 성공", null), HttpStatus.OK);
        }
}
