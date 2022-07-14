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

INSERT INTO user(user_id,user_name,user_email,user_password,user_phone_num,user_address,user_level, user_nickname) VALUES (1,'kim','kim@naver.com','kim','010-1111-2222','address','LV1_OUTSIDER' ,'mj');
INSERT INTO user(user_id,user_name,user_email,user_password,user_phone_num,user_address,user_level, user_nickname) VALUES (2,'lee','lee@naver.com','lee','010-1111-3333','address','LV1_OUTSIDER', 'kc');

INSERT INTO product(product_id,user_id,product_name,product_explanation,category_id,product_status_cd,heart_num, product_cost, exchange_cost_range) VALUES (1,1,'투썸아아','투썸아아입니다',1,0,3, 3000, 500);
INSERT INTO product(product_id,user_id,product_name,product_explanation,category_id,product_status_cd,heart_num, product_cost, exchange_cost_range) VALUES (2,2,'스벅아아','스벅아아입니다',2,0,5, 6000, 1000);

INSERT INTO heart(user_id, product_id) VALUES (1,1);
INSERT INTO heart(user_id, product_id) VALUES (1,2);