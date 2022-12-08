package shop.mtcoding.finalproject.web;

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
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewReqDto.InsertReportReviewReqDto;
import shop.mtcoding.finalproject.service.ReportReviewService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReportReviewController {

    private final ReportReviewService reportReviewService;

    /* 승현 작업 시작 */

    @PostMapping("/review/{reviewId}/report")
    public ResponseEntity<?> insert(@PathVariable Long reviewId,
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody InsertReportReviewReqDto insertReportReviewReqDto) {
        insertReportReviewReqDto.setReviewId(reviewId);
        insertReportReviewReqDto.setUserId(loginUser.getUser().getId());
        reportReviewService.insert(insertReportReviewReqDto);
        return new ResponseEntity<>(new ResponseDto<>("리뷰 신고 완료", null), HttpStatus.CREATED);
    }

    /* 승현 작업 종료 */

}
