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
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.menu.MenuRepository;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.InsertMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.UpdateMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.UpdateMenuStateReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.DetailMenuRespDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.InsertMenuRespDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.MenuListRespDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.ShowMenuRespDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.UpdateMenuRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MenuService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    /* 성진 작업 시작@@ */
    public MenuListRespDto 메뉴_목록보기(Long storeId) {
        // 1. 메뉴리스트 셀렉
        List<Menu> menuList = menuRepository.findMenuListByStoreId(storeId);
        log.debug("디버그 : 해당 가게 메뉴 : " + menuList.get(0).getName());
        // 2. DTO 응답
        MenuListRespDto menuListRespDto = new MenuListRespDto(menuList);
        log.debug("디버그 : DTO 응답 타나?");
        return menuListRespDto;
    }

    /* 승현 작업 시작 */

    @Transactional
    public void updateByMenuIdToState(UpdateMenuStateReqDto updateMenuStateReqDto) {

        // 1. 메뉴가 있는지 확인하기
        Menu menu = menuRepository.findByMenuId(updateMenuStateReqDto.getId()).orElseThrow(
                () -> new CustomApiException("해당 메뉴가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        // 2. 메뉴 작성자가 본인인지 확인하기 (나중에 익셉션 빼면 좋을듯)
        if (!menu.getStore().getUser().getId().equals(updateMenuStateReqDto.getUserId())) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 3. 메뉴 수정하기
        menuRepository.save(menu.close(updateMenuStateReqDto.toEntity()));
    }

    @Transactional
    public UpdateMenuRespDto updateByMenuId(UpdateMenuReqDto updateMenuReqDto) {
        // 1. 메뉴가 있는지 확인하기
        Menu menu = menuRepository.findByMenuId(updateMenuReqDto.getId()).orElseThrow(
                () -> new CustomApiException("해당 메뉴가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        // 2. 메뉴 작성자가 본인인지 확인하기 (나중에 익셉션 빼면 좋을듯)
        if (!menu.getStore().getUser().getId().equals(updateMenuReqDto.getUserId())) {
            throw new CustomApiException("권한이 없습니다.", HttpStatus.BAD_REQUEST);
        }

        // 3. 메뉴 수정하기
        Menu menuPS = menuRepository.save(menu.update(updateMenuReqDto.toEntity()));

        return new UpdateMenuRespDto(menuPS);
    }

    @Transactional
    public InsertMenuRespDto insert(InsertMenuReqDto insertMenuReqDto) {
        // 1. 가게가 있는지 확인하기
        Store storePS = storeRepository.findByUserId(insertMenuReqDto.getUserId()).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        // 2. 심사중이거나 폐업한 점포면 예외처리
        // if (!storePS.isAccept() || storePS.isClosure()) {
        // throw new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        // }

        // 3. 메뉴 추가하기
        Menu menu = insertMenuReqDto.toEntity(storePS);
        Menu menuPS = menuRepository.save(menu);

        return new InsertMenuRespDto(menuPS);
    }

    public DetailMenuRespDto findById(Long menuId) {
        Menu menuPS = menuRepository.findByMenuId(menuId).orElseThrow(
                () -> new CustomApiException("해당 메뉴가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        // 메뉴를 가게가 폐업상태이거나 메뉴가 가려진 상태면 예외처리
        if (menuPS.getStore().isClosure() || menuPS.isClosure()) {
            throw new CustomApiException("해당 메뉴가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        }
        return new DetailMenuRespDto(menuPS);
    }

    public List<ShowMenuRespDto> findAll(Long userId) {
        // 1. 가게가 있는지 체크하기
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));

        // 2. 메뉴 목록 받아오기
        List<Menu> menuPS = menuRepository.findAllByStoreId(storePS.getId());

        // 3. Dto로 바꾸기
        List<ShowMenuRespDto> showMenuRespDtos = new ArrayList<>();
        for (int i = 0; i < menuPS.size(); i++) {
            showMenuRespDtos.add(i, new ShowMenuRespDto(menuPS.get(i)));
        }

        return showMenuRespDtos;
    }

    /* 승현 작업 종료 */

}
