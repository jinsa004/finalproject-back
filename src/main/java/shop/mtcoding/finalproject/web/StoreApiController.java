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
import shop.mtcoding.finalproject.dto.store.StoreReqDto.InsertStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.UpdateBusinessStateReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.UpdateStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.ApplyRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.DetailStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.InsertStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.UpdateBusinessStateRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.UpdateStoreRespDto;
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
        DetailStoreRespDto detailStoreRespDto = storeService.findByUserId(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("가게 상세보기 성공", detailStoreRespDto), HttpStatus.OK);
    }

    @PutMapping("/store/close")
    public ResponseEntity<?> delete(@AuthenticationPrincipal LoginUser loginUser) {
        storeService.updateByUserIdToClose(loginUser.getUser().getId());
        return new ResponseEntity<>(new ResponseDto<>("폐업 신청 성공", null), HttpStatus.OK);
    }

    @PutMapping("/store/business")
    public ResponseEntity<?> updateToBusiness(@RequestBody UpdateBusinessStateReqDto updateBusinessStateReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        updateBusinessStateReqDto.setUserId(loginUser.getUser().getId());
        UpdateBusinessStateRespDto businessStateRespDto = storeService.updateToBusinessState(updateBusinessStateReqDto);
        return new ResponseEntity<>(new ResponseDto<>("영업 상태 수정 성공", businessStateRespDto), HttpStatus.OK);
    }

    @PutMapping("/store")
    public ResponseEntity<?> save(@RequestBody InsertStoreReqDto insertStoreReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        insertStoreReqDto.setUserId(loginUser.getUser().getId());
        InsertStoreRespDto insertStoreRespDto = storeService.insert(insertStoreReqDto);
        return new ResponseEntity<>(new ResponseDto<>("가게 등록 성공", insertStoreRespDto), HttpStatus.OK);
    }

    @PutMapping("/store/info")
    public ResponseEntity<?> update(@RequestBody UpdateStoreReqDto updateStoreReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        updateStoreReqDto.setUserId(loginUser.getUser().getId());
        UpdateStoreRespDto updateStoreRespDto = storeService.update(updateStoreReqDto);
        return new ResponseEntity<>(new ResponseDto<>("가게 수정 성공", updateStoreRespDto), HttpStatus.OK);
    }

    @PostMapping("/user/apply")
    public ResponseEntity<?> apply(@RequestBody ApplyReqDto applyReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        applyReqDto.setUserId(loginUser.getUser().getId());
        ApplyRespDto applyRespDto = storeService.apply(applyReqDto);
        return new ResponseEntity<>(new ResponseDto<>("입점신청 성공", applyRespDto), HttpStatus.CREATED);
    }
}