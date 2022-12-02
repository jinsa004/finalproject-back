package shop.mtcoding.finalproject.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.menu.MenuReqDto.InsertMenuReqDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.DetailMenuRespDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.InsertMenuRespDto;
import shop.mtcoding.finalproject.dto.menu.MenuRespDto.ShowMenuRespDto;
import shop.mtcoding.finalproject.dto.user.UserRespDto.UserDto;
import shop.mtcoding.finalproject.service.MenuService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class MenuApiController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final MenuService menuService;

    /* 승현 작업 시작 */
    @PostMapping("/store/menu")
    public ResponseEntity<?> insert(@RequestBody InsertMenuReqDto insertMenuReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        insertMenuReqDto.setUserDto(new UserDto(loginUser.getUser().getId()));
        InsertMenuRespDto insertMenuRespDto = menuService.insert(insertMenuReqDto);
        return new ResponseEntity<>(new ResponseDto<>("메뉴 추가 성공", insertMenuRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/store/menu")
    public ResponseEntity<?> findAll(@AuthenticationPrincipal LoginUser loginUser) {
        List<ShowMenuRespDto> showMenuRespDtos = menuService.findAll(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("메뉴 전체보기 성공", showMenuRespDtos), HttpStatus.OK);
    }

    @GetMapping("/store/menu/{menuId}")
    public ResponseEntity<?> findById(@PathVariable Long menuId,
            @AuthenticationPrincipal LoginUser loginUser) {
        DetailMenuRespDto detailMenuRespDto = menuService.findById(menuId);
        return new ResponseEntity<>(new ResponseDto<>("메뉴 상세보기 성공", detailMenuRespDto), HttpStatus.OK);
    }

    /* 승현 작업 종료 */

}
