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
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.like.Like;
import shop.mtcoding.finalproject.domain.like.LikeInterface;
import shop.mtcoding.finalproject.domain.menu.Menu;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.util.CustomDateUtil;

public class StoreRespDto {

    /* 성진 작업 시작@!@ */

    @Getter
    @Setter
    public static class StoreInfoRespDto {
        private String notice;
        private String minAmount;
        private String deliveryHour;
        private String deliveryCost;
        private String ceoName;
        private String businessNumber;
        private String businessAddress;

        public StoreInfoRespDto(Store store) {
            this.notice = store.getNotice();
            this.minAmount = store.getMinAmount();
            this.deliveryHour = store.getDeliveryHour();
            this.deliveryCost = store.getDeliveryCost();
            this.ceoName = store.getCeoName();
            this.businessNumber = store.getBusinessNumber();
            this.businessAddress = store.getBusinessAddress();
        }

    }

    @Getter
    @Setter
    public static class DetailStoreMainRespDto {
        private String name;
        private String minAmount;
        private String deliveryHour;
        private String deliveryCost;
        private String phone;
        private Double starPoint;
        private Long reviewCount;
        private Long commentCount;
        private Long likeCount;

        private List<MenuDto> menuList = new ArrayList<>();

        public DetailStoreMainRespDto(Store store, CustomerReviewInterface customerReviewDto,
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
            private String price;
            private MenuCategoryEnum category;

            public MenuDto(Menu menu) {
                this.name = menu.getName();
                this.intro = menu.getIntro();
                this.thumbnail = menu.getThumbnail();
                this.price = menu.getPrice();
                this.category = menu.getCategory();
            }

        }

    }

    // @Getter
    // @Setter
    // public static class StoreListRespDto {
    // private List<StoreDto> storeList = new ArrayList<>();

    // @Getter
    // @Setter
    // public class StoreDto {
    // private String name;
    // private String thumbnail;
    // private String deliveryCost;
    // private String intro;
    // private CustomerReview customerReview;

    // public StoreDto(Store store) {
    // this.name = store.getName();
    // this.thumbnail = store.getThumbnail();
    // this.deliveryCost = store.getDeliveryCost();
    // this.intro = store.getIntro();
    // this.customerReview = store.get;
    // }

    // }

    // }

    // 강사님이 짜주신 로직!!
    @Getter
    @Setter
    public static class StoreListRespDto {
        private List<StoreDto> stores = new ArrayList<>();

        public StoreListRespDto(List<Store> storesPS, List<CustomerReview> customerReviewsPS) {
            for (Store store : storesPS) {

                List<CustomerReview> tempReviews = new ArrayList<>();

                for (CustomerReview customerReview : customerReviewsPS) {
                    if (customerReview.getStore().getId() == store.getId()) {
                        tempReviews.add(customerReview);
                    }
                }

                stores.add(new StoreDto(store, tempReviews));
            }

        }

        @Getter
        @Setter
        public class StoreDto {
            private Long storeId;
            private String storeName;
            private String deliveryCost;
            private String intro;
            private String thumbnail;

            private List<CustomerReviewDto> customerReviews = new ArrayList<>();

            public StoreDto(Store store, List<CustomerReview> crs) {
                this.storeId = store.getId();
                this.storeName = store.getName();
                this.deliveryCost = store.getDeliveryCost();
                this.intro = store.getIntro();
                this.thumbnail = store.getThumbnail();
                this.customerReviews = crs.stream().map(CustomerReviewDto::new).collect(Collectors.toList());
            }

            @Getter
            @Setter
            public class CustomerReviewDto {
                private Long storeId;
                private Double starPoint;

                public CustomerReviewDto(CustomerReview customerReview) {
                    this.storeId = customerReview.getStore().getId();
                    this.starPoint = customerReview.getStarPoint();
                }

            }

        }

    }

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class UpdateBusinessStateRespDto {

        private boolean isOpend;

        public UpdateBusinessStateRespDto(Store store) {
            this.isOpend = store.isOpend();
        }
    }

    @Getter
    @Setter
    public static class UpdateStoreRespDto {
        private StoreCategoryEnum category;
        private String name;
        private String phone;
        private String thumbnail;
        private String ceoName;
        private String businessNumber;
        private String businessAddress;
        private String openTime;
        private String closeTime;
        private String minAmount;
        private String deliveryHour;
        private String deliveryCost;
        private String intro;
        private String notice;

        public UpdateStoreRespDto(Store store) {
            this.category = store.getCategory();
            this.name = store.getName();
            this.phone = store.getPhone();
            this.thumbnail = store.getThumbnail();
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
    public static class DetailStoreRespDto {
        private StoreCategoryEnum category;
        private String name;
        private String phone;
        private String thumbnail;
        private String ceoName;
        private String businessNumber;
        private String businessAddress;
        private String openTime;
        private String closeTime;
        private String minAmount;
        private String deliveryHour;
        private String deliveryCost;
        private String intro;
        private String notice;

        public DetailStoreRespDto(Store store) {
            this.category = store.getCategory();
            this.name = store.getName();
            this.phone = store.getPhone();
            this.thumbnail = store.getThumbnail();
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
    public static class InsertStoreRespDto {
        private StoreCategoryEnum category;
        private String name;
        private String phone;
        private String thumbnail;
        private String ceoName;
        private String businessNumber;
        private String businessAddress;
        private String openTime;
        private String closeTime;
        private String minAmount;
        private String deliveryHour;
        private String deliveryCost;
        private String intro;
        private String notice;

        public InsertStoreRespDto(Store store) {
            this.category = store.getCategory();
            this.name = store.getName();
            this.phone = store.getPhone();
            this.thumbnail = store.getThumbnail();
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
    public static class ApplyRespDto {

        private String ceoName;
        private String businessNumber;
        private String businessAddress;
        private String createTime;
        private boolean accept;

        public ApplyRespDto(Store store) {
            this.ceoName = store.getCeoName();
            this.businessNumber = store.getBusinessNumber();
            this.businessAddress = store.getBusinessAddress();
            this.createTime = CustomDateUtil.toStringFormat(store.getCreatedAt());
            this.accept = store.isAccept();
        }

    }

    /* 승현 작업 종료 */
}
