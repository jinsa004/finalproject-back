package shop.mtcoding.finalproject.dto.store;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.config.enums.StoreCategoryEnum;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.util.CustomDateUtil;

public class StoreRespDto {

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class ShowStoreRespDto {
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

        public ShowStoreRespDto(Store store) {
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
    public static class SaveStoreRespDto {
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

        public SaveStoreRespDto(Store store) {
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

    @Getter
    @Setter
    public static class UserDto {
        private Long id;

        public UserDto(User user) {
            this.id = user.getId();
        }
    }

    /* 승현 작업 종료 */
}
