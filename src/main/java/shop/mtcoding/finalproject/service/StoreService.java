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
import shop.mtcoding.finalproject.dto.store.StoreRespDto.ShowStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.UpdateStoreRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StoreService {

    /* 승현 작업 시작함 */
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final StoreRepository storeRepository;

    public UpdateStoreRespDto updateById(UpdateStoreReqDto updateStoreReqDto) {

        // 1. 해당 id의 점포가 있는지 찾기
        Store store = storeRepository.findByUserId(updateStoreReqDto.getUserId()).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));

        // 2. 심사중인 점포일 경우 예외처리 해버리기
        // if (!store.isAccept()) {
        // throw new CustomApiException("해당 점포는 아직 심사중입니다.", HttpStatus.BAD_REQUEST);
        // }

        // 3. 해당 점포의 내용 업데이트하기
        Store storePS = storeRepository.save(store.update(updateStoreReqDto.toEntity()));

        log.debug("디버그 : 업데이트된 점포값 아이디 - " + storePS.getId());
        log.debug("디버그 : 업데이트된 점포값 유저아이디 - " + storePS.getUserId());

        // 4. 처리된 내용 보여주기
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

    public ShowStoreRespDto findByUserId(Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));

        log.debug("디버그 : 상세보기 점포값 아이디 - " + storePS.getId());
        log.debug("디버그 : 상세보기 점포값 유저아이디 - " + storePS.getUserId());
        return new ShowStoreRespDto(storePS);
    }

    /* 승현 작업 종료함 */
}
