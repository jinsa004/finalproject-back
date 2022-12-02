package shop.mtcoding.finalproject.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.menu.MenuRepository;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.InsertMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.InsertMenuRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MenuService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    /* 승현 작업 시작 */
    public InsertMenuRespDto insert(InsertMenuReqDto insertMenuReqDto) {
        // 1. 가게가 있는지 확인하기
        Store storePS = storeRepository.findByUserId(insertMenuReqDto.getUserDto().getId()).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        log.debug("디버그 : " + insertMenuReqDto.getCategory());
        // 2. 심사중이거나 폐업한 점포면 예외처리
        // if (!storePS.isAccept() || storePS.isClosure()) {
        // throw new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        // }

        // 3. 메뉴 추가하기
        Menu menu = insertMenuReqDto.toEntity(storePS);
        Menu menuPS = menuRepository.save(menu);

        return new InsertMenuRespDto(menuPS);
    }

    /* 승현 작업 종료 */

}
