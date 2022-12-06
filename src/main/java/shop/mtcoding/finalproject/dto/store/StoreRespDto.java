package shop.mtcoding.finalproject.dto.store;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
import shop.mtcoding.finalproject.domain.customerReview.CustomerReview;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.util.CustomDateUtil;

public class StoreRespDto {

    /* 성진 작업 시작@!@ */
    @Getter
    @Setter
    public static class StoreListRespDto {
        private List<StoreDto> stores = new ArrayList<>();
        private List<CustomerReviewDto> customerReviews = new ArrayList<>();

        public StoreListRespDto(List<Store> stores, List<CustomerReview> customerReviews) {
            this.stores = stores.stream().map((store) -> new StoreDto(store))
                    .collect(Collectors.toList());
            ;
            this.customerReviews = customerReviews.stream()
                    .map((customerReview) -> new CustomerReviewDto(customerReview))
                    .collect(Collectors.toList());
            ;
        }

        @Getter
        @Setter
        public class StoreDto {
            private Long storeId;
            private String storeName;
            private String deliveryCost;
            private String intro;
            private String thumbnail;

            public StoreDto(Store store) {
                this.storeId = store.getId();
                this.storeName = store.getName();
                this.deliveryCost = store.getDeliveryCost();
                this.intro = store.getIntro();
                this.thumbnail = store.getThumbnail();
            }

        }

        @Getter
        @Setter
        public class CustomerReviewDto {
            private Long storeId;
            private int starPoint;

            public CustomerReviewDto(CustomerReview customerReview) {
                this.storeId = customerReview.getOrder().getStore().getId();
                this.starPoint = customerReview.getStarPoint();
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
