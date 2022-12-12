package shop.mtcoding.finalproject.dto.reportReview;

import java.math.BigInteger;
import java.sql.Timestamp;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportCeoReviewRespDto {
    private BigInteger reportReviewId;
    private Double starPoint;
    private String photo;
    private BigInteger orderId;
    private Timestamp createdAt;
    private Boolean isClosure;
    private Boolean isResolve;
    private String adminComment;
}
