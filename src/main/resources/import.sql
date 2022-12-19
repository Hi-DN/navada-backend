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

INSERT INTO user(user_id,user_name,user_email,user_phone_num,user_address,user_level, user_nickname) VALUES (1,'kim','kim@naver.com','010-1111-2222','address','LV1_OUTSIDER' ,'투썸러버');
INSERT INTO user(user_id,user_name,user_email,user_phone_num,user_address,user_level, user_nickname) VALUES (2,'lee','lee@naver.com','010-1111-3333','address','LV2_RESIDENT', '나는스벅나는스벅나는');
INSERT INTO user(user_id,user_name,user_email,user_phone_num,user_address,user_level, user_nickname) VALUES (3,'park','park@naver.com','010-1111-3333','address','LV4_HEADMAN', 'seller');

INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (now(),1,1,'투썸아아 투썸아아 투썸아아 투썸아아 ','투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다ㅇㅇㅇㅇ',1,3, 4500, 500,'투썸러버');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (now(),2,1,'투썸 카페라떼','투썸 카페라떼입니다!!',1,3, 5000, 500,'투썸러버');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (now(),3,1,'투썸 바닐라라떼','투썸 바닐라라떼입니다 스벅으로 교환 원해요~!',1,3, 5500, 1000,'투썸러버');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (now(),4,2,'스타벅스아아이스아아메리카노오스타벅스아','스벅 아아입니다 시원한 스벅아아 드실분분분 스벅 아아입니다 시원한 스벅아아 드실분분분 스벅 아아입니다 시원한 스벅아아 드실분분분 스벅 아아입니다 시원한 스벅아아 드실분분분 구해여어',1,5, 4100, 1000,'나는스벅나는스벅나는');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (now(),5,2,'스타벅스 카페라떼','스타벅스 카페라떼입니다 투썸껄로 교환 원해요',1,5, 5100, 500,'나는스벅나는스벅나는');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (now(),6,2,'스타벅스 바닐라라떼','스타벅스 바닐라라떼 설명~~',1,0, 6000, 1000,'나는스벅나는스벅나는');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (now(),7,3,'할리스 아아','할리스 아이스 아메리카노 원가 4500',1,0, 4500, 1000,'seller');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (now(),8,3,'할리스 카페라떼','할리스 카페라떼 입니다',1,0, 5000, 1000,'seller');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname) VALUES (now(),9,3,'할리스 바닐라라떼','할리스 바닐라라떼입니다',1,0, 6000, 1000,'seller');


-- INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(1,2,1,4,1,0);
-- INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(2,2,1,4,2,0);
-- INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(3,2,1,4,3,0);
-- INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(4,3,1,7,2,0);
-- INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(5,3,1,8,2,0);
-- INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(6,2,3,6,9,1);
--
-- INSERT INTO exchange(exchange_id, requester_id, acceptor_id, requester_product_id, acceptor_product_id,acceptor_confirm_yn, requester_confirm_yn, exchange_complete_yn, acceptor_history_delete_yn, requester_history_delete_yn,acceptor_rating, requester_rating) VALUES (1,1,3,6,9,false,false,false,false,false,-1,-1);
-- INSERT INTO exchange(exchange_id, requester_id, acceptor_id, requester_product_id, acceptor_product_id,acceptor_confirm_yn, requester_confirm_yn, exchange_complete_yn, acceptor_history_delete_yn, requester_history_delete_yn,acceptor_rating, requester_rating) VALUES (2,2,1,6,9,false,false,false,false,false,-1,-1);
-- INSERT INTO exchange(exchange_id, requester_id, acceptor_id, requester_product_id, acceptor_product_id,acceptor_confirm_yn, requester_confirm_yn, exchange_complete_yn, acceptor_history_delete_yn, requester_history_delete_yn,acceptor_rating, requester_rating) VALUES (3,3,1,6,4,false,false,false,false,false,-1,-1);

INSERT INTO heart(user_id, product_id) VALUES (1,4);
INSERT INTO heart(user_id, product_id) VALUES (1,5);
INSERT INTO heart(user_id, product_id) VALUES (1,6);
INSERT INTO heart(user_id, product_id) VALUES (1,7);
INSERT INTO heart(user_id, product_id) VALUES (1,8);
INSERT INTO heart(user_id, product_id) VALUES (1,9);