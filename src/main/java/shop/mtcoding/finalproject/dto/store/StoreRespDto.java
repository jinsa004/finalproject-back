package shop.mtcoding.finalproject.dto.store;

import lombok.Getter;
import lombok.Setter;
import shop.mtcoding.finalproject.domain.store.Store;
import shop.mtcoding.finalproject.util.CustomDateUtil;

public class StoreRespDto {

    /* 승현 작업 시작 */

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
