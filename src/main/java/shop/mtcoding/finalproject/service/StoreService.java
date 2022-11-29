package shop.mtcoding.finalproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.ApplyReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.UpdateStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.ApplyRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.UpdateStoreRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StoreService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final StoreRepository storeRepository;

    public UpdateStoreRespDto updateById(UpdateStoreReqDto updateStoreReqDto) {
        Store store = storeRepository.findByUserId(updateStoreReqDto.getUserId()).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        Store storePS = storeRepository.save(store.update(updateStoreReqDto));
        return new UpdateStoreRespDto(storePS);
    }

    public ApplyRespDto apply(ApplyReqDto applyReqDto) {
        Store storePS = storeRepository.save(applyReqDto.toEntity());
        return new ApplyRespDto(storePS);
    }

    public ApplyRespDto findByUserIdToApply(Long id) {
        Store storePS = storeRepository.findByUserId(id).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        return new ApplyRespDto(storePS);
    }
}
