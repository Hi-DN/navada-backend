INSERT INTO category(category_name) VALUES ('기프티콘');
INSERT INTO category(category_name) VALUES ('전자기기');
INSERT INTO category(category_name) VALUES ('가구');
INSERT INTO category(category_name) VALUES ('유아용품');
INSERT INTO category(category_name) VALUES ('스포츠');
INSERT INTO category(category_name) VALUES ('식품');
INSERT INTO category(category_name) VALUES ('취미용품');
INSERT INTO category(category_name) VALUES ('미용');
INSERT INTO category(category_name) VALUES ('여성의류');
INSERT INTO category(category_name) VALUES ('남성의류');
INSERT INTO category(category_name) VALUES ('반려동물용품');
INSERT INTO category(category_name) VALUES ('도서');
INSERT INTO category(category_name) VALUES ('장난감');
INSERT INTO category(category_name) VALUES ('식물');
INSERT INTO category(category_name) VALUES ('기타용품');

INSERT INTO user(user_id,user_name,user_email,user_password,user_phone_num,user_address,user_level, user_nickname) VALUES (1,'kim','kim@naver.com','kim','010-1111-2222','address','LV1_OUTSIDER' ,'user1');
INSERT INTO user(user_id,user_name,user_email,user_password,user_phone_num,user_address,user_level, user_nickname) VALUES (2,'lee','lee@naver.com','lee','010-1111-3333','address','LV1_OUTSIDER', 'user2');
INSERT INTO user(user_id,user_name,user_email,user_password,user_phone_num,user_address,user_level, user_nickname) VALUES (3,'park','park@naver.com','park','010-1111-3333','address','LV1_OUTSIDER', 'user3');

INSERT INTO product(product_id,user_id,product_name,product_explanation,category_id,product_status_cd,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (1,1,'투썸 아아','투썸 아아입니다',1,1,3, 3000, 500,'user1');
INSERT INTO product(product_id,user_id,product_name,product_explanation,category_id,product_status_cd,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (2,2,'스벅 아아','스벅 아아입니다',2,1,5, 6000, 1000,'user2');
INSERT INTO product(product_id,user_id,product_name,product_explanation,category_id,product_status_cd,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (3,3,'할리스 아아','할리스',2,0,5, 6000, 1000,'user3');
INSERT INTO product(product_id,user_id,product_name,product_explanation,category_id,product_status_cd,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (4,1,'투썸 카페라떼','투섬 카페라떼입니다',2,1,0, 6000, 1000,'user1');
INSERT INTO product(product_id,user_id,product_name,product_explanation,category_id,product_status_cd,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (5,2,'스벅 카페라떼','할리스',2,0,0, 6000, 1000,'user2');
INSERT INTO product(product_id,user_id,product_name,product_explanation,category_id,product_status_cd,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (6,3,'할리스 카페라떼','할리스 카페라떼',2,1,0, 6000, 1000,'user3');

-- 유저 1 -> 2 요청 (상품 1 -> 2) : 수락
INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(1,1,2,1,2,1);
-- 유저 1 -> 3 요청 (상품 1 -> 3)
INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(2,1,3,1,3,0);
-- 유저 2 -> 3 요청 (상품 2 -> 3)
INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(3,2,3,2,3,0);
-- 유저 2 -> 1 요청 (상품 5 -> 4)
INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(4,2,1,5,4,0);
-- 유저 3 -> 1 요청 (상품 6 -> 4) : 수락
INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(5,3,1,6,4,1);

INSERT INTO exchange(exchange_id, requester_id, acceptor_id, requester_product_id, acceptor_product_id,acceptor_confirm_yn, requester_confirm_yn, exchange_complete_yn, acceptor_history_delete_yn, requester_history_delete_yn,acceptor_rating, requester_rating) VALUES (1,1,2,1,2,false,false,false,false,false,-1,-1);
INSERT INTO exchange(exchange_id, requester_id, acceptor_id, requester_product_id, acceptor_product_id,acceptor_confirm_yn, requester_confirm_yn, exchange_complete_yn, acceptor_history_delete_yn, requester_history_delete_yn,acceptor_rating, requester_rating) VALUES (2,3,1,6,4,false,false,false,false,false,-1,-1);

INSERT INTO heart(user_id, product_id) VALUES (1,2);
INSERT INTO heart(user_id, product_id) VALUES (1,3);
INSERT INTO heart(user_id, product_id) VALUES (1,5);
INSERT INTO heart(user_id, product_id) VALUES (1,6);
INSERT INTO heart(user_id, product_id) VALUES (2,1);
INSERT INTO heart(user_id, product_id) VALUES (2,3);