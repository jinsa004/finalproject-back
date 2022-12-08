package shop.mtcoding.finalproject.domain.customerReview;

public interface CustomerInterface {
    // 리뷰갯수
    Long getCount();

    // 별점평균
    Double getStarPoint();

    // 유저 닉네임
    String getNickname();

    // 유저 프로필사진
    String getUPhoto();

    // 리뷰 사진
    String getCrPhoto();

    // 리뷰 내용
    String getContent();

    // 사장님 답글
    String getComment();
}
