package shop.mtcoding.finalproject.dto.store;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.util.CustomDateUtil;

public class StoreRespDto {

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
