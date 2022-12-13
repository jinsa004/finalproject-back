package shop.mtcoding.finalproject.service;

import java.util.ArrayList;
import java.util.List;

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
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.dto.ceoReview.CeoReviewReqDto.InsertCeoReviewReqDto;
import shop.mtcoding.finalproject.dto.ceoReview.CeoReviewRespDto.InsertCeoReviewRespDto;
import shop.mtcoding.finalproject.dto.ceoReview.CeoReviewRespDto.ShowReviewRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CeoReviewService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final StoreRepository storeRepository;
    private final CeoReviewRepository ceoReviewsRepository;
    private final CustomerReviewRepository customerReviewRepository;

    /* 승현 작업 시작 */

    public List<ShowReviewRespDto> findAllReviewByStoreId(Long storeId) {

        // 1. 가게 상태 확인하기
        Store storePS = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        // 2. 리뷰 목록 가져오기
        List<CustomerReview> customerReviewPS = customerReviewRepository.findAllByStoreId(storeId);

        // 3. Dto로 바꾸기
        List<ShowReviewRespDto> showReviewRespDtos = new ArrayList<>();
        for (int i = 0; i < customerReviewPS.size(); i++) {
            showReviewRespDtos.add(i, new ShowReviewRespDto(customerReviewPS.get(i)));
        }

        return showReviewRespDtos;
    }

    @Transactional
    public InsertCeoReviewRespDto insertByCustomerReviewId(InsertCeoReviewReqDto insertCeoReviewReqDto, Long userId,
            Long customerReviewId) {

        // 1. 리뷰상태 확인
        CustomerReview customerReviewPS = customerReviewRepository.findById(customerReviewId)
                .orElseThrow(() -> new CustomApiException("해당 리뷰가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        // 2. 주문한 가게 주인이 맞는지 확인
        if (!customerReviewPS.getOrder().getStore().getUser().getId().equals(userId)) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 3. 리뷰등록
        CeoReview ceoReviewPS = ceoReviewsRepository.save(insertCeoReviewReqDto.toEntity(customerReviewPS));

        // 4. 리뷰 업데이트
        customerReviewRepository.save(customerReviewPS.updateCeoReview(ceoReviewPS));

        return new InsertCeoReviewRespDto(ceoReviewPS);
    }

    /* 승현 작업 종료 */
}
