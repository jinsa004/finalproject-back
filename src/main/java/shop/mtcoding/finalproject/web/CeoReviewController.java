package shop.mtcoding.finalproject.web;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.ceoReview.CeoReviewReqDto.InsertCeoReviewReqDto;
import shop.mtcoding.finalproject.service.CeoReviewService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class CeoReviewController {

    private final CeoReviewService ceoReviewService;

    @PostMapping("/user/{userId}/store/{customerReviewId}/review")
    public ResponseEntity<?> insertCeoReviewByCustomerId(@PathVariable Long customerReviewId, @PathVariable Long userId,
            @RequestBody InsertCeoReviewReqDto insertCeoReviewReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "사장님 리뷰달기 성공", ceoReviewService
                .insertByCustomerReviewId(insertCeoReviewReqDto, userId, customerReviewId)), HttpStatus.CREATED);
    }

    @GetMapping("/user/{userId}/store/{storeId}/review")
    public ResponseEntity<?> findAllReviewByStoreId(@PathVariable Long storeId, @PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(
                new ResponseDto<>(1, "가게별 리뷰목록보기 성공", ceoReviewService.findAllReviewByStoreId(storeId)), HttpStatus.OK);
    }
}
