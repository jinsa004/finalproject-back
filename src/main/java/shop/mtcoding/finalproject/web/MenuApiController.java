package shop.mtcoding.finalproject.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.CeoInsertMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.CeoUpdateMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.CeoUpdateMenuStateReqDto;
import shop.mtcoding.finalproject.service.MenuService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MenuApiController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MenuService menuService;

    @GetMapping("/user/{userId}/store/{storeId}/menu/{menuId}/detail")
    public ResponseEntity<?> getDetailMenu(@PathVariable Long userId, @PathVariable Long storeId,
            @PathVariable Long menuId, @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "메뉴 상세보기 성공", menuService.detailMenu(menuId, storeId)),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/store/{storeId}/menu/list")
    public ResponseEntity<?> getMenuList(@PathVariable Long userId, @PathVariable Long storeId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "메뉴 목록보기 성공", menuService.menuList(storeId)), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/store/menu/list")
    public ResponseEntity<?> findAll(@PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "사장님 가게 메뉴 전체보기 성공", menuService.findAll(userId)),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/store/menu/{menuId}/info")
    public ResponseEntity<?> findById(@PathVariable Long userId, @PathVariable Long menuId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "사장님 메뉴 상세보기 성공", menuService.findById(menuId)),
                HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/store/menu/{menuId}/update/state")
    public ResponseEntity<?> updateByMenuIdToState(@PathVariable Long userId, @PathVariable Long menuId,
            @RequestBody CeoUpdateMenuStateReqDto ceoUpdateMenuStateReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        menuService.updateByMenuIdToState(ceoUpdateMenuStateReqDto, userId, menuId);
        return new ResponseEntity<>(new ResponseDto<>(1, "메뉴 보이기 수정 완료", null), HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/store/menu/{menuId}/update")
    public ResponseEntity<?> updateByMenuId(@PathVariable Long userId, @PathVariable Long menuId,
            @RequestBody CeoUpdateMenuReqDto ceoUpdateMenuReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(
                new ResponseDto<>(1, "메뉴 수정 성공", menuService.updateByMenuId(ceoUpdateMenuReqDto, userId, menuId)),
                HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/store/menu/save")
    public ResponseEntity<?> insert(@RequestBody CeoInsertMenuReqDto ceoInsertMenuReqDto, @PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "메뉴 추가 성공", menuService.insert(ceoInsertMenuReqDto, userId)),
                HttpStatus.CREATED);
    }

}
