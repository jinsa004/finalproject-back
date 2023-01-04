# 프로젝트 땡기는 민족
![image](https://user-images.githubusercontent.com/112357294/210491674-e24eb8a2-fb94-446a-8b7d-ed4abec8c128.png)

### 1. 프로젝트 개발이유
![image](https://user-images.githubusercontent.com/112357294/210491711-3bf78a2e-0568-4930-a097-d17334d0bf6f.png)

### 2. 사용언어 및 개발환경
- ![VS Code Insiders](https://img.shields.io/badge/VS%20Code%20Insiders-35b393.svg?style=for-the-badge&logo=visual-studio-code&logoColor=white) ![Android Studio](https://img.shields.io/badge/Android%20Studio-3DDC84.svg?style=for-the-badge&logo=android-studio&logoColor=white)
- ![Flutter](https://img.shields.io/badge/Flutter-%2302569B.svg?style=for-the-badge&logo=Flutter&logoColor=white) ![Dart](https://img.shields.io/badge/dart-%230175C2.svg?style=for-the-badge&logo=dart&logoColor=white) 
- ![Spring](https://img.shields.io/badge/spring-%236DB33F.svg?style=for-the-badge&logo=spring&logoColor=white) ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=for-the-badge&logo=java&logoColor=white)![Gradle](https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white) ![Apache Tomcat](https://img.shields.io/badge/apache%20tomcat-%23F8DC75.svg?style=for-the-badge&logo=apache-tomcat&logoColor=black) ![JWT](https://img.shields.io/badge/JWT-black?style=for-the-badge&logo=JSON%20web%20tokens) ![MariaDB](https://img.shields.io/badge/MariaDB-003545?style=for-the-badge&logo=mariadb&logoColor=white) 
- ![Git](https://img.shields.io/badge/git-%23F05033.svg?style=for-the-badge&logo=git&logoColor=white) ![GitHub](https://img.shields.io/badge/github-%23121011.svg?style=for-the-badge&logo=github&logoColor=white) ![Notion](https://img.shields.io/badge/Notion-%23000000.svg?style=for-the-badge&logo=notion&logoColor=white) 	![Discord](https://img.shields.io/badge/Discord-%235865F2.svg?style=for-the-badge&logo=discord&logoColor=white) ![Figma](https://img.shields.io/badge/figma-%23F24E1E.svg?style=for-the-badge&logo=figma&logoColor=white) ![AWS](https://img.shields.io/badge/AWS-%23FF9900.svg?style=for-the-badge&logo=amazon-aws&logoColor=white)
- H2 database / Junit / BCrypt Hash

### 3. 시연영상 및 PPT
https://www.notion.so/getinthere/PPT-0aec3737c253488aa1608726403bc093

### 4. 백엔드 아키텍쳐 Rest Api
![image](https://user-images.githubusercontent.com/112357294/210492988-08ffd128-3634-4e3a-a759-5234638c7de0.png)

# 프로젝트 테이블 더미데이터 생성

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

### Maria DB 더미데이터

/////////////////// 테이블 ///////////////////

DROP TABLE users;
DROP TABLE stores;
DROP TABLE menus;
DROP TABLE orders;
DROP TABLE order_details;
DROP TABLE ceo_reviews;
DROP TABLE customer_reviews;
DROP TABLE likes;
DROP TABLE report_reviews;
DROP TABLE payments;

CREATE TABLE users (
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   address VARCHAR(60),
   username VARCHAR(20),
   password VARCHAR(60),
   nickname VARCHAR(20),
   phone VARCHAR(20),
   photo LONGBLOB,
   role VARCHAR(20) DEFAULT 'CUSTOMER',
   is_active BOOLEAN,
   created_at timestamp,
   update_at timestamp
);

CREATE TABLE stores(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   category VARCHAR(20),
   name VARCHAR(30),
   phone VARCHAR(11),
   thumbnail LONGBLOB,
   ceo_name VARCHAR(20),
   business_number VARCHAR(10),
   business_address VARCHAR(60),
   open_time VARCHAR(10),
   close_time VARCHAR(10),
   min_amount INT,
   delivery_hour VARCHAR(4),
   delivery_cost INT,
   intro VARCHAR(100),
   notice VARCHAR(100),
   is_opend BOOLEAN,
   is_accept BOOLEAN,
   is_closure BOOLEAN,
   user_id BIGINT,
   created_at timestamp,
   update_at timestamp
);

CREATE TABLE menus(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   name VARCHAR(60),
   thumbnail LONGBLOB,
   intro VARCHAR(100),
   price INT,
   category VARCHAR(20),
   is_closure BOOLEAN,
   store_id BIGINT
);

CREATE TABLE orders(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   comment VARCHAR(80),
   state VARCHAR(20),
   reason VARCHAR(30),
   is_closure BOOLEAN,
   delivery_state_enum VARCHAR(20),
   user_id BIGINT,
   store_id BIGINT,
   payment_id BIGINT,
   complete_time VARCHAR(20),
   delivery_time VARCHAR(20),
   created_at timestamp,
   update_at timestamp
);

CREATE TABLE order_details(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   count INT,
   order_id BIGINT,
   menu_id BIGINT
);

CREATE TABLE ceo_reviews(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   content VARCHAR(100),
   store_id BIGINT,
   order_id BIGINT,
   created_at timestamp,
   update_at timestamp
);

CREATE TABLE customer_reviews(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   content VARCHAR(100),
   star_point DOUBLE,
   photo LONGBLOB,
   is_closure BOOLEAN,
   user_id BIGINT,
   store_id BIGINT,
   oreder_id BIGINT,
   ceo_review_id BIGINT,
   created_at timestamp,
   update_at timestamp
);

CREATE TABLE likes(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   user_id BIGINT,
   store_id BIGINT
);

CREATE TABLE report_reviews(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   user_id BIGINT,
   customer_review_id BIGINT,
   ceo_review_id BIGINT,
   reason VARCHAR(20),
   admin_comment VARCHAR(80),
   is_resolve BOOLEAN,
   is_accept BOOLEAN,
   resolved_time VARCHAR(20),
   created_at timestamp,
   update_at timestamp
);

CREATE TABLE payments(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   merchant_uid VARCHAR(40),
   imp_uid VARCHAR(40),
   amount int,
   nickname VARCHAR(40),
   menu_name VARCHAR(40),
   pay_method VARCHAR(40),
   is_canceled boolean,
   created_at timestamp,
   update_at timestamp
);

INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'ssar', '1234', 'ssar님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'cos', '1234', 'cos님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'hoho', '1234', 'hoho님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'haha', '1234', 'haha님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'koko', '1234', 'koko님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'kaka', '1234', 'kaka님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'hihi', '1234', 'hihi님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'kiki', '1234', 'kiki님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'papa', '1234', 'papa님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'popo', '1234', 'popo님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'pepe', '1234', 'pepe님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'pipi', '1234', 'pipi님', '01011112222', NULL, 'CEO', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'gimhae', '1234', 'gimhae님', '01011112222', NULL, 'CUSTOMER', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'busan', '1234', 'busan님', '01011112222', NULL, 'CUSTOMER', TRUE,now(),now());
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active, created_at, update_at)
VALUES('부산시 진구 서면 17번 길', 'admin', '1234', 'admin님', '01011112222', NULL, 'ADMIN', TRUE, now(), now());


INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id, created_at, update_at)
VALUES('CHICKEN', '네네치킨', '01011112222', 'assets/images/store_thumbnail/네네치킨.png', '네네치킨님', '112233', '부산시 진구 서면 17번 길', '2시', '4시', 10000, '30', 3000, '부산 네네치킨입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 1, now(), now());

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id, created_at, update_at)
VALUES('CHICKEN', '희치킨', '01011112222', 'assets/images/store_thumbnail/희치킨.png', '희치킨님', '112234', '부산시 진구 서면 18번 길', '2시', '4시', 10000, '30', 3000, '부산 희치킨입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 2, now(), now());

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id, created_at, update_at)
VALUES('PIZZA', '피자헛', '01011112222', 'assets/images/store_thumbnail/피자.jpg', '피자헛님', '112235', '부산시 진구 서면 19번 길', '2시', '4시', 10000, '30', 3000, '부산 피자헛입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 3, now(), now());

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id, created_at, update_at)
VALUES('BURGER','버거킹', '01011112222', 'assets/images/store_thumbnail/로고.png', '버거킹님', '112236', '부산시 진구 서면 20번 길', '2시', '4시', 10000, '30', 3000, '부산 버거킹입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 4, now(), now());

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id, created_at, update_at)
VALUES('SCHOOLFOOD', '조아분식', '01011112222', 'assets/images/store_thumbnail/분식.jpg', '조아분식님', '112237', '부산시 진구 서면 21번 길', '2시', '4시', 10000, '30', 3000, '부산 조아분식입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 5, now(), now());

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id, created_at, update_at)
VALUES('KRFOOD', '모던한식당', '01011112222', 'assets/images/store_thumbnail/한식.jpg', '모던한식당님', '112238', '부산시 진구 서면 22번 길', '2시', '4시', 10000, '30', 3000, '부산 모던한식당입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 6, now(), now());

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id, created_at, update_at)
VALUES('CNFOOD', '라이친', '01011112222', 'assets/images/store_thumbnail/중식.jpg', '라이친님', '112239', '부산시 진구 서면 23번 길', '2시', '4시', 10000, '30', 3000, '부산 라이친입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 7, now(), now());

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id, created_at, update_at)
VALUES('JPFOOD', '카츠오우', '01011112222', 'assets/images/store_thumbnail/일식.jpg', '카츠오우님', '112240', '부산시 진구 서면 24번 길', '2시', '4시', 10000, '30', 3000, '부산 카츠오우입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 8, now(), now());

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id, created_at, update_at)
VALUES('BOSSAM', '진짜보쌈', '01011112222', 'assets/images/store_thumbnail/보쌈.jpg', '진짜보쌈님', '112241', '부산시 진구 서면 25번 길', '2시', '4시', 10000, '30', 3000, '부산 진짜보쌈입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 9, now(), now());

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id, created_at, update_at)
VALUES('PORRIDGE', '본죽', '01011112222', 'assets/images/store_thumbnail/죽.jpg', '본죽님', '112242', '부산시 진구 서면 26번 길', '2시', '4시', 10000, '30', 3000, '부산 본죽입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 10, now(), now());

INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('레드마블치킨', 'assets/images/store_thumbnail/레드마블치킨.jpg', '알싸한 매운 양념에 마블소스를 듬뿍!!', 20000, 'MAIN', FALSE, 1);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('청양마요치킨', 'assets/images/store_thumbnail/청양마요치킨.jpg', '청양고추의 알싸함과 마요네즈소스!', 20000, 'MAIN', FALSE, 1);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('치즈스틱', 'assets/images/store_thumbnail/치즈스틱.jpg', '겉은 바삭 속은 치즈?~!', 2500, 'SIDE', FALSE, 1);

INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('후라이드치킨', 'assets/images/store_thumbnail/후라이드치킨.jpg', '바삭한 튀김 옷으로 승부한다!', 18000, 'MAIN', FALSE, 2);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('마늘순살샐러드', 'assets/images/store_thumbnail/마늘순살샐러드.jpg', '드레싱 소스가 듬뿍', 10000, 'SIDE', FALSE, 2);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('콜라', 'assets/images/store_thumbnail/콜라.jpg', '코카콜라', 2000, 'DRINK', FALSE, 2);

INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('돈마호크', 'assets/images/store_thumbnail/돈마호크.png', '돈마호크 고기를 사용한 피자', 25000, 'MAIN', FALSE, 3);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('치즈오븐스파게티', 'assets/images/store_thumbnail/치즈오븐스파게티.jpg', '180도 오븐에 5분간 스윽~', 5000, 'SIDE', FALSE, 3);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('콜라', 'assets/images/store_thumbnail/콜라.jpg', '코카콜라', 2000, 'DRINK', FALSE, 3);

INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('기네스와퍼', 'assets/images/store_thumbnail/기네스와퍼.png', '기네스 맥주를 이용한 번에 와퍼를?!', 10200, 'MAIN', FALSE, 4);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('치즈프라이', 'assets/images/store_thumbnail/치즈프라이.png', '감자튀김과 치즈의 만남', 2800, 'SIDE', FALSE, 4);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('콜라', 'assets/images/store_thumbnail/콜라.jpg', '코카콜라', 2000, 'DRINK', FALSE, 4);

INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('떡볶이', 'assets/images/store_thumbnail/떡볶이.jpg', '한국인은 먹을 수 밖에 없는 맛', 8000, 'MAIN', FALSE, 5);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('쥬시쿨', 'assets/images/store_thumbnail/쥬시쿨.jpg', '맛있는 자두맛 쥬시쿨', 1500, 'DRINK', FALSE, 5);

INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('비빔밥', 'assets/images/store_thumbnail/비빔밥.jpg', '비빔밥을 비벼봐요~', 6500, 'MAIN', FALSE, 6);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('해물파전', 'assets/images/store_thumbnail/해물파전.jpg', '각종 야채와 해물 만남의 전', 10000, 'SIDE', FALSE, 6);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('콜라', 'assets/images/store_thumbnail/콜라.jpg', '코카콜라', 2000, 'DRINK', FALSE, 6);

INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('짜장면', 'assets/images/store_thumbnail/짜장면.jpg', '달콤짭짤 춘장과 면의 만남', 5000, 'MAIN', FALSE, 7);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('짬뽕', 'assets/images/store_thumbnail/짬뽕.jpg', '시원한 해물육수 베이스의 짬뽕!!', 6000, 'MAIN', FALSE, 7);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('콜라', 'assets/images/store_thumbnail/콜라.jpg', '코카콜라', 2000, 'DRINK', FALSE, 7);

INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('돈까스', 'assets/images/store_thumbnail/돈까스.jpg', '깨끗한 기름으로 깔끔하게', 7000, 'MAIN', FALSE, 8);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('야끼만두', 'assets/images/store_thumbnail/야끼만두.jpg', '야끼만두는 인정이지~', 4000, 'SIDE', FALSE, 8);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('콜라', 'assets/images/store_thumbnail/콜라.jpg', '코카콜라', 2000, 'DRINK', FALSE, 8);

INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('보쌈', 'assets/images/store_thumbnail/보쌈.png', '보쌈을 쌈에 싸서 드셔보아요', 50000, 'MAIN', FALSE, 9);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('막국수', 'assets/images/store_thumbnail/막국수.jpg', '시원한 막국수와 보쌈을 함께!', 12000, 'SIDE', FALSE, 9);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('소주', 'assets/images/store_thumbnail/소주.jpg', '어른의 음료', 4000, 'DRINK', FALSE, 9);

INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('전복죽', 'assets/images/store_thumbnail/전복죽.jpg', '아픈 사람에게 영양만점 전복죽', 10500, 'MAIN', FALSE, 10);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('소고기장조림', 'assets/images/store_thumbnail/소고기장조림.jpg', '죽이 더 맛있어지는 비결', 4000, 'SIDE', FALSE, 10);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('콜라', 'assets/images/store_thumbnail/콜라.jpg', '코카콜라', 2000, 'DRINK', FALSE, 10);
INSERT INTO menus(name, thumbnail, intro, price, category, is_closure, store_id)
VALUES('콜라', 'assets/images/store_thumbnail/콜라.jpg', '코카콜라', 2000, 'DRINK', FALSE, 1);

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'ORDER', NULL, FALSE, 'DELIVERY', 14, 1, 1, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'TAKEOUT', 14, 1, 2, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 1, 3, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'TAKEOUT', 14, 2, 4, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 3, 5, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 4, 6, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 5, 7, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 6, 8, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 7, 9, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 8, 10, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 9, 11, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 10, 12, NULL, '30분', now(), now());

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time, created_at, update_at)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 10, 13, NULL, '30분', now(), now());

INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 1, 1);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 1, 3);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 2, 2);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 3, 1);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 4, 4);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 5, 7);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 5, 9);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 6, 10);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 7, 13);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 8, 15);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 9, 18);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 10, 21);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 11, 24);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 12, 27);
INSERT INTO order_details(count, order_id, menu_id) VALUES( 1, 13, 28);

