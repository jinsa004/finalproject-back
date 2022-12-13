package shop.mtcoding.finalproject.dto.store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.enums.MenuCategoryEnum;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
import shop.mtcoding.finalproject.domain.ceoReview.CeoReviewInterface;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReviewInterface;
import shop.mtcoding.finalproject.domain.like.Like;
import shop.mtcoding.finalproject.domain.like.LikeInterface;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.util.CustomBase64ConvertUtil;
import shop.mtcoding.finalproject.util.CustomDateUtil;

public class StoreRespDto {

    /* 성진 작업 시작@!@ */

    @Getter
    @Setter
    public static class LikeStoreListRespDto {
        private List<LikeDto> likes = new ArrayList<>();

        public LikeStoreListRespDto(List<Like> likeList, List<CustomerReviewInterface> customerReviewInterfaceList) {
            for (Like like : likeList) {
                for (CustomerReviewInterface customerReviewInterface : customerReviewInterfaceList) {
                    if (customerReviewInterface.getStoreId() == like.getStore().getId()) {
                        likes.add(new LikeDto(like, customerReviewInterface));
                    }
                }
            }
        }

        @Getter
        @Setter
        public class LikeDto {
            private Long storeId;
            private String storeName;
            private int deliveryCost;
            private String intro;
            private String thumbnail;
            private Long count;
            private Double starPoint;

            public LikeDto(Like like, CustomerReviewInterface customerReviewInterfacePS) {
                this.storeId = like.getStore().getId();
                this.storeName = like.getStore().getName();
                this.deliveryCost = like.getStore().getDeliveryCost();
                this.intro = like.getStore().getIntro();
                this.thumbnail = CustomBase64ConvertUtil.convertToString(like.getStore().getThumbnail());
                this.count = customerReviewInterfacePS.getCount();
                this.starPoint = customerReviewInterfacePS.getStarPoint();
            }

        }
    }

    @Getter
    @Setter
    public static class CustomerStoreInfoRespDto {
        private String notice;
        private int minAmount;
        private String deliveryHour;
        private int deliveryCost;
        private String name;
        private String ceoName;
        private String businessNumber;
        private String businessAddress;

        public CustomerStoreInfoRespDto(Store store) {
            this.notice = store.getNotice();
            this.minAmount = store.getMinAmount();
            this.deliveryHour = store.getDeliveryHour();
            this.deliveryCost = store.getDeliveryCost();
            this.name = store.getName();
            this.ceoName = store.getCeoName();
            this.businessNumber = store.getBusinessNumber();
            this.businessAddress = store.getBusinessAddress();
        }

    }

    @Getter
    @Setter
    public static class CustomerDetailStoreMainRespDto {
        private String name;
        private int minAmount;
        private String deliveryHour;
        private int deliveryCost;
        private String phone;
        private Double starPoint;
        private Long reviewCount;
        private Long commentCount;
        private Long likeCount;

        private List<MenuDto> menuList = new ArrayList<>();

        public CustomerDetailStoreMainRespDto(Store store, CustomerReviewInterface customerReviewDto,
                CeoReviewInterface ceoReviewDto, LikeInterface likeDto, List<Menu> menus) {
            this.name = store.getName();
            this.minAmount = store.getMinAmount();
            this.deliveryHour = store.getDeliveryHour();
            this.deliveryCost = store.getDeliveryCost();
            this.phone = store.getPhone();
            this.starPoint = customerReviewDto.getStarPoint();
            this.reviewCount = customerReviewDto.getCount();
            this.commentCount = ceoReviewDto.getCount();
            this.likeCount = likeDto.getCount();
            this.menuList = menus.stream().map(MenuDto::new).collect(Collectors.toList());
        }

        @Getter
        @Setter
        public class MenuDto {
            private String name;
            private String intro;
            private String thumbnail;
            private int price;
            private String category;

            public MenuDto(Menu menu) {
                this.name = menu.getName();
                this.intro = menu.getIntro();
                this.thumbnail = CustomBase64ConvertUtil.convertToString(menu.getThumbnail());
                this.price = menu.getPrice();
                this.category = menu.getCategory().getCategory();
            }

        }

    }

    @Getter
    @Setter
    public static class CustomerStoreListRespDto {
        private List<StoreDto> stores = new ArrayList<>();

        public CustomerStoreListRespDto(List<Store> storesPS,
                List<CustomerReviewInterface> customerReviewInterfaceList) {
            for (Store store : storesPS) {
                for (CustomerReviewInterface customerReviewInterface : customerReviewInterfaceList) {
                    if (customerReviewInterface.getStoreId() == store.getId()) {
                        stores.add(new StoreDto(store, customerReviewInterface));
                    }
                }
            }
        }

        @Getter
        @Setter
        public class StoreDto {
            private Long storeId;
            private String storeName;
            private int deliveryCost;
            private String intro;
            private String thumbnail;
            private Long count;
            private Double starPoint;

            public StoreDto(Store store, CustomerReviewInterface customerReviewInterfacePS) {
                this.storeId = store.getId();
                this.storeName = store.getName();
                this.deliveryCost = store.getDeliveryCost();
                this.intro = store.getIntro();
                this.thumbnail = CustomBase64ConvertUtil.convertToString(store.getThumbnail());
                this.count = customerReviewInterfacePS.getCount();
                this.starPoint = customerReviewInterfacePS.getStarPoint();
            }

        }

    }

    // 강사님이 짜주신 로직!!(해당 가게에 있는 모든 리뷰의 목록을 띄우는 방식)
    // @Getter
    // @Setter
    // public static class StoreListRespDto {
    // private List<StoreDto> stores = new ArrayList<>();

    // public StoreListRespDto(List<Store> storesPS, List<CustomerReview>
    // customerReviewsPS) {
    // for (Store store : storesPS) {

