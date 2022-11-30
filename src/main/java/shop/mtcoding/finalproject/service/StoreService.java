package shop.mtcoding.finalproject.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.ApplyReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.SaveStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.ApplyRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.SaveStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.ShowStoreRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StoreService {

    /* 승현 작업 시작함 */
    private final Logger log = LoggerFactory.getLogger(getClass());
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;

    public SaveStoreRespDto save(SaveStoreReqDto saveStoreReqDto) {

        // 1. 해당 id의 점포가 있는지 찾기
        Store store = storeRepository.findByUserId(saveStoreReqDto.getUserDto().getId()).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));

        // 2. 심사중인 점포일 경우 예외처리 해버리기
        // if (!store.isAccept()) {
        // throw new CustomApiException("해당 점포는 아직 심사중입니다.", HttpStatus.BAD_REQUEST);
        // }

        log.debug("디버그 1 : " + saveStoreReqDto.getCategory());
        log.debug("디버그 1 : " + saveStoreReqDto.getName());

        // 3. 해당 점포의 내용 업데이트하기
        Store storePS = storeRepository.save(saveStoreReqDto.toEntity(store));

        // 4. 처리된 내용 보여주기
        return new SaveStoreRespDto(storePS);
    }

    public ApplyRespDto apply(ApplyReqDto applyReqDto) {
        User userPS = userRepository.findById(applyReqDto.getUserDto().getId()).orElseThrow(
                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
        Store storePS = storeRepository.save(applyReqDto.toEntity(applyReqDto, userPS));
        return new ApplyRespDto(storePS);
    }

    public ApplyRespDto findByUserIdToApply(Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        return new ApplyRespDto(storePS);
    }

    public ShowStoreRespDto findByUserId(Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        return new ShowStoreRespDto(storePS);
    }

    //////// 테스트 /////////

    public List<Store> findByAll() {
        List<Store> stores = storeRepository.findAllTest();
        return stores;
    }

    /* 승현 작업 종료함 */
}
