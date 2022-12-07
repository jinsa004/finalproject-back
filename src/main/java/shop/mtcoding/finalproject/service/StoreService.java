package shop.mtcoding.finalproject.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReview;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewRepository;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.like.Like;
import shop.mtcoding.finalproject.domain.like.LikeRepository;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.menu.MenuRepository;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.ApplyReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.InsertStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.UpdateBusinessStateReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.UpdateStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.ApplyRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.DetailStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.InsertStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.StoreListRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.UpdateBusinessStateRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.UpdateStoreRespDto;

@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class StoreService {

    private final Logger log = LoggerFactory.getLogger(getClass());
    private final StoreRepository storeRepository;
    private final UserRepository userRepository;
    private final CustomerReviewRepository customerReviewRepository;
    private final CeoReviewRepository ceoReviewRepository;
    private final LikeRepository likeRepository;
    private final MenuRepository menuRepository;

    /* 성진 작업 시작함 */

    public void 가게_상세보기(Long storeId) {
        // 1. 가게가 존재하는지?
        Store storePS = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomApiException("해당 가게 내역이 없습니다.",
                        HttpStatus.BAD_REQUEST));
        // 2. 별점 평균 데이터 및 리뷰 개수
        CustomerReview customerReviewPS = customerReviewRepository.findByStoreId(storeId);
        // 3. 답글 개수 데이터
        CeoReview ceoReviewPS = ceoReviewRepository.findByStoreId(storeId);
        // 4. 좋아요 개수 데이터
        Like likePS = likeRepository.findByStoreId(storeId);
        // 5. 메뉴 테이블 데이터 셀렉
        List<Menu> menuList = menuRepository.findAllByStoreId(storeId);
        // 6. DTO 응답
    }

    public StoreListRespDto 가게_목록보기() {
        // 1 가게 정보 1셀렉 가게리스트
        List<Store> storeList = storeRepository.findAll();
        log.debug("디버그 : 스토어리스트 : " + storeList);
        // 2 리뷰 별점 셀렉해서 평균내기(평균은 쿼리로 작성) 리뷰리스트
        log.debug("디버그 : 리뷰리스트 전");
        List<CustomerReview> customerReviewList = customerReviewRepository.starPointAverageToStore();
        log.debug("디버그 : 리뷰리스트 후: " + customerReviewList);
        // 3 DTO 응답
        StoreListRespDto storeListRespDto = new StoreListRespDto(storeList, customerReviewList);
        return storeListRespDto;
    }

    /* 성진 작업 끝!@! */

    /* 승현 작업 시작함 */

    @Transactional
    public UpdateBusinessStateRespDto updateToBusinessState(UpdateBusinessStateReqDto updateBusinessStateReqDto) {
        log.debug("디버그 : " + updateBusinessStateReqDto.isOpend());
        // 1. 해당 id의 점포가 있는지 찾기
        Store store = storeRepository.findByUserId(updateBusinessStateReqDto.getUserId()).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        log.debug("디버그 : " + store.isOpend());

        // 2. 심사중인 점포일 경우 예외처리 해버리기
        // if (!store.isAccept() || storePS.isClosure()) {
        // throw new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        // }

        // 3. 해당 점포의 내용 업데이트하기
        Store storePS = storeRepository.save(store.update(updateBusinessStateReqDto.toEntity()));
        log.debug("디버그 : " + storePS.isOpend());

        // 4. 처리된 내용 보여주기
        return new UpdateBusinessStateRespDto(storePS);
    }

    @Transactional
    public InsertStoreRespDto insert(InsertStoreReqDto insertStoreReqDto) {

        // 1. 해당 id의 점포가 있는지 찾기
        Store store = storeRepository.findByUserId(insertStoreReqDto.getUserId()).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));

        // 2. 심사중인 점포일 경우 예외처리 해버리기 < 나중에 통합적으로 체크하는거 구현하기
        // if (!store.isAccept() || storePS.isClosure()) {
        // throw new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        // }

        // 3. 해당 점포의 내용 업데이트하기
        Store storePS = storeRepository.save(store.update(insertStoreReqDto.toEntity()));

        // 4. 처리된 내용 보여주기
        return new InsertStoreRespDto(storePS);
    }

    @Transactional
    public UpdateStoreRespDto update(UpdateStoreReqDto updateStoreReqDto) {
        // 1. 해당 id의 점포가 있는지 찾기
        Store store = storeRepository.findByUserId(updateStoreReqDto.getUserId()).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));

        // 2. 심사중인 점포일 경우 예외처리 해버리기 (빼야겠다)
        // if (!store.isAccept() || storePS.isClosure()) {
        // throw new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        // }

        // 3. 해당 점포의 내용 업데이트하기
        Store storePS = storeRepository.save(store.update(updateStoreReqDto.toEntity()));

        // 4. 처리된 내용 보여주기
        return new UpdateStoreRespDto(storePS);
    }

    @Transactional
    public void updateByUserIdToClose(Long id) {
        // 1. 해당 id의 점포가 있는지 찾기
        Store store = storeRepository.findByUserId(id).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));

        // 2. 심사중인 점포일 경우 예외처리 해버리기 (빼야겠다)
        // if (!store.isAccept() || storePS.isClosure()) {
        // throw new CustomApiException("해당 가게가 존재하지 않습니다.", HttpStatus.BAD_REQUEST);
        // }

        // 3. 해당 점포의 내용 업데이트하기
        storeRepository.save(store.close(store));
    }

    @Transactional
    public ApplyRespDto apply(ApplyReqDto applyReqDto) {
        User userPS = userRepository.findById(applyReqDto.getUserId()).orElseThrow(
                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));

        // 이미 만들어져 있는지 체크하기
        if (!storeRepository.findByUserId(applyReqDto.getUserId()).isEmpty()) {
            throw new CustomApiException("사용할 수 없는 계정입니다.", HttpStatus.BAD_REQUEST);
        }

        Store storePS = storeRepository.save(applyReqDto.toEntity(applyReqDto, userPS));
        return new ApplyRespDto(storePS);
    }

    public ApplyRespDto findByUserIdToApply(Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        return new ApplyRespDto(storePS);
    }

    public DetailStoreRespDto findByUserId(Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));

        return new DetailStoreRespDto(storePS);
    }

    /* 승현 작업 종료함 */
}
