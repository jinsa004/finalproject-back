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
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.CeoInsertMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.CeoUpdateMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.CeoUpdateMenuStateReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.CeoDetailMenuRespDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.CeoInsertMenuRespDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.CeoShowMenuRespDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.CeoUpdateMenuRespDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.CustomerDetailMenuRespDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.CustomerMenuListRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MenuService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final StoreRepository storeRepository;
    private final MenuRepository menuRepository;

    /* 성진 작업 시작@@ */
    // 메뉴 상세보기(사용자 앱 입장)
    public CustomerDetailMenuRespDto detailMenu(Long menuId) {
        // 1. 해당 메뉴의 내용을 셀렉
        Menu menuPS = menuRepository.findById(menuId).orElseThrow(
                () -> new CustomApiException("해당 메뉴가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        return new CustomerDetailMenuRespDto(menuPS);
    }

    // 메뉴 목록보기(사용자 앱 입장)
    public CustomerMenuListRespDto menuList(Long storeId) {
        // 1. 메뉴리스트 셀렉
        List<Menu> menuList = menuRepository.findMenuListByStoreId(storeId);
        log.debug("디버그 : 해당 가게 메뉴 : " + menuList.get(0).getName());
        // 2. DTO 응답
        CustomerMenuListRespDto customerMenuListRespDto = new CustomerMenuListRespDto(menuList);
        log.debug("디버그 : DTO 응답 타나?");
        return customerMenuListRespDto;
    }

    /* 승현 작업 시작 */

    @Transactional
    public void updateByMenuIdToState(CeoUpdateMenuStateReqDto ceoUpdateMenuStateReqDto, Long userId, Long menuId) {
        Menu menuPS = menuRepository.findByMenuId(menuId).orElseThrow(
                () -> new CustomApiException("해당 메뉴가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        menuPS.checkWriter(userId);
        menuRepository.save(menuPS.close(ceoUpdateMenuStateReqDto.toEntity()));
    }

    @Transactional
    public CeoUpdateMenuRespDto updateByMenuId(CeoUpdateMenuReqDto ceoUpdateMenuReqDto, Long userId, Long menuId) {
        Menu menuPS = menuRepository.findByMenuId(menuId).orElseThrow(
                () -> new CustomApiException("해당 메뉴가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        menuPS.checkWriter(userId);
        return new CeoUpdateMenuRespDto(menuRepository.save(menuPS.update(ceoUpdateMenuReqDto.toEntity())));
    }

    @Transactional
    public CeoInsertMenuRespDto insert(CeoInsertMenuReqDto ceoInsertMenuReqDto, Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        storePS.checkStoreBusinessState();
        return new CeoInsertMenuRespDto(menuRepository.save(ceoInsertMenuReqDto.toEntity(storePS)));
    }

    public CeoDetailMenuRespDto findById(Long menuId) {
        Menu menuPS = menuRepository.findByMenuId(menuId).orElseThrow(
                () -> new CustomApiException("해당 메뉴가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        menuPS.getStore().checkStoreBusinessState();
        return new CeoDetailMenuRespDto(menuPS);
    }

    public List<CeoShowMenuRespDto> findAll(Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST));
        List<Menu> menuPS = menuRepository.findAllByStoreId(storePS.getId());
        List<CeoShowMenuRespDto> ceoShowMenuRespDtos = new ArrayList<>();
        for (int i = 0; i < menuPS.size(); i++) {
            ceoShowMenuRespDtos.add(i, new CeoShowMenuRespDto(menuPS.get(i)));
        }
        return ceoShowMenuRespDtos;
    }

    /* 승현 작업 종료 */

}
