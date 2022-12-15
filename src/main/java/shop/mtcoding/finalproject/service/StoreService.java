package shop.mtcoding.finalproject.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.persistence.NoResultException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import lombok.RequiredArgsConstructor;
import shop.mtcoding.finalproject.config.auth.LoginUser;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
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
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CategoryStoreListRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CeoApplyStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CeoDetailStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CeoInsertStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CeoUpdateStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CustomerDetailStoreMainRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CustomerStoreInfoRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.CustomerStoreListRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.DetailToSaveStoreRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.LikeStoreListRespDto;
import shop.mtcoding.finalproject.dto.store.StoreRespDto.StoreNameRespDto;

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

    public CategoryStoreListRespDto categoryStoreList(StoreCategoryEnum category) {
        // 1. 가게정보 카테고리에 맞는 값을 셀렉
        log.debug("디버그 : 카테고리 가게 셀렉 전");
        List<Store> storeList = storeRepository.findAllByCategory(category);
        log.debug("디버그 : 카테고리 가게 리스트 크기 : " + storeList.size());
        log.debug("디버그 : 카테고리 가게 셀렉 후" + storeList.get(0).getId());
        // 2. 가게 목록보기에 필요한 연산데이터(리뷰 개수, 평균 별점)
        log.debug("디버그 : 연산데이터 전");
        List<CustomerReviewInterface> customerReviewList = customerReviewRepository.findAllByStoreReviewToStarPoint();
        log.debug("디버그 : 연산데이터 후 : 리뷰리스트 크기 : " + customerReviewList.size());
        log.debug("디버그 : 연산데이터 후 : 가게아이디 : " + customerReviewList.get(3).getStoreId());
        log.debug("디버그 : 연산데이터 후 : 리뷰아이디 : " + customerReviewList.get(3).getReviewId());
        // 3. DTO 응답
        log.debug("디버그 : DTO 응답 전");
        CategoryStoreListRespDto categoryStoreListRespDto = new CategoryStoreListRespDto(storeList,
                customerReviewList);
        log.debug("디버그 : DTO 응답 후" + categoryStoreListRespDto.getStores().get(0).getStarPoint());
        try {
            return categoryStoreListRespDto;
        } catch (NoResultException e) {
            return null;
        }
    }

    public StoreNameRespDto checkStoreName(Long userId, LoginUser loginUser) {
        // 1. 가게 셀렉
        Store storePS = storeRepository.findByUserIdToStoreCheck(loginUser.getUser().getId())
                .orElseThrow(() -> new CustomApiException("입점미신청", HttpStatus.BAD_GATEWAY));
        log.debug("디버그 : 가게 정보 들고오나? : " + storePS.getId());
        log.debug("디버그 : 가게 정보 들고오나? : " + storePS.getName());
        // 2. 입점신청 대기유저를 위한 로직(신청이 됐으면 storeId는 있기때문에 입점신청을 구분하는 isAccept 컬럼사용)
        if (storePS.isAccept() == false) {
            throw new CustomApiException("입점대기", HttpStatus.BAD_REQUEST);
        }
        // 3. DTO 응답
        StoreNameRespDto storeNameRespDto = new StoreNameRespDto(storePS, loginUser.getUser());
        log.debug("디버그 : DTO에 담겨서 들고오나? : " + storeNameRespDto.getName());
        try {
            return storeNameRespDto;
        } catch (NoResultException e) {
            return null;
        }
    }

    public LikeStoreListRespDto likeStoreList(Long userId) {
        // 1. 찜한 가게 목록보기 join fetch를 이용한 기능
        List<Like> likeList = likeRepository.findByUserIdToLikeStore(userId);
        log.debug("디버그 : 가게 목록보자 : " + likeList.get(0).getStore().getName());
        // 2. 평균별점, 리뷰개수 연산데이터
        List<CustomerReviewInterface> customerReviewList = customerReviewRepository.findAllByStoreReviewToStarPoint();
        log.debug("디버그 : 너가 문제니 연산데이터? : 1번가게 별점" + customerReviewList.get(0).getStarPoint());
        // 3. DTO 응답
        LikeStoreListRespDto likeStoreListRespDto = new LikeStoreListRespDto(likeList, customerReviewList);
        log.debug("디버그 : " + likeStoreListRespDto);
        try {
            return likeStoreListRespDto;
        } catch (NoResultException e) {
            return null;
        }
    }

    public CustomerStoreInfoRespDto customerStoreInfo(Long storeId) {
        // 이미 가게 상세보기에서 가게가 있는지 검증됐기 때문에 가게 정보만 셀렉해서 뿌리면 끝!
        Optional<Store> storePS = storeRepository.findById(storeId);
        return new CustomerStoreInfoRespDto(storePS.get());
    }

    public CustomerDetailStoreMainRespDto customerDetailStore(Long storeId) {
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
        try {
            // 6. DTO 응답
            return new CustomerDetailStoreMainRespDto(storePS, customerReviewDto, ceoReviewDto, likeDto, menuList);
        } catch (NoResultException e) {
            return null;
        }
    }

    public CustomerStoreListRespDto customerStoreList() {
        // 1 가게 정보 1셀렉 가게리스트
        List<Store> storeList = storeRepository.findAllToAcceptStoreList();
        log.debug("디버그 : 스토어리스트 : " + storeList);
        // 2 리뷰 별점 셀렉해서 평균내기(평균은 쿼리로 작성) 리뷰리스트
        log.debug("디버그 : 리뷰리스트 전");
        List<CustomerReviewInterface> customerReviewList = customerReviewRepository.findAllByStoreReviewToStarPoint();
        log.debug("디버그 : 리뷰리스트 후: " + customerReviewList);
        // 3 DTO 응답
        try {
            return new CustomerStoreListRespDto(storeList, customerReviewList);
        } catch (NoResultException e) {
            return null;
        }
    }

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
        try {
            return new CeoApplyStoreRespDto(storePS);
        } catch (NoResultException e) {
            return null;
        }
    }

    public CeoDetailStoreRespDto findByUserId(Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        storePS.checkStoreBusinessState();
        try {
            return new CeoDetailStoreRespDto(storePS);
        } catch (NoResultException e) {
            return null;
        }
    }

    public OrderStatsRespDto findStatsByStoreId(FindStatsReqDto findStatsReqDto, Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        storePS.checkStoreBusinessState();
        storePS.checkCeo(userId);
        findStatsReqDto.setStoreId(storePS.getId());
        try {
            return orderRepositoryQuery.findAllOrderStatsByStoreId(findStatsReqDto);
        } catch (NoResultException e) {
            return null;
        }
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
        try {
            return adminShowApplyStoreRespDtos;
        } catch (NoResultException e) {
            return null;
        }
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

    public DetailToSaveStoreRespDto findByIdToSave(Long userId) {
        Store storePS = storeRepository.findByUserId(userId).orElseThrow(
                () -> new CustomApiException("해당 아이디로 신청한 내역이 없습니다.", HttpStatus.BAD_REQUEST));
        storePS.checkStoreBusinessState();
        storePS.checkCeo(userId);
        try {
            return new DetailToSaveStoreRespDto(storePS);
        } catch (NoResultException e) {
            return null;
        }
    }
}