INSERT INTO ceo_reviews(content, store_id, order_id, created_at, update_at) VALUES('고 마워 요', 1, 1, now(), now());
INSERT INTO ceo_reviews(content, store_id, order_id, created_at, update_at) VALUES('고 마워 요', 2, 4, now(), now());

INSERT INTO customer_reviews(content, star_point, photo, is_closure, user_id, store_id, oreder_id, ceo_review_id, created_at, update_at)
VALUES('맛있어요', 5.0, NULL, FALSE, 14, 1, 1, 1, now(), now());

INSERT INTO customer_reviews(content, star_point, photo, is_closure, user_id, store_id, oreder_id, ceo_review_id, created_at, update_at)
VALUES('맛있어요', 3.0, NULL, FALSE, 14, 1, 2, NULL, now(), now());

INSERT INTO customer_reviews(content, star_point, photo, is_closure, user_id, store_id, oreder_id, ceo_review_id, created_at, update_at)
VALUES('맛있어요', 4.0, NULL, FALSE, 14, 2, 4, 2, now(), now());

INSERT INTO customer_reviews(content, star_point, photo, is_closure, user_id, store_id, oreder_id, ceo_review_id, created_at, update_at)
VALUES('맛있어요', 3.0, NULL, FALSE, 14, 3, 5, NULL, now(), now());

