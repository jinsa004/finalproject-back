# 테이블 생성

### 추가사항
- usr

### 수정사항
1. table
- reviews 이름 수정 > customer_review로 수정 : 누가 사용하는 리뷰인지 명시함
- commants 이름 수정 > ceo_review로 수정 : customer_review와 사용용도가 비슷하고 누가 사용하는 테이블인지 명시함

2. colume
(추가)
- user: createdTime 추가, photo 추가, email 추가
- store: createdTime 추가
- reportReview: createdTime 추가
- 

(수정)
- 공통적: created 이름 수정 > createdTime : 시간으로 사용되는 컬럼이 두개인데 하나만 Time이 붙으면 이상하다고 판단하여 동일한 시간컬럼인 created도 수정함
- store: ownerName 이름 수정 > ceoName