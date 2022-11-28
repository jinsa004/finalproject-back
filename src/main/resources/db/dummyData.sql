 create table users (
    id int primary KEY AUTO_INCREMENT,
    deliveryAddressId int NOT null,
    username VARCHAR(24) NOT NULL,
    password VARCHAR(80) NOT null,
    email VARCHAR(40) not null,
    nickname VARCHAR(24) NOT null,
    phone VARCHAR(24) NOT null,
    photo VARCHAR(256),
    role VARCHAR(24),
    createdAt datetime
);

  create table delivery_address (
    id int primary KEY AUTO_INCREMENT,
    userId int NOT null,
    address VARCHAR(80),
    createdAt datetime
);
 
   create table stores (
    id int primary KEY AUTO_INCREMENT,
    userId int NOT null,
    name VARCHAR(24),
    phone VARCHAR(24),
    ceoName VARCHAR(24) not null,
    businessNumber VARCHAR(24) NOT null,
    businessAddress VARCHAR(80) NOT null,
    openTime VARCHAR(24),
    closeTime VARCHAR(24),
    minAmount VARCHAR(24),
    deliveryHour VARCHAR(24),
    deliveryCost VARCHAR(24),
    intro VARCHAR(160),
    notice VARCHAR(160),
    isOpend boolean,
    isAccept boolean,
    createdAt datetime
);

  create table menus (
    id int primary KEY AUTO_INCREMENT,
    storeId int NOT null,
    name VARCHAR(40) not null,
    thumbnail VARCHAR(256),
    intro VARCHAR(40) not null,
    price VARCHAR(24) NOT null,
    category VARCHAR(24) NOT null,
    isClosure boolean,
    createdAt datetime
);

  create table orders (
    id int primary KEY AUTO_INCREMENT,
    userId int NOT null,
    storeId int NOT null,
    paymentId int NOT null,
    comment VARCHAR(80),
    state VARCHAR(20),
    reason VARCHAR(80),
    isClosure boolean,
    createdAt datetime
);

  create table order_details (
    id int primary KEY AUTO_INCREMENT,
    orderId int NOT null,
    menuId int NOT null,
    count int not null,
    createdAt datetime
);

  create table customer_reviews (
    id int primary KEY AUTO_INCREMENT,
    userId int NOT null,
    orderId int NOT null,
    content VARCHAR(160),
    starPoint int not null,
    photo VARCHAR(256),
    isClosure boolean,
    createdAt datetime
);

  create table ceo_reviews (
    id int primary KEY AUTO_INCREMENT,
    reviewId int NOT null,
    userId int NOT null,
    content VARCHAR(160),
    createdAt datetime
);

  create table report_reviews (
    id int primary KEY AUTO_INCREMENT,
    userId int NOT null,
    reviewId int NOT null,
    reason VARCHAR(160),
    adminComment VARCHAR(80),
    resolvedTime timestamp,
    createdAt datetime
);

  create table likes (
    id int primary KEY AUTO_INCREMENT,
    userId int NOT null,
    storeId int NOT NULL
);

  create table payments (
    content VARCHAR(24),
    createdAt datetime
);

insert into users(deliveryAddressId, username, password, email, nickname, phone, photo, role, createdAt) 
VALUES(1,'ssar','1234', 'ssar@nate.com', 'ssar','01011112222', null, 'CUSTOMER', now());
insert into users(deliveryAddressId, username, password, email, nickname, phone, photo, role, createdAt) 
VALUES(2,'cos','1234','cos@nate.com', 'cos','01011112222', null, 'CUSTOMER', now());
insert into users(deliveryAddressId, username, password, email, nickname, phone, photo, role, createdAt) 
VALUES(3,'jinsa','1234', 'jinsa@nate.com','jinsa','01011112222', null, 'CUSTOMER', now());

insert into delivery_address(userId, address, createdAt)
VALUES(1,'부산시 진구 서면로 20번 길', now());
insert into delivery_address(userId, address, createdAt)
VALUES(2,'부산시 진구 서면로 48번 길', now());
insert into delivery_address(userId, address, createdAt)
VALUES(3,'부산시 진구 서면로 96번 길', now());

