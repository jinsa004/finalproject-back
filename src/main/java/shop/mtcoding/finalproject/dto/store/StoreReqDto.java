package shop.mtcoding.finalproject.dto.store;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.domain.user.User;
import shop.mtcoding.finalproject.util.CustomEnumUtil;

public class StoreReqDto {

    /* 승현 작업 시작 */
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

        @NotBlank(message = "카테고리는 필수입니다.")
        private String category;

        @NotBlank(message = "가게이름은 필수입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,30}$", message = "이름은 특수문자를 포함하지 않은 1~30자리여야 합니다.")
        @Size(min = 2, max = 30, message = "이름을 1~30자 사이로 입력해주세요.")
        private String name;

        @NotBlank(message = "가게 연락처는 필수입니다.")
        @Size(min = 10, max = 15, message = "전화번호를 10 ~ 15자 사이로 입력해주세요.")
        private String phone;

        private String thumbnail;

        @NotBlank(message = "가게 영업시작 시간은 필수입니다.")
        private String openTime;

        @NotBlank(message = "가게 영업종료 시간은 필수입니다.")
        private String closeTime;

        @NotBlank(message = "최소 금액은 필수입니다.")
        private String minAmount;

        @NotBlank(message = "배달 평균 시간은 필수입니다.")
        private String deliveryHour;

        @NotBlank(message = "배달 최소금액은 필수입니다.")
        private String deliveryCost;

        @NotBlank(message = "가게 소개글은 필수입니다.")
        private String intro;

        @NotBlank(message = "배달 최소금액은 필수입니다.")
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

        @NotBlank(message = "카테고리는 필수입니다.")
        private String category;

        @NotBlank(message = "가게이름은 필수입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{1,30}$", message = "이름은 특수문자를 포함하지 않은 1~30자리여야 합니다.")
        @Size(min = 2, max = 30, message = "이름을 1~30자 사이로 입력해주세요.")
        private String name;

        @NotBlank(message = "가게 연락처는 필수입니다.")
        @Size(min = 10, max = 15, message = "전화번호를 10 ~ 15자 사이로 입력해주세요.")
        private String phone;

        private String thumbnail;

        @NotBlank(message = "가게 영업시작 시간은 필수입니다.")
        private String openTime;

        @NotBlank(message = "가게 영업종료 시간은 필수입니다.")
        private String closeTime;

        @NotBlank(message = "최소 금액은 필수입니다.")
        private String minAmount;

        @NotBlank(message = "배달 평균 시간은 필수입니다.")
        private String deliveryHour;

        @NotBlank(message = "배달 최소금액은 필수입니다.")
        private String deliveryCost;

        @NotBlank(message = "가게 소개글은 필수입니다.")
        private String intro;

        @NotBlank(message = "배달 최소금액은 필수입니다.")
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

        @NotBlank(message = "사업자명은 필수입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,20}$", message = "이름은 특수문자를 포함하지 않은 2~20자리여야 합니다.")
        @Size(min = 2, max = 20, message = "이름을 2~20자 사이로 입력해주세요.")
        private String ceoName;

        @NotBlank(message = "사업자번호는 필수입니다.")
        @Pattern(regexp = "^[0-9]{10}", message = "숫자만 입력가능합니다.")
        @Size(min = 10, max = 10, message = "사업자번호는 10자리입니다.")
        private String businessNumber;

        @NotBlank(message = "사업자주소는 필수입니다.")
        @Size(min = 10, max = 10)
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
