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
   is_active BOOLEAN
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
   user_id BIGINT
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
   delivery_time VARCHAR(20)
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
   order_id BIGINT
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
   ceo_review_id BIGINT
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
   resolved_time VARCHAR(20)
);

CREATE TABLE payments(
   id BIGINT PRIMARY KEY AUTO_INCREMENT,
   content VARCHAR(30)
);

INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'ssar', '1234', 'ssar님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'cos', '1234', 'cos님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'hoho', '1234', 'hoho님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'haha', '1234', 'haha님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'koko', '1234', 'koko님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'kaka', '1234', 'kaka님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'hihi', '1234', 'hihi님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'kiki', '1234', 'kiki님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'papa', '1234', 'papa님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'popo', '1234', 'popo님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'pepe', '1234', 'pepe님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'pipi', '1234', 'pipi님', '01011112222', NULL, 'CEO', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'gimhae', '1234', 'gimhae님', '01011112222', NULL, 'CUSTOMER', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'busan', '1234', 'busan님', '01011112222', NULL, 'CUSTOMER', TRUE);
INSERT INTO users(address, username, password, nickname, phone, photo, role, is_active)
VALUES('부산시 진구 서면 17번 길', 'admin', '1234', 'admin님', '01011112222', NULL, 'ADMIN', TRUE);


INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id)
VALUES('CHICKEN', '네네치킨', '01011112222', 'assets/images/store_thumbnail/네네치킨.png', '네네치킨님', '112233', '부산시 진구 서면 17번 길', '2시', '4시', 10000, '30', 3000, '부산 네네치킨입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 1);

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id)
VALUES('CHICKEN', '희치킨', '01011112222', 'assets/images/store_thumbnail/희치킨.png', '희치킨님', '112234', '부산시 진구 서면 18번 길', '2시', '4시', 10000, '30', 3000, '부산 희치킨입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 2);

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id)
VALUES('PIZZA', '피자헛', '01011112222', 'assets/images/store_thumbnail/피자.jpg', '피자헛님', '112235', '부산시 진구 서면 19번 길', '2시', '4시', 10000, '30', 3000, '부산 피자헛입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 3);

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id)
VALUES('BURGER','버거킹', '01011112222', 'assets/images/store_thumbnail/로고.png', '버거킹님', '112236', '부산시 진구 서면 20번 길', '2시', '4시', 10000, '30', 3000, '부산 버거킹입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 4);

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id)
VALUES('SCHOOLFOOD', '조아분식', '01011112222', 'assets/images/store_thumbnail/분식.jpg', '조아분식님', '112237', '부산시 진구 서면 21번 길', '2시', '4시', 10000, '30', 3000, '부산 조아분식입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 5);

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id)
VALUES('KRFOOD', '모던한식당', '01011112222', 'assets/images/store_thumbnail/한식.jpg', '모던한식당님', '112238', '부산시 진구 서면 22번 길', '2시', '4시', 10000, '30', 3000, '부산 모던한식당입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 6);

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id)
VALUES('CNFOOD', '라이친', '01011112222', 'assets/images/store_thumbnail/중식.jpg', '라이친님', '112239', '부산시 진구 서면 23번 길', '2시', '4시', 10000, '30', 3000, '부산 라이친입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 7);

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id)
VALUES('JPFOOD', '카츠오우', '01011112222', 'assets/images/store_thumbnail/일식.jpg', '카츠오우님', '112240', '부산시 진구 서면 24번 길', '2시', '4시', 10000, '30', 3000, '부산 카츠오우입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 8);

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id)
VALUES('BOSSAM', '진짜보쌈', '01011112222', 'assets/images/store_thumbnail/보쌈.jpg', '진짜보쌈님', '112241', '부산시 진구 서면 25번 길', '2시', '4시', 10000, '30', 3000, '부산 진짜보쌈입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 9);

INSERT INTO stores(category, name, phone, thumbnail, ceo_name, business_number, business_address, open_time, close_time, min_amount, delivery_hour, delivery_cost, intro, notice, is_opend, is_accept, is_closure, user_id)
VALUES('PORRIDGE', '본죽', '01011112222', 'assets/images/store_thumbnail/죽.jpg', '본죽님', '112242', '부산시 진구 서면 26번 길', '2시', '4시', 10000, '30', 3000, '부산 본죽입니다.', '리뷰 이벤트중입니다.', TRUE, TRUE, FALSE, 10);

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

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'ORDER', NULL, FALSE, 'DELIVERY', 14, 1, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'TAKEOUT', 14, 1, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 1, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'TAKEOUT', 14, 2, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 3, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 4, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 5, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 6, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 7, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 8, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 9, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 10, NULL, NULL, '30분');

INSERT INTO orders(comment, state, reason, is_closure, delivery_state_enum, user_id, store_id, payment_id, complete_time, delivery_time)
VALUES('젓가락 빼주세요', 'COMPLETE', NULL, FALSE, 'DELIVERY', 14, 10, NULL, NULL, '30분');

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

INSERT INTO ceo_reviews(content, store_id, order_id) VALUES('고 마워 요', 1, 1);
INSERT INTO ceo_reviews(content, store_id, order_id) VALUES('고 마워 요', 2, 4);

INSERT INTO customer_reviews(content, star_point, photo, is_closure, user_id, store_id, oreder_id, ceo_review_id)
VALUES('맛있어요', 5.0, NULL, FALSE, 14, 1, 1, 1);

INSERT INTO customer_reviews(content, star_point, photo, is_closure, user_id, store_id, oreder_id, ceo_review_id)
VALUES('맛있어요', 3.0, NULL, FALSE, 14, 1, 2, NULL);

INSERT INTO customer_reviews(content, star_point, photo, is_closure, user_id, store_id, oreder_id, ceo_review_id)
VALUES('맛있어요', 4.0, NULL, FALSE, 14, 2, 4, 2);

INSERT INTO customer_reviews(content, star_point, photo, is_closure, user_id, store_id, oreder_id, ceo_review_id)
VALUES('맛있어요', 3.0, NULL, FALSE, 14, 3, 5, NULL);

INSERT INTO customer_reviews(content, star_point, photo, is_closure, user_id, store_id, oreder_id, ceo_review_id)
VALUES('맛있어요', 5.0, NULL, FALSE, 14, 4, 6, NULL);

INSERT INTO likes(user_id, store_id) VALUES(14 ,1 );
INSERT INTO likes(user_id, store_id) VALUES(14 ,2 );
INSERT INTO likes(user_id, store_id) VALUES(14 ,4 );

INSERT INTO report_reviews(user_id, customer_review_id, ceo_review_id, reason, admin_comment, is_resolve, is_accept, resolved_time)
VALUES(1, 1, 1, 'HONOR', '명예훼손으로 인한 수용처리', FALSE, TRUE, 2022-12-19 17:17:17.258301);
INSERT INTO report_reviews(user_id, customer_review_id, ceo_review_id, reason, admin_comment, is_resolve, is_accept, resolved_time)
VALUES(14, 2, 2, 'HONOR', NULL, FALSE, FALSE, 2022-12-19 17:17:17.270307);
