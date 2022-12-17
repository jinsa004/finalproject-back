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

    public List<ShowReviewRespDto> findAllReviewByStoreId(Long storeId) {

        // 1. 가게 상태 확인하기
        Store storePS = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        List<CustomerReview> customerReviewPS = customerReviewRepository.findAllByStoreId(storeId);
        List<ShowReviewRespDto> showReviewRespDtos = new ArrayList<>();
        for (int i = 0; i < customerReviewPS.size(); i++) {
            showReviewRespDtos.add(i, new ShowReviewRespDto(customerReviewPS.get(i)));
        }
        return showReviewRespDtos;
    }

    @Transactional
    public InsertCeoReviewRespDto insertByCustomerReviewId(InsertCeoReviewReqDto insertCeoReviewReqDto, Long userId,
            Long customerReviewId) {

        CustomerReview customerReviewPS = customerReviewRepository.findByCustomerReviewId(customerReviewId)
                .orElseThrow(() -> new CustomApiException("해당 리뷰가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        if (!customerReviewPS.getOrder().getStore().getUser().getId().equals(userId)) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }
        customerReviewPS.checkCeoReview();

        // 3. 리뷰등록
        CeoReview ceoReviewPS = ceoReviewsRepository.save(insertCeoReviewReqDto.toEntity(customerReviewPS));
        customerReviewRepository.save(customerReviewPS.updateCeoReview(ceoReviewPS));

        return new InsertCeoReviewRespDto(ceoReviewPS);
    }

}
