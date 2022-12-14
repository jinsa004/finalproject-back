package shop.mtcoding.finalproject.web;

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
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewReqDto.InsertReportReviewReqDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewReqDto.ResolveReportReviewReqDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewRespDto.DetailReportReviewRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewRespDto.ResolveAcceptReportReviewRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewRespDto.ResolveRefuseReportReviewRespDto;
import shop.mtcoding.finalproject.service.ReportReviewService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class ReportReviewController {

    private final ReportReviewService reportReviewService;

    /* 성진 작업 시작@ */

    @PutMapping("/admin/review/{reportReviewId}/refuse")
    public ResponseEntity<?> resolveRefuseReportReview(@RequestBody ResolveReportReviewReqDto resolveReportReviewReqDto,
            @PathVariable Long reportReviewId) {
        ResolveRefuseReportReviewRespDto resolveReportReviewRespDto = reportReviewService
                .resolveRefuseReportReview(resolveReportReviewReqDto, reportReviewId);
        return new ResponseEntity<>(new ResponseDto<>(1, "신고리뷰 기각하기 기능 성공", resolveReportReviewRespDto), HttpStatus.OK);
    }

    @PutMapping("/admin/review/{reportReviewId}/accept")
    public ResponseEntity<?> resolveAcceptReportReview(@RequestBody ResolveReportReviewReqDto resolveReportReviewReqDto,
            @PathVariable Long reportReviewId) {
        ResolveAcceptReportReviewRespDto resolveReportReviewRespDto = reportReviewService
                .resolveAcceptReportReview(resolveReportReviewReqDto, reportReviewId);
        return new ResponseEntity<>(new ResponseDto<>(1, "신고리뷰 수용하기 기능 성공", resolveReportReviewRespDto), HttpStatus.OK);
    }

    @GetMapping("/admin/review/{reportReviewId}/report/detail")
    public ResponseEntity<?> getDetailReportReview(@PathVariable Long reportReviewId) {
        DetailReportReviewRespDto detailReportReviewRespDto = reportReviewService.detailReportReview(reportReviewId);
        return new ResponseEntity<>(new ResponseDto<>(1, "신고리뷰 상세보기 기능 성공", detailReportReviewRespDto), HttpStatus.OK);
    }

    @GetMapping("/admin/review/report/list")
    public ResponseEntity<?> getReportReviewList() {
        return new ResponseEntity<>(new ResponseDto<>(1, "신고리뷰 목록보기 기능 성공", reportReviewService.reportReviewList()),
                HttpStatus.OK);
    }

    /* 승현 작업 시작 */

    // 내 신고리뷰 목록보기
    @GetMapping("/user/{userId}/store/{storeId}/review/report")
    public ResponseEntity<?> findAllByStoreId(@PathVariable Long storeId, @PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "신고한 리뷰 목록보기 완료", reportReviewService.findAllByStoreId(storeId,
                userId)), HttpStatus.OK);
    }

    // 해당 리뷰 신고하기
    @PostMapping("/user/{userId}/store/review/{customerReviewId}/report")
    public ResponseEntity<?> insert(@PathVariable Long customerReviewId, @PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody InsertReportReviewReqDto insertReportReviewReqDto) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(
                new ResponseDto<>(1, "리뷰 신고 완료",
                        reportReviewService.insertReportReview(insertReportReviewReqDto, customerReviewId, userId)),
                HttpStatus.CREATED);
    }

    /* 승현 작업 종료 */

}
