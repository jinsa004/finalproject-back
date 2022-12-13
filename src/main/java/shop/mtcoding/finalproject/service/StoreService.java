package shop.mtcoding.finalproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.exception.CustomApiException;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewInterface;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewRepository;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewInterface;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewRepository;
import shop.mtcoding.finalproject.domain.like.Like;
import shop.mtcoding.finalproject.domain.like.LikeInterface;
import shop.mtcoding.finalproject.domain.like.LikeRepository;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.menu.MenuRepository;
import shop.mtcoding.finalproject.domain.order.OrderRepositoryQuery;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.store.StoreRepository;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.domain.user.UserRepository;
import shop.mtcoding.finalproject.dto.order.OrderReqDto.FindStatsReqDto;
import shop.mtcoding.finalproject.dto.order.OrderStatsRespDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.AdminUpdateStoreApplyAcceptReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoApplyStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoInsertStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoUpdateStoreBusinessStateReqDto;
import shop.mtcoding.finalproject.dto.store.StoreReqDto.CeoUpdateStoreReqDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.AdminShowApplyStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CeoApplyStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CeoDetailStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CeoInsertStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CeoUpdateStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CustomerDetailStoreMainRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CustomerStoreInfoRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CustomerStoreListRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.LikeStoreListRespDto;

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
    private final OrderRepositoryQuery orderRepositoryQuery;

    public LikeStoreListRespDto 찜한가게_목록보기(Long userId) {
        // 1. 찜한 가게 목록보기 join fetch를 이용한 기능
        List<Like> likeList = likeRepository.findByUserIdToLikeStore(userId);
        log.debug("디버그 : 가게 목록보자 : " + likeList.get(0).getStore().getName());
        // 2. 평균별점, 리뷰개수 연산데이터
        List<CustomerReviewInterface> customerReviewList = customerReviewRepository.findAllByStoreReviewToStarPoint();
        log.debug("디버그 : 너가 문제니 연산데이터? : 1번가게 별점" + customerReviewList.get(0).getStarPoint());
        // 3. DTO 응답
        LikeStoreListRespDto likeStoreListRespDto = new LikeStoreListRespDto(likeList, customerReviewList);
        log.debug("디버그 : " + likeStoreListRespDto);
        return likeStoreListRespDto;
    }

    public CustomerStoreInfoRespDto 가게_정보보기(Long storeId) {
        // 이미 가게 상세보기에서 가게가 있는지 검증됐기 때문에 가게 정보만 셀렉해서 뿌리면 끝!
        Optional<Store> storePS = storeRepository.findById(storeId);
        return new CustomerStoreInfoRespDto(storePS.get());
    }

    public CustomerDetailStoreMainRespDto 가게_상세보기(Long storeId) {
        // 1. 가게가 존재하는지?
        Store storePS = storeRepository.findById(storeId)
                .orElseThrow(() -> new CustomApiException("해당 가게 내역이 없습니다.",
                        HttpStatus.BAD_REQUEST));
        log.debug("디버그 : 가게정보 :" + storePS.getName());
        // 2. 별점 평균 데이터 및 리뷰 개수(연산)
        CustomerReviewInterface customerReviewDto = customerReviewRepository.findByStoreId(storeId);
        log.debug("디버그 : 리뷰 별점 :" + customerReviewDto.getStarPoint());
        // 3. 답글 개수 데이터(연산)
        CeoReviewInterface ceoReviewDto = ceoReviewRepository.findByStoreId(storeId);
        log.debug("디버그 : 답글 갯수 : " + ceoReviewDto.getCount());
        // 4. 좋아요 개수 데이터(연산)
        LikeInterface likeDto = likeRepository.findByStoreId(storeId);
        log.debug("디버그 : 좋아요 개수 : " + likeDto.getCount());
        // 5. 메뉴 테이블 데이터 셀렉(리스트)
        List<Menu> menuList = menuRepository.findAllByStoreId(storeId);
        log.debug("디버그 : 메뉴 정보 : " + menuList.get(0).getName());

        // 6. DTO 응답
        return new CustomerDetailStoreMainRespDto(storePS, customerReviewDto, ceoReviewDto, likeDto, menuList);
    }

    public CustomerStoreListRespDto 가게_목록보기() {
        // 1 가게 정보 1셀렉 가게리스트
        List<Store> storeList = storeRepository.findAll();
        log.debug("디버그 : 스토어리스트 : " + storeList);
        // 2 리뷰 별점 셀렉해서 평균내기(평균은 쿼리로 작성) 리뷰리스트
        log.debug("디버그 : 리뷰리스트 전");
        List<CustomerReviewInterface> customerReviewList = customerReviewRepository.findAllByStoreReviewToStarPoint();
        log.debug("디버그 : 리뷰리스트 후: " + customerReviewList);
        // 3 DTO 응답
        return new CustomerStoreListRespDto(storeList, customerReviewList);
    }

    // public StoreListRespDto 가게_목록보기() {
    // // 1 가게 정보 1셀렉 가게리스트
    // List<Store> storeList = storeRepository.findAll();
    // log.debug("디버그 : 스토어리스트 : " + storeList);
    // // 2 리뷰 별점 셀렉해서 평균내기(평균은 쿼리로 작성) 리뷰리스트
    // log.debug("디버그 : 리뷰리스트 전");
    // List<CustomerReview> customerReviewList =
    // customerReviewRepository.starPointAverageToStore();
    // log.debug("디버그 : 리뷰리스트 후: " + customerReviewList);
    // // 3 DTO 응답
    // StoreListRespDto storeListRespDto = new StoreListRespDto(storeList,
    // customerReviewList);
    // return storeListRespDto;
    // }

    @Transactional
    public void updateToBusinessState(CeoUpdateStoreBusinessStateReqDto ceoUpdateStoreBusinessStateReqDto,
            Long userId) {
        Store store = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        store.checkStoreBusinessState();
        storeRepository.save(store.update(ceoUpdateStoreBusinessStateReqDto.toEntity()));
    }

    @Transactional
    public CeoInsertStoreRespDto insert(CeoInsertStoreReqDto ceoInsertStoreReqDto, Long userId) {
        Store store = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        store.checkStoreBusinessState();
        return new CeoInsertStoreRespDto(storeRepository.save(store.update(ceoInsertStoreReqDto.toEntity())));
    }

    @Transactional
    public CeoUpdateStoreRespDto update(CeoUpdateStoreReqDto ceoUpdateStoreReqDto, Long userId) {
        Store store = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        store.checkStoreBusinessState();
        return new CeoUpdateStoreRespDto(storeRepository.save(store.update(ceoUpdateStoreReqDto.toEntity())));
    }

    @Transactional
    public void updateByUserIdToClose(Long id) {
        Store store = storeRepository.findByUserId(id).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        store.checkStoreBusinessState();
        storeRepository.save(store.close(store));
    }

    @Transactional
    public CeoApplyStoreRespDto apply(CeoApplyStoreReqDto ceoApplyStoreReqDto, Long userId) {
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("해당 유저의 아이디가 없습니다.", HttpStatus.BAD_REQUEST));
        if (!storeRepository.findByUserId(userId).isEmpty()) {
            throw new CustomApiException("사용할 수 없는 계정입니다.", HttpStatus.BAD_REQUEST);
        }
        return new CeoApplyStoreRespDto(
                storeRepository.save(ceoApplyStoreReqDto.toEntity(ceoApplyStoreReqDto, userPS)));
    }

    public CeoApplyStoreRespDto findByUserIdToApply(Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        return new CeoApplyStoreRespDto(storePS);
    }

    public CeoDetailStoreRespDto findByUserId(Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        storePS.checkStoreBusinessState();
        return new CeoDetailStoreRespDto(storePS);
    }

    public OrderStatsRespDto findStatsByStoreId(FindStatsReqDto findStatsReqDto, Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        storePS.checkStoreBusinessState();
        storePS.checkCeo(userId);
        findStatsReqDto.setStoreId(storePS.getId());
        return orderRepositoryQuery.findAllOrderStatsByStoreId(findStatsReqDto);
    }

    @Transactional
    public void insertLike(Long userId, Long storeId) {
        Store storePS = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomApiException("해당 아이디의 가게가 없습니다.", HttpStatus.BAD_REQUEST));
        storePS.checkStoreBusinessState();
        User userPS = userRepository.findById(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디의 유저가 없습니다.", HttpStatus.BAD_REQUEST));

        Like likePS = likeRepository.findByUserIdAndStoreId(userId, storePS.getId());

        if (likePS != null) {
            likeRepository.deleteById(likePS.getId());
        } else {
            likeRepository.save(Like.builder().user(userPS).store(storePS).build());
        }
    }

    public List<AdminShowApplyStoreRespDto> findAllToApplyList() {
        List<Store> stores = storeRepository.findAll();
        List<AdminShowApplyStoreRespDto> adminShowApplyStoreRespDtos = new ArrayList<>();
        for (int i = 0; i < stores.size(); i++) {
            adminShowApplyStoreRespDtos.add(i, new AdminShowApplyStoreRespDto(stores.get(i)));
        }
        return adminShowApplyStoreRespDtos;
    }

    @Transactional
    public void updateByStoreIdToAccept(AdminUpdateStoreApplyAcceptReqDto adminUpdateStoreApplyAcceptReqDto,
            Long storeId) {
        Store storePS = storeRepository.findById(storeId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        storePS.updateAccept(adminUpdateStoreApplyAcceptReqDto.isAccept());
        storeRepository.save(storePS);

        User userPS = userRepository.findById(storePS.getUser().getId()).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        userPS.updateRole(adminUpdateStoreApplyAcceptReqDto.isAccept());
        userRepository.save(userPS);
    }
}
