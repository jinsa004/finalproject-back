package shop.mtcoding.finalproject.dto.store;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.store.Store;

public class StoreReqDto {

    /* 승현 작업 시작 */

    @Getter
    @Setter
    public static class ApplyReqDto {

        private Long userId;

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

        public Store toEntity() {
            return Store.builder()
                    .userId(userId)
                    .category(null)
                    .name(null)
                    .phone(null)
                    .thumbnail(null)
                    .ceoName(ceoName)
                    .businessNumber(businessNumber)
                    .businessAddress(businessAddress)
                    .openTime(null)
                    .closeTime(null)
                    .minAmount(null)
                    .deliveryHour(null)
                    .deliveryCost(null)
                    .intro(null)
                    .notice(null)
                    .opend(false)
                    .accept(false)
                    .build();
        }
    }

    @Getter @Setter
    public static class SaveReqDto {
        
        private String category;
        private String name;
        private String phone;
        private String thumbnail;
        private String openTime;
        private String closeTime;
        private String minAmount;
        private String deliveryHour;
        private String deliveryCost;
        private String intro;
        private String notice;
        private boolean opend;
    }

    /* 승현 작업 종료 */
}
