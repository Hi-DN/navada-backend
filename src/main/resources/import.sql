INSERT INTO user(user_id,user_name,user_email,user_password,user_phone_num,user_address,user_level, user_nickname) VALUES (1,'kim','kim@naver.com','kim','010-1111-2222','address',0 ,'mj');
INSERT INTO user(user_id,user_name,user_email,user_password,user_phone_num,user_address,user_level, user_nickname) VALUES (2,'lee','lee@naver.com','lee','010-1111-3333','address',1, 'kc');

INSERT INTO product(product_id,user_id,product_name,product_explanation,category,product_status_cd,heart_num, product_cost, exchange_cost_range) VALUES (1,1,'투썸아아','투썸아아입니다','기프티콘','0','3', 3000, 500);
INSERT INTO product(product_id,user_id,product_name,product_explanation,category,product_status_cd,heart_num, product_cost, exchange_cost_range) VALUES (2,2,'스벅아아','스벅아아입니다','기프티콘','0','5', 6000, 1000);