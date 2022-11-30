package shop.mtcoding.finalproject.web;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.ApplyReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.SaveStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.ApplyRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.SaveStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.ShowStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.UserDto;
import shop.mtcoding.finalproject.service.StoreService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class StoreApiController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final StoreService storeService;

    @GetMapping("/user/apply")
    public ResponseEntity<?> findByUserIdToApply(@AuthenticationPrincipal LoginUser loginUser) {
        log.debug("디버그 : 유저아이디: " + loginUser.getUser().getId());
        ApplyRespDto applyRespDto = storeService.findByUserIdToApply(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("입점현황보기 성공", applyRespDto), HttpStatus.OK);
    }

    @GetMapping("/store")
    public ResponseEntity<?> findByUserId(@AuthenticationPrincipal LoginUser loginUser) {
        ShowStoreRespDto showStoreRespDto = storeService.findByUserId(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("가게 상세보기 성공", showStoreRespDto), HttpStatus.OK);
    }

    @PutMapping("/store/info")
    public ResponseEntity<?> updateById(@RequestBody SaveStoreReqDto saveStoreReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        saveStoreReqDto.setUserDto(new UserDto(loginUser.getUser()));
        SaveStoreRespDto saveStoreRespDto = storeService.save(saveStoreReqDto);
        return new ResponseEntity<>(new ResponseDto<>("가게 등록 성공", saveStoreRespDto), HttpStatus.OK);
    }

    @PostMapping("/user/apply")
    public ResponseEntity<?> apply(@RequestBody ApplyReqDto applyReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        applyReqDto.setUserDto(new UserDto(loginUser.getUser()));
        ApplyRespDto applyRespDto = storeService.apply(applyReqDto);
        return new ResponseEntity<>(new ResponseDto<>("입점신청 성공", applyRespDto), HttpStatus.CREATED);
    }

    //// 테스트 ////
    @GetMapping("/test/store")
    public ResponseEntity<?> findAll() {
        List<Store> showStoreRespDtos = storeService.findByAll();
        return new ResponseEntity<>(new ResponseDto<>("테스트용 가게 목록", showStoreRespDtos), HttpStatus.OK);
    }

}
