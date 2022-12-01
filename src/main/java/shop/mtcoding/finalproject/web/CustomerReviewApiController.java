package shop.mtcoding.finalproject.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewReqDto.InsertCustomerReviewReqDto;
import shop.mtcoding.finalproject.dto.customerReview.CustomerReviewRespDto.InsertCustomerReviewRespDto;
import shop.mtcoding.finalproject.service.CustomerReviewService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CustomerReviewApiController {
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final CustomerReviewService customerReviewService;

    @PostMapping("/review/{orderId}/insert")
    public ResponseEntity<?> userDetail(@RequestBody InsertCustomerReviewReqDto insertCustomerReviewReqDto,
            @PathVariable Long orderId, @AuthenticationPrincipal LoginUser loginUser) {
        InsertCustomerReviewRespDto insertCustomerReviewRespDto = customerReviewService
                .고객리뷰_등록하기(insertCustomerReviewReqDto, orderId, loginUser);
        return new ResponseEntity<>(new ResponseDto<>("리뷰 등록하기 완료", insertCustomerReviewRespDto), HttpStatus.OK);
    }

}
