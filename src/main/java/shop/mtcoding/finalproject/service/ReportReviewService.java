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
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewRespDto.InsertReportReviewRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewRespDto.ReportReviewListRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewRespDto.ResolveAcceptReportReviewRespDto;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewRespDto.ResolveRefuseReportReviewRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class ReportReviewService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final ReportReviewRepository reportReviewRepository;
    private final CustomerReviewRepository customerReviewRepository;
    private final UserRepository userRepository;
    private final StoreRepository storeRepository;
    private final ReportReviewRepositoryQuery reportRepositoryQuery;

    /* ?????? ?????? ?????? */

    @Transactional
    public ResolveRefuseReportReviewRespDto resolveRefuseReportReview(
            ResolveReportReviewReqDto resolveReportReviewReqDto,
            Long reportReviewId) {
        // 1. ?????? ?????? ????????? ??????????????? ??????
        ReportReview reportReviewPS = reportReviewRepository.findById(reportReviewId)
                .orElseThrow(() -> new CustomApiException("?????? ????????? ????????????", HttpStatus.BAD_REQUEST));
        // 2. ?????? ?????? ?????? ??????
        reportReviewPS.refuseResolve(resolveReportReviewReqDto);
        reportReviewRepository.save(reportReviewPS);
        // 3. DTO ??????
        ResolveRefuseReportReviewRespDto resolveReportReviewRespDto = new ResolveRefuseReportReviewRespDto(
                reportReviewPS);
        try {
            return resolveReportReviewRespDto;
        } catch (NoResultException e) {
            return null;
        }
    }

    @Transactional
    public ResolveAcceptReportReviewRespDto resolveAcceptReportReview(
            ResolveReportReviewReqDto resolveReportReviewReqDto,
            Long reportReviewId) {
        // 1. ?????? ?????? ????????? ??????????????? ??????
        ReportReview reportReviewPS = reportReviewRepository.findById(reportReviewId)
                .orElseThrow(() -> new CustomApiException("?????? ????????? ????????????", HttpStatus.BAD_REQUEST));
        // 2. ?????? ?????? ?????? ??????
        reportReviewPS.acceptResolve(resolveReportReviewReqDto);
        reportReviewRepository.save(reportReviewPS);
        // 3. DTO ??????
        ResolveAcceptReportReviewRespDto resolveReportReviewRespDto = new ResolveAcceptReportReviewRespDto(
                reportReviewPS);
        try {
            return resolveReportReviewRespDto;
        } catch (NoResultException e) {
            return null;
        }
    }

    public DetailReportReviewRespDto detailReportReview(Long reportReviewId) {
        // 1. ?????? ?????? ????????? ????????? ??????
        ReportReview reportReviewPS = reportReviewRepository.findById(reportReviewId)
                .orElseThrow(() -> new CustomApiException("?????? ????????? ????????????.", HttpStatus.BAD_REQUEST));
        // 2. ???????????? ??????
        ReportCustomerInfoRespDto reportCustomerReviewRespDto = reportRepositoryQuery
                .findByReportReviewId(reportReviewId);
        // 3. ?????? ?????? ????????? ??????
        ReportCeoInfoRespDto reportCeoReviewRespDto = reportRepositoryQuery.findByReportReviewIdToCeo(reportReviewId);
        // 4. DTO ??????
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
        // 1. ???????????? ????????????
        List<ReportReview> reportReviewList = new ArrayList<>();
        reportReviewList = reportReviewRepository.findAllByUser();
        // 2. DTO ??????
        ReportReviewListRespDto reportReviewListRespDto = new ReportReviewListRespDto(reportReviewList);
        return reportReviewListRespDto;
    }

    /* ?????? ?????? ?????? */
    public List<ReportCeoReviewRespDto> findAllByStoreId(Long storeId, Long userId) {

        // 1. ??????????????? ????????? ????????????
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("?????? ????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST));
        storePS.checkCeo(userId);
        return reportRepositoryQuery.findAllByStoreId(storeId);
    }

    @Transactional
    public InsertReportReviewRespDto insertReportReview(InsertReportReviewReqDto insertReportReviewReqDto,
            Long customerReviewId, Long userId) {
        CustomerReview customerReviewPS = customerReviewRepository.findById(customerReviewId).orElseThrow(
                () -> new CustomApiException("?????? ????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST));
        customerReviewPS.getStore().checkCeo(userId);
        ReportReview reportReviewPS = reportReviewRepository.findByCustomerReviewId(customerReviewId);
        if (reportReviewPS != null) {
            throw new CustomApiException("?????? ????????? ???????????????", HttpStatus.BAD_REQUEST);
        }

        CeoReview ceoReviewPS = customerReviewPS.getCeoReview();
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("?????? ????????? ???????????? ????????????.", HttpStatus.BAD_REQUEST));

        return new InsertReportReviewRespDto(reportReviewRepository
                .save(insertReportReviewReqDto.toEntity(userPS, customerReviewPS, ceoReviewPS)));
    }

    /* ?????? ?????? ?????? */

}
