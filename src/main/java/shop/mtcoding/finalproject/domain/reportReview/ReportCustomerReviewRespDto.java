package shop.mtcoding.finalproject.domain.reportReview;

import java.math.BigInteger;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportCustomerReviewRespDto {
    private BigInteger id;
    private String reason;
    private String content;
    private String nickname;
}