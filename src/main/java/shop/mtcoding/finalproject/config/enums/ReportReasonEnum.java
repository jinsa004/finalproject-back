package shop.mtcoding.finalproject.config.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ReportReasonEnum {
    HONOR("명예훼손"), PORTRAITRIGHTS("초상권침해"), COPYRIGHT("저작권침해"), TRADEMARKRIGHT("상표권침해"), ETC("기타사유");

    private String reason;
}
