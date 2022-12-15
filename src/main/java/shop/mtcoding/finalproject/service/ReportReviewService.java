package shop.mtcoding.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.enums.UserEnum;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewRepository;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.reportReview.ReportReview;
import shop.mtcoding.finalproject.domain.reportReview.ReportReviewRepository;
import shop.mtcoding.finalproject.domain.reportReview.ReportReviewRepositoryQuery;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.reportReview.ReportCeoInfoRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportCeoReviewRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportCustomerInfoRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewReqDto.InsertReportReviewReqDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewReqDto.ResolveReportReviewReqDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewRespDto.DetailReportReviewRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewRespDto.ReportReviewListRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewRespDto.ResolveReportReviewRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReportReviewService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ReportReviewRepository reportReviewRepository;
    private final CustomerReviewRepository customerReviewRepository;
    private final CeoReviewRepository ceoReviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReportReviewRepositoryQuery reportRepositoryQuery;

    /* 성진 작업 시작 */

    public ResolveReportReviewRespDto resolveReportReview(ResolveReportReviewReqDto resolveReportReviewReqDto,
            Long reportReviewId) {
        // 1. 해당 신고 리뷰가 존재하는지 체크
        ReportReview reportReviewPS = reportReviewRepository.findById(reportReviewId)
                .orElseThrow(() -> new CustomApiException("신고 리뷰가 없습니다", HttpStatus.BAD_REQUEST));
        // 2. 해당 신고 리뷰 처분
        reportReviewPS.resolve(resolveReportReviewReqDto);
        // 3. DTO 응답
        ResolveReportReviewRespDto resolveReportReviewRespDto = new ResolveReportReviewRespDto(reportReviewPS);
        try {
            return resolveReportReviewRespDto;
        } catch (NoResultException e) {
            return null;
        }
    }

    public DetailReportReviewRespDto detailReportReview(Long reportReviewId) {
        // 1. 해당 신고 리뷰가 있는지 체크
        ReportReview reportReviewPS = reportReviewRepository.findById(reportReviewId)
                .orElseThrow(() -> new CustomApiException("신고 리뷰가 없습니다.", HttpStatus.BAD_REQUEST));
        // 2. 신고리뷰 셀렉
        ReportCustomerInfoRespDto reportCustomerReviewRespDto = reportRepositoryQuery
                .findByReportReviewId(reportReviewId);
        // 3. 해당 리뷰 관련자 셀렉
        ReportCeoInfoRespDto reportCeoReviewRespDto = reportRepositoryQuery.findByReportReviewIdToCeo(reportReviewId);
        // 4. DTO 응답
        DetailReportReviewRespDto detailReportReviewRespDto = new DetailReportReviewRespDto(
                reportCustomerReviewRespDto,
                reportCeoReviewRespDto);
        try {
            return detailReportReviewRespDto;
        } catch (NoResultException e) {
            return null;
        }
    }

    public ReportReviewListRespDto reportReviewList() {
        // 1. 신고리뷰 목록찾기
        List<ReportReview> reportReviewList = new ArrayList<>();
        reportReviewList = reportReviewRepository.findAllByUser();
        // 2. DTO 응답
        ReportReviewListRespDto reportReviewListRespDto = new ReportReviewListRespDto(reportReviewList);
        try {
            return reportReviewListRespDto;
        } catch (NoResultException e) {
            return null;
        }
    }

    /* 승현 작업 시작 */
    public List<ReportCeoReviewRespDto> findAllByStoreId(Long storeId, Long userId) {

        // 1. 가게주인이 맞는지 확인하기
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 리뷰가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        if (!storePS.getUser().getId().equals(userId)) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        List<ReportCeoReviewRespDto> reviewRespDtos = reportRepositoryQuery.findAllByStoreId(storeId);
        // 2. 목록가져오기
        try {
            return reviewRespDtos;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public void insertReportReview(InsertReportReviewReqDto insertReportReviewReqDto, Long reviewId, Long userId) {

        // 1. 리뷰가 있는지 확인
        CustomerReview customerReviewPS = null;
        CeoReview ceoReviewPS = null;

        if (insertReportReviewReqDto.getUserKind().equals(UserEnum.CUSTOMER.getValue())) {
            customerReviewPS = customerReviewRepository.findById(reviewId).orElseThrow(
                    () -> new CustomApiException("해당 리뷰가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

            // 2. 리뷰 작성자가 본인인지 확인
            if (!customerReviewPS.getUser().getId().equals(userId)) {
                throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
            }

            // 3. 상대 리뷰 가져오기
            ceoReviewPS = customerReviewPS.getCeoReview();

        } else {
            ceoReviewPS = ceoReviewRepository.findById(reviewId).orElseThrow(
                    () -> new CustomApiException("해당 리뷰가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

            // 2. 리뷰 작성자가 본인인지 확인
            if (!ceoReviewPS.getStore().getUser().getId().equals(userId)) {
                throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
            }

            // 3. 상대 리뷰 가져오기
            customerReviewPS = customerReviewRepository.findByCeoReviewId(ceoReviewPS.getId()).orElseThrow(
                    () -> new CustomApiException("해당 리뷰가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        }

        // 4. 신고리뷰 생성
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("해당 유저가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        reportReviewRepository.save(insertReportReviewReqDto.toEntity(userPS, customerReviewPS, ceoReviewPS));

    }
    /* 승현 작업 종료 */

}
