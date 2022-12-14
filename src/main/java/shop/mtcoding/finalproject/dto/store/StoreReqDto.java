package shop.mtcoding.finalproject.dto.store;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.util.CustomBase64ConvertUtil;
import shop.mtcoding.finalproject.util.CustomEnumUtil;

public class StoreReqDto {

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class AdminUpdateStoreApplyAcceptReqDto {
        private boolean isAccept;
    }

    @Getter
    @Setter
    public static class CeoUpdateStoreBusinessStateReqDto {

        private boolean isOpend;

        public Store toEntity() {
            return Store.builder()
                    .isOpend(this.isOpend)
                    .build();
        }

    }

    @Getter
    @Setter
    public static class CeoUpdateStoreReqDto {

        private String category;

        private String name;

        private String phone;

        private String thumbnail;

        private String openTime;

        private String closeTime;

        private int minAmount;

        private String deliveryHour;

        private int deliveryCost;

        private String intro;

        private String notice;

        public Store toEntity() {
            return Store.builder()
                    .category(CustomEnumUtil.toStoreCategoryEnumFormat(category))
                    .name(name)
                    .phone(phone)
                    .thumbnail(thumbnail)
                    .openTime(openTime)
                    .closeTime(closeTime)
                    .minAmount(minAmount)
                    .deliveryHour(deliveryHour)
                    .deliveryCost(deliveryCost)
                    .intro(intro)
                    .notice(notice)
                    .build();
        }
    }

    @Getter
    @Setter
    public static class CeoInsertStoreReqDto {

        private String category;

        private String name;

        private String phone;

        private String thumbnail;

        private String openTime;

        private String closeTime;

        private int minAmount;

        private String deliveryHour;

        private int deliveryCost;

        private String intro;

        private String notice;

        public Store toEntity() {
            return Store.builder()
                    .category(CustomEnumUtil.toStoreCategoryEnumFormat(category))
                    .name(name)
                    .phone(phone)
                    .thumbnail(thumbnail)
                    .openTime(openTime)
                    .closeTime(closeTime)
                    .minAmount(minAmount)
                    .deliveryHour(deliveryHour)
                    .deliveryCost(deliveryCost)
                    .intro(intro)
                    .notice(notice)
                    .build();
        }

    }

    @Getter
    @Setter
    public static class CeoApplyStoreReqDto {

        private String ceoName;

        private String businessNumber;

        private String businessAddress;

        public Store toEntity(CeoApplyStoreReqDto ceoApplyStoreReqDto, User user) {
            return Store.builder()
                    .ceoName(ceoApplyStoreReqDto.getCeoName())
                    .businessNumber(ceoApplyStoreReqDto.getBusinessNumber())
                    .businessAddress(ceoApplyStoreReqDto.getBusinessAddress())
                    .user(user)
                    .isOpend(false)
                    .isAccept(false)
                    .isClosure(false)
                    .build();
        }

    }
    /* 승현 작업 종료 */
}