insert into stores(userId, name, phone, ceoName, businessNumber, businessAddress, openTime, closeTime, minAmount, deliveryHour, deliveryCost, intro, notice, isOpend, isAccept, createdAt)
VALUES(1, '그린 치킨', '01011112222', 'ssar', '1234512345', '부산광역시 부산 진구 서면로 17번지', '10:00', '11:00', '12,000원', '40분', '4,000원', '안녕하세요 그린치킨입니다.', '리뷰이벤트 실시중입니다', true, true, now());
insert into stores(userId, name, phone, ceoName, businessNumber, businessAddress, openTime, closeTime, minAmount, deliveryHour, deliveryCost, intro, notice, isOpend, isAccept, createdAt)
VALUES(2, '레드 밀면', '01022223333', 'cos', '123456789', '부산광역시 부산 진구 서면로 27번지', '10:30', '20:00', '10,000원', '20분', '3,000원', '안녕하세요 레드밀면입니다.', '추가 식초, 겨자드려요', true, true, now());
insert into stores(userId, name, phone, ceoName, businessNumber, businessAddress, openTime, closeTime, minAmount, deliveryHour, deliveryCost, intro, notice, isOpend, isAccept, createdAt)
VALUES(1, '블루 카페', '01033332222', 'jinsa', '987654321', '부산광역시 부산 진구 서면로 37번지', '11:00', '16:00', '8,000원', '15분', '2,000원', '안녕하세요 블루카페입니다.', '리뷰이벤트 참여시 허니브레드를 드려요', true, true, now());

insert into menus(storeId, name, thumbnail, intro, price, category, isClosure, createdAt)
VALUES(1, '후라이드 치킨', null, '황금올리브유로 튀긴 치킨', '18,000원', '치킨', false, now());
insert into menus(storeId, name, thumbnail, intro, price, category, isClosure, createdAt)
VALUES(2, '비빔 밀면', null, '10가지 과일 및 한방재료가 들어간 소스밀면', '6,000원', '분식', false, now());
insert into menus(storeId, name, thumbnail, intro, price, category, isClosure, createdAt)
VALUES(3, '페퍼로니 피자', null, '페퍼로니가 꽉 찬 피자', '14,000원', '피자', false, now());

insert into orders(userId, storeId, paymentId, comment, state, reason, isClosure, createdAt)
VALUES(1,1,1,'치킨 무 안주셔도 됩니다','주문완료',null,false, now());
insert into orders(userId, storeId, paymentId, comment, state, reason, isClosure, createdAt)
VALUES(2,2,1,'밀면 김치 많이 부탁드려요','주문취소','밀면 재료소진',false, now());
insert into orders(userId, storeId, paymentId, comment, state, reason, isClosure, createdAt)
VALUES(3,3,2,'갈릭딥핑소스 부탁드려요','배달중',null,false, now());

insert into order_details(orderId, menuId, count, createdAt)
VALUES(1, 1, 1, now());
insert into order_details(orderId, menuId, count, createdAt)
VALUES(2, 3, 2, now());

insert into customer_reviews(userId, orderId, content, starPoint, photo, isClosure, createdAt)
VALUES(1, 1, '치킨이 겉은 바삭 속은 촉촉한게 너무 맛있었어요', 4, null, false, now());
insert into customer_reviews(userId, orderId, content, starPoint, photo, isClosure, createdAt)
VALUES(3, 2, '미친 이딴 피자도 피자라고 팔고 다니나요? 진짜 맛대가리 없네요.', 1, null, false, now());

insert into ceo_reviews(reviewId, userId, content, createdAt)
VALUES(1,1,'이용해주셔서 감사합니다 늘 깨끗한 그린치킨이 되도록 노력하겠습니다.', now());
insert into ceo_reviews(reviewId, userId, content, createdAt)
VALUES(2,3,'갈릭딥핑소스는 만족스러우셨나요? 맛있게 드셨다면 다음에도 주문해주세요!', now());

insert into report_reviews(userId, reviewId, reason, adminComment, resolvedTime, createdAt)
VALUES(3, 2, '욕설', '욕설로 인한 리뷰차단 처리완료', '2022-11-28 15:34', now());

insert into likes(userId, storeId)
VALUES(1,1);
insert into likes(userId, storeId)
VALUES(1,3);
insert into likes(userId, storeId)
VALUES(2,2);
insert into likes(userId, storeId)
VALUES(2,3);

insert into payments(content, createdAt)
VALUES('카카오페이', now());
insert into payments(content, createdAt)
VALUES('네이버페이', now());
insert into payments(content, createdAt)
VALUES('만나서 현금결제', now());