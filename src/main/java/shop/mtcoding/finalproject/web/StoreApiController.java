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
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
import shop.mtcoding.finalproject.dto.ResponseDto;
import shop.mtcoding.finalproject.dto.order.OrderReqDto.FindStatsReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.AdminUpdateStoreApplyAcceptReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoApplyStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoInsertStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoUpdateStoreBusinessStateReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoUpdateStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.LikeStoreListRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.StoreNameRespDto;
import shop.mtcoding.finalproject.service.StoreService;

@RequiredArgsConstructor
@RequestMapping("/api")
@RestController
public class StoreApiController {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final StoreService storeService;

    @GetMapping("/user/{userId}/store/{category}/list")
    public ResponseEntity<?> getCategoryStoreList(@PathVariable StoreCategoryEnum category, @PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(
                new ResponseDto<>(1, "??????????????? ?????? ???????????? ??????", storeService.categoryStoreList(category, userId)),
                HttpStatus.OK);
    }

    // ????????? ????????? ????????? ??? ????????? ????????? ????????????????????? ???????????????, ????????? ???????????? ???????????? ????????? ?????? ??????
    @GetMapping("/user/{userId}/store/name")
    public ResponseEntity<?> getStoreName(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        StoreNameRespDto storeNameRespDto = storeService.checkStoreName(userId, loginUser);
        if (storeNameRespDto.getName().equals("")) {
            return new ResponseEntity<>(new ResponseDto<>(1, "???????????? ??????", storeNameRespDto), HttpStatus.OK);
        }
        return new ResponseEntity<>(new ResponseDto<>(1, "???????????? ??????", storeNameRespDto), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/like/store/list")
    public ResponseEntity<?> getLikeStroeList(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        LikeStoreListRespDto likeStoreListRespDto = storeService.likeStoreList(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "???????????? ???????????? ??????", likeStoreListRespDto), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/store/{storeId}/info")
    public ResponseEntity<?> getStoreInfo(@PathVariable Long userId, @PathVariable Long storeId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ???????????? ?????? ??????", storeService.customerStoreInfo(storeId)),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/store/{storeId}/detail")
    public ResponseEntity<?> detailStoreMain(@PathVariable Long userId, @PathVariable Long storeId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ???????????? ?????? ??????", storeService.customerDetailStore(storeId)),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/store/list")
    public ResponseEntity<?> findStoreList(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ???????????? ?????? ??????",
                storeService.customerStoreList(userId)), HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/store/apply")
    public ResponseEntity<?> findByUserIdToApply(@PathVariable Long userId,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????????????????? ??????", storeService.findByUserIdToApply(userId)),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/store/info")
    public ResponseEntity<?> findByUserId(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ???????????? ??????", storeService.findByUserId(userId)),
                HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/store/info/stats")
    public ResponseEntity<?> findStatsByStoreId(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser,
            @RequestBody FindStatsReqDto findStatsReqDto) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(
                new ResponseDto<>(1, "???????????? ??????", storeService.findStatsByStoreId(findStatsReqDto, userId)),
                HttpStatus.OK);
    }

    @GetMapping("/admin/store/apply/list")
    public ResponseEntity<?> findAllToApplyList(@AuthenticationPrincipal LoginUser loginUser) {
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ?????? ???????????? ?????? ??????", storeService.findAllToApplyList()),
                HttpStatus.OK);
    }

    @GetMapping("/user/{userId}/store/save/info")
    public ResponseEntity<?> findByIdToSave(@AuthenticationPrincipal LoginUser loginUser, @PathVariable Long userId) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "??????????????? ???????????? ???????????? ??????", storeService.findByIdToSave(userId)),
                HttpStatus.OK);
    }

    @PutMapping("/admin/store/{storeId}/apply/accept")
    public ResponseEntity<?> updateByStoreIdToAccept(@AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long storeId,
            @RequestBody AdminUpdateStoreApplyAcceptReqDto adminUpdateStoreApplyAcceptReqDto) {
        // loginUser.getUser().checkRole();
        storeService.updateByStoreIdToAccept(adminUpdateStoreApplyAcceptReqDto, storeId);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ?????? ?????? ??????", null), HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/store/update/close")
    public ResponseEntity<?> delete(@PathVariable Long userId, @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        storeService.updateByUserIdToClose(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ?????? ??????", null), HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/store/update/business")
    public ResponseEntity<?> updateToBusiness(@PathVariable Long userId,
            @RequestBody CeoUpdateStoreBusinessStateReqDto ceoUpdateStoreBusinessStateReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        storeService.updateToBusinessState(ceoUpdateStoreBusinessStateReqDto, userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ?????? ?????? ??????", null), HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/store/save")
    public ResponseEntity<?> save(@PathVariable Long userId, @RequestBody CeoInsertStoreReqDto ceoInsertStoreReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ?????? ??????", storeService.insert(ceoInsertStoreReqDto, userId)),
                HttpStatus.OK);
    }

    @PutMapping("/user/{userId}/store/update")
    public ResponseEntity<?> update(@PathVariable Long userId, @RequestBody CeoUpdateStoreReqDto ceoUpdateStoreReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "?????? ?????? ??????", storeService.update(ceoUpdateStoreReqDto, userId)),
                HttpStatus.OK);
    }

    @PostMapping("/user/{userId}/store/apply")
    public ResponseEntity<?> apply(@PathVariable Long userId, @RequestBody CeoApplyStoreReqDto ceoApplyStoreReqDto,
            @AuthenticationPrincipal LoginUser loginUser) {
        loginUser.getUser().checkUser(userId);
        return new ResponseEntity<>(new ResponseDto<>(1, "???????????? ??????", storeService.apply(ceoApplyStoreReqDto, userId)),
                HttpStatus.CREATED);
    }

    @PostMapping("/user/{userId}/store/{storeId}/like")
    public ResponseEntity<?> insertLike(@AuthenticationPrincipal LoginUser loginUser,
            @PathVariable Long userId, @PathVariable Long storeId) {
        loginUser.getUser().checkUser(userId);
        storeService.insertLike(userId, storeId);
        return new ResponseEntity<>(new ResponseDto<>(1, "????????? ?????? ??????", null), HttpStatus.CREATED);
    }
}
