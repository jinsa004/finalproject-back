package shop.mtcoding.finalproject.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewRepository;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.dto.ceoReview.CeoReviewRespDto.ShowReviewRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class CeoReviewService {

    private final StoreRepository storeRepository;
    private final CeoReviewRepository ceoReviewsRepository;
    private final CustomerReviewRepository customerReviewRepository;

    public List<ShowReviewRespDto> findAllReviewByStoreId(Long storeId) {

        // 1. 가게 상태 확인하기
        Store storePS = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        // 폐업, 미승인 상태일때 익셉션
        // if (storePS.isClosure() || !storePS.isAccept()) {
        // throw new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        // }

        // 2. 리뷰 목록 가져오기
        List<CustomerReview> customerReviewPS = customerReviewRepository.findAllByStoreId(storeId);

        // 3. Dto로 바꾸기
        List<ShowReviewRespDto> showReviewRespDtos = new ArrayList<>();
        for (int i = 0; i < customerReviewPS.size(); i++) {
            showReviewRespDtos.add(i, new ShowReviewRespDto(customerReviewPS.get(i)));
        }

        return showReviewRespDtos;
    }

}
