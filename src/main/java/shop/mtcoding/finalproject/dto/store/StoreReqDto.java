package shop.mtcoding.finalproject.dto.store;

import javax.persistence.Column;
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

        @Column(nullable = false, length = 20)
        @NotBlank(message = "사업자명은 필수입니다.")
        @Pattern(regexp = "^[가-힣a-zA-Z0-9]{2,10}$", message = "이름은 특수문자를 포함하지 않은 2~20자리여야 합니다.")
        @Size(min = 2, max = 20, message = "이름을 2~20자 사이로 입력해주세요.")
        private String ceoName;

        @NotBlank(message = "사업자번호는 필수입니다.")
        @Pattern(regexp = "^[0-9]", message = "숫자만 입력가능합니다.")
        @Column(nullable = false, length = 10)
        private String businessNumber;

        @NotBlank(message = "사업자주소는 필수입니다.")
        @Column(nullable = false, length = 60)
        private String businessAddress;

        public Store toEntity(Long userId) {
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

    /* 승현 작업 종료 */
}