    // List<CustomerReview> tempReviews = new ArrayList<>();

    // for (CustomerReview customerReview : customerReviewsPS) {
    // if (customerReview.getStore().getId() == store.getId()) {
    // tempReviews.add(customerReview);
    // }
    // }

    // stores.add(new StoreDto(store, tempReviews));
    // }

    // }

    // @Getter
    // @Setter
    // public class StoreDto {
    // private Long storeId;
    // private String storeName;
    // private String deliveryCost;
    // private String intro;
    // private String thumbnail;

    // private List<CustomerReviewDto> customerReviews = new ArrayList<>();

    // public StoreDto(Store store, List<CustomerReview> crs) {
    // this.storeId = store.getId();
    // this.storeName = store.getName();
    // this.deliveryCost = store.getDeliveryCost();
    // this.intro = store.getIntro();
    // this.thumbnail = store.getThumbnail();
    // this.customerReviews =
    // crs.stream().map(CustomerReviewDto::new).collect(Collectors.toList());
    // }

    // @Getter
    // @Setter
    // public class CustomerReviewDto {
    // private Long storeId;
    // private Double starPoint;

    // public CustomerReviewDto(CustomerReview customerReview) {
    // this.storeId = customerReview.getStore().getId();
    // this.starPoint = customerReview.getStarPoint();
    // }

    // }

    // }

    // }

    /* 성진 작업 종료 */

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class AdminShowApplyStoreRespDto {
        private Long id;
        private String username;
        private String businessNumber;
        private String ceoName;
        private boolean isAccept;

        public AdminShowApplyStoreRespDto(Store store) {
            this.id = store.getId();
            this.username = store.getUser().getUsername();
            this.businessNumber = store.getBusinessNumber();
            this.ceoName = store.getCeoName();
            this.isAccept = store.isAccept();
        }
    }

    @Getter
    @Setter
    public static class CeoUpdateStoreRespDto {
        private String category;
        private String name;
        private String phone;
        private String thumbnail;
        private String ceoName;
        private String businessNumber;
        private String businessAddress;
        private String openTime;
        private String closeTime;
        private int minAmount;
        private String deliveryHour;
        private int deliveryCost;
        private String intro;
        private String notice;

        public CeoUpdateStoreRespDto(Store store) {
            this.category = store.getCategory().getCategory();
            this.name = store.getName();
            this.phone = store.getPhone();
            this.thumbnail = CustomBase64ConvertUtil.convertToString(store.getThumbnail());
            this.ceoName = store.getCeoName();
            this.businessNumber = store.getBusinessNumber();
            this.businessAddress = store.getBusinessAddress();
            this.openTime = store.getOpenTime();
            this.closeTime = store.getCloseTime();
            this.minAmount = store.getMinAmount();
            this.deliveryHour = store.getDeliveryHour();
            this.deliveryCost = store.getDeliveryCost();
            this.intro = store.getIntro();
            this.notice = store.getNotice();
        }

    }

    @Getter
    @Setter
    public static class CeoDetailStoreRespDto {
        private String category;
        private String name;
        private String phone;
        private String thumbnail;
        private String ceoName;
        private String businessNumber;
        private String businessAddress;
        private String openTime;
        private String closeTime;
        private int minAmount;
        private String deliveryHour;
        private int deliveryCost;
        private String intro;
        private String notice;

        public CeoDetailStoreRespDto(Store store) {
            this.category = store.getCategory().getCategory();
            this.name = store.getName();
            this.phone = store.getPhone();
            this.thumbnail = CustomBase64ConvertUtil.convertToString(store.getThumbnail());
            this.ceoName = store.getCeoName();
            this.businessNumber = store.getBusinessNumber();
            this.businessAddress = store.getBusinessAddress();
            this.openTime = store.getOpenTime();
            this.closeTime = store.getCloseTime();
            this.minAmount = store.getMinAmount();
            this.deliveryHour = store.getDeliveryHour();
            this.deliveryCost = store.getDeliveryCost();
            this.intro = store.getIntro();
            this.notice = store.getNotice();
        }

    }

    @Getter
    @Setter
    public static class CeoInsertStoreRespDto {
        private String category;
        private String name;
        private String phone;
        private String thumbnail;
        private String ceoName;
        private String businessNumber;
        private String businessAddress;
        private String openTime;
        private String closeTime;
        private int minAmount;
        private String deliveryHour;
        private int deliveryCost;
        private String intro;
        private String notice;

        public CeoInsertStoreRespDto(Store store) {
            this.category = store.getCategory().getCategory();
            this.name = store.getName();
            this.phone = store.getPhone();
            this.thumbnail = CustomBase64ConvertUtil.convertToString(store.getThumbnail());
            this.ceoName = store.getCeoName();
            this.businessNumber = store.getBusinessNumber();
            this.businessAddress = store.getBusinessAddress();
            this.openTime = store.getOpenTime();
            this.closeTime = store.getCloseTime();
            this.minAmount = store.getMinAmount();
            this.deliveryHour = store.getDeliveryHour();
            this.deliveryCost = store.getDeliveryCost();
            this.intro = store.getIntro();
            this.notice = store.getNotice();
        }

    }

    @Getter
    @Setter
    public static class CeoApplyStoreRespDto {

        private String ceoName;
        private String businessNumber;
        private String businessAddress;
        private String createTime;
        private boolean accept;

        public CeoApplyStoreRespDto(Store store) {
            this.ceoName = store.getCeoName();
            this.businessNumber = store.getBusinessNumber();
            this.businessAddress = store.getBusinessAddress();
            this.createTime = CustomDateUtil.toStringFormat(store.getCreatedAt());
            this.accept = store.isAccept();
        }

    }

    /* 승현 작업 종료 */
}
