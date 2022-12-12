package shop.mtcoding.finalproject.service;

import java.util.List;

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
import shop.mtcoding.finalproject.domain.reportReview.ReportReviewRepository;
import shop.mtcoding.finalproject.domain.reportReview.ReportReviewRepositoryQuery;
import shop.mtcoding.finalproject.domain.reportReview.ReportReviewRespDto;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.reportReview.ReportReviewReqDto.InsertReportReviewReqDto;

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

    /* 승현 작업 시작 */
    public List<ReportReviewRespDto> findAllByStoreId(Long storeId, Long userId) {

        // 1. 가게주인이 맞는지 확인하기
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 리뷰가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        if (!storePS.getUser().getId().equals(userId)) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 2. 목록가져오기
        List<ReportReviewRespDto> reviewRespDtos = reportRepositoryQuery.findAllByStoreId(storeId);

        return reviewRespDtos;
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
