package shop.mtcoding.finalproject.domain.reportReview;

import java.math.BigInteger;
import java.sql.Timestamp;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ReportReviewRespDto {

    private Double starPoint;
    private String photo;
    private BigInteger id;
    private Timestamp createdAt;
    private Boolean isClosure;
    private Boolean isResolve;
    private String adminComment;

    // public ReportReviewRespDto(CustomerReview customerReview, ReportReview
    // reportReview) {
    // this.starPoint = customerReview.getStarPoint();
    // this.photo = customerReview.getPhoto();
    // this.orderId = customerReview.getOrder().getId();
    // this.orderCreatedAt =
    // CustomDateUtil.toStringFormat(customerReview.getOrder().getCreatedAt());
    // this.isClosure = customerReview.isClosure();
    // this.isResolve = reportReview.isResolve();
    // this.adminComment = reportReview.getAdminComment();
    // }
}
