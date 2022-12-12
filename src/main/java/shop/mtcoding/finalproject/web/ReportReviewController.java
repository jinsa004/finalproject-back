package shop.mtcoding.finalproject.web;

import java.util.List;

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
import shop.mtcoding.finalproject.domain.reportReview.ReportReviewRespDto;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewReqDto.InsertReportReviewReqDto;
import shop.mtcoding.finalproject.service.ReportReviewService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReportReviewController {

    private final ReportReviewService reportReviewService;

    /* 성진 작업 시작@ */

    @GetMapping("/admin/review/report/list")
    public ResponseEntity<?> getReportReviewList() {
        return new ResponseEntity<>(new ResponseDto<>("신고리뷰 목록보기 기능 성공", reportReviewService.reportReviewList()),
                HttpStatus.OK);
    }

    /* 승현 작업 시작 */
    // 내 신고리뷰 목록보기
    @GetMapping("/user/{userId}/store/{storeId}/review/report")
    public ResponseEntity<?> findAllByStoreId(@PathVariable Long storeId, @PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkAccount(userId);
        List<ReportReviewRespDto> reportReviewRespDtos = reportReviewService.findAllByStoreId(storeId,
                userId);
        return new ResponseEntity<>(new ResponseDto<>("신고한 리뷰 목록보기 완료", reportReviewRespDtos), HttpStatus.OK);
    }

    // 해당 리뷰 신고하기
    @PostMapping("/user/{userId}/review/{reviewId}/report")
    public ResponseEntity<?> insert(@PathVariable Long reviewId, @PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody InsertReportReviewReqDto insertReportReviewReqDto) {
        loginUser.getUser().checkAccount(userId);
        reportReviewService.insertReportReview(insertReportReviewReqDto, reviewId, userId);
        return new ResponseEntity<>(new ResponseDto<>("리뷰 신고 완료", null), HttpStatus.CREATED);
    }

    /* 승현 작업 종료 */

}
