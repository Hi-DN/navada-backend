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
INSERT INTO category(category_name) VALUES ('반려동물');
INSERT INTO category(category_name) VALUES ('도서');
INSERT INTO category(category_name) VALUES ('장난감');
INSERT INTO category(category_name) VALUES ('식물');
INSERT INTO category(category_name) VALUES ('기타');

INSERT INTO faq(faq_id, faq_question, faq_answer) VALUES(1,'레벨은 어떻게 책정되나요?','레벨의 경우 매달 마지막날 책정됩니다. 자세한 책정방식 정하고 여기에 추가 필요!')
INSERT INTO faq(faq_id, faq_question, faq_answer) VALUES(2,'교환 완료 버튼을 눌렀는데 계속 교환중이라고 떠요.','교환 신청자와 수락자 모두가 교환 완료 확인을 한 경우에만 교환 완료로 처리됩니다.\n\n따라서 상대가 교환 완료를 하고 나면 정상적으로 교환완료로 보여지게 됩니다.')
INSERT INTO faq(faq_id, faq_question, faq_answer) VALUES(3,'교환을 이미 수락했는데 다시 취소하고 싶어요.','상대방과 본인 모두 교환완료 처리를 하지 않았다면, 교환 취소가 가능합니다.\n\n교환 상세 페이지 하단에서 교환 취소가 가능하며, 자동으로 상대방에게 취소 알람이 갑니다.')

INSERT INTO user(user_id,user_name,user_email,user_password,user_phone_num,user_address,user_level, user_nickname) VALUES (1,'kim','kim@naver.com','kim','010-1111-2222','address','LV1_OUTSIDER' ,'투썸러버');
INSERT INTO user(user_id,user_name,user_email,user_password,user_phone_num,user_address,user_level, user_nickname) VALUES (2,'lee','lee@naver.com','lee','010-1111-3333','address','LV2_RESIDENT', '나는스벅나는스벅나는');
INSERT INTO user(user_id,user_name,user_email,user_password,user_phone_num,user_address,user_level, user_nickname) VALUES (3,'park','park@naver.com','park','010-1111-3333','address','LV4_HEADMAN', 'seller');

INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname,product_exchange_status_cd) VALUES (now(),1,1,'투썸아아 투썸아아 투썸아아 투썸아아 ','투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다투썸 아아입니다ㅇㅇㅇㅇ',1,0, 4500, 500,'투썸러버','1');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname,product_exchange_status_cd) VALUES (now(),2,1,'투썸 카페라떼','투썸 카페라떼입니다!!',1,0, 5000, 500,'투썸러버','0');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname,product_exchange_status_cd) VALUES (now(),3,1,'투썸 바닐라라떼','투썸 바닐라라떼입니다 스벅으로 교환 원해요~!',1,0, 5500, 1000,'투썸러버','0');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname,product_exchange_status_cd) VALUES (now(),4,2,'스타벅스 아이스 아메리카노 스타벅스아','스벅 아아입니다 시원한 스벅아아 드실분분분 스벅 아아입니다 시원한 스벅아아 드실분분분 스벅 아아입니다 시원한 스벅아아 드실분분분 스벅 아아입니다 시원한 스벅아아 드실분분분 구해여어',1,1, 4100, 1000,'나는스벅나는스벅나는','1');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname,product_exchange_status_cd) VALUES (now(),5,2,'스타벅스 카페라떼','스타벅스 카페라떼입니다 투썸껄로 교환 원해요',1,1, 5100, 500,'나는스벅나는스벅나는','0');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname,product_exchange_status_cd) VALUES (now(),6,2,'스타벅스 바닐라라떼','스타벅스 바닐라라떼 설명~~',1,1, 6000, 1000,'나는스벅나는스벅나는','0');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname,product_exchange_status_cd) VALUES (now(),7,3,'할리스 아아','할리스 아이스 아메리카노 원가 4500',1,1, 4500, 1000,'seller','0');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname,product_exchange_status_cd) VALUES (now(),8,3,'할리스 카페라떼','할리스 카페라떼 입니다',1,1, 5000, 1000,'seller','0');
INSERT INTO product(created_date,product_id,user_id,product_name,product_explanation,category_id,heart_num, product_cost, exchange_cost_range,user_nickname,product_exchange_status_cd) VALUES (now(),9,3,'할리스 바닐라라떼','할리스 바닐라라떼입니다',1,1, 6000, 1000,'seller','0');


INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, request_status_cd) VALUES(1,2,1,4,1,'1');
INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, request_status_cd) VALUES(2,2,1,5,2,'0');
-- INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(3,2,1,4,3,0);
INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, request_status_cd) VALUES(4,3,1,7,3,'0');
-- INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(5,3,1,8,2,0);
-- INSERT INTO request(request_id,requester_id, acceptor_id, requester_product_id,acceptor_product_id, exchange_status_cd) VALUES(6,2,3,6,9,1);

INSERT INTO exchange(exchange_id, requester_id, acceptor_id, requester_product_id, acceptor_product_id,acceptor_confirm_yn, requester_confirm_yn,  acceptor_history_delete_yn, requester_history_delete_yn,acceptor_rating, requester_rating) VALUES (1,2,1,4,1,false,false,false,false,-1,-1);
-- INSERT INTO exchange(exchange_id, requester_id, acceptor_id, requester_product_id, acceptor_product_id,acceptor_confirm_yn, requester_confirm_yn, exchange_complete_yn, acceptor_history_delete_yn, requester_history_delete_yn,acceptor_rating, requester_rating) VALUES (2,2,1,6,9,false,false,false,false,false,-1,-1);
-- INSERT INTO exchange(exchange_id, requester_id, acceptor_id, requester_product_id, acceptor_product_id,acceptor_confirm_yn, requester_confirm_yn, exchange_complete_yn, acceptor_history_delete_yn, requester_history_delete_yn,acceptor_rating, requester_rating) VALUES (3,3,1,6,4,false,false,false,false,false,-1,-1);

INSERT INTO heart(user_id, product_id) VALUES (1,4);
INSERT INTO heart(user_id, product_id) VALUES (1,5);
INSERT INTO heart(user_id, product_id) VALUES (1,6);
INSERT INTO heart(user_id, product_id) VALUES (1,7);
INSERT INTO heart(user_id, product_id) VALUES (1,8);
INSERT INTO heart(user_id, product_id) VALUES (1,9);