# 테이블 생성

### table수정사항
- reviews 이름 수정 > customer_review로 수정
- commants 이름 수정 > ceo_review로 수정
<br>

### colume추가 및 수정사항
<추가>
- user: createdTime 삭제(AudingTime 상속), photo 추가, email 추가
- store: createdTime 삭제(AudingTime 상속)
- reportReview: createdTime 삭제(AudingTime 상속) 

<수정>
- store: ownerName 이름 수정 > ceoName
<br>

### 참고사항
- AudingTime(강사님 구현하신 것) 수정함