INSERT INTO customer_reviews(content, star_point, photo, is_closure, user_id, store_id, oreder_id, ceo_review_id, created_at, update_at)
VALUES('맛있어요', 5.0, NULL, FALSE, 14, 4, 6, NULL, now(), now());

INSERT INTO likes(user_id, store_id) VALUES(14 ,1 );
INSERT INTO likes(user_id, store_id) VALUES(14 ,2 );
INSERT INTO likes(user_id, store_id) VALUES(14 ,4 );

INSERT INTO report_reviews(user_id, customer_review_id, ceo_review_id, reason, admin_comment, is_resolve, is_accept, resolved_time, created_at, update_at)
VALUES(1, 1, 1, 'HONOR', '명예훼손으로 인한 수용처리', FALSE, TRUE, null, now(), now());
INSERT INTO report_reviews(user_id, customer_review_id, ceo_review_id, reason, admin_comment, is_resolve, is_accept, resolved_time, created_at, update_at)
VALUES(14, 2, 2, 'HONOR', NULL, FALSE, FALSE, null, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), now());

INSERT INTO payments(merchant_uid, imp_uid, amount, nickname, menu_name, pay_method, is_canceled, created_at, update_at)
VALUES('asdf1234', 'zxcv1234', 10000, 'busan님', '레드마블치킨', '카카오페이', false, now(), NOW());
