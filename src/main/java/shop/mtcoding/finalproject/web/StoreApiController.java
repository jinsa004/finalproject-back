package shop.mtcoding.finalproject.web;

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
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.ApplyReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.UpdateStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.ApplyRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.UpdateStoreRespDto;
import shop.mtcoding.finalproject.service.StoreService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class StoreApiController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final StoreService storeService;

    @PutMapping("/store/info")
    public ResponseEntity<?> updateById(@RequestBody UpdateStoreReqDto updateStoreReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        updateStoreReqDto.setUserId(loginUser.getUser().getId());
        UpdateStoreRespDto updateStoreRespDto = storeService.updateById(updateStoreReqDto);
        return new ResponseEntity<>(new ResponseDto<>("가게 정보 수정 성공", updateStoreRespDto), HttpStatus.OK);
    }

    @PostMapping("/user/apply")
    public ResponseEntity<?> apply(@RequestBody ApplyReqDto applyReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        applyReqDto.setUserId(loginUser.getUser().getId());
        ApplyRespDto applyRespDto = storeService.apply(applyReqDto);
        return new ResponseEntity<>(new ResponseDto<>("입점신청 성공", applyRespDto), HttpStatus.CREATED);
    }

    @GetMapping("/user/apply")
    public ResponseEntity<?> findByUserIdToApply(@AuthenticationPrincipal LoginUser loginUser) {
        ApplyRespDto applyRespDto = storeService.findByUserIdToApply(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("입점현황보기 성공", applyRespDto), HttpStatus.OK);
    }

}