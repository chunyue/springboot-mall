-- product
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('蘋果（澳洲）', 'FOOD', 'https://cdn.pixabay.com/photo/2016/11/30/15/00/apples-1872997_1280.jpg', 30, 10, '這是來自澳洲的蘋果！', '2022-03-19 17:00:00', '2022-03-22 18:00:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('蘋果（日本北海道）', 'FOOD', 'https://cdn.pixabay.com/photo/2017/09/26/13/42/apple-2788662_1280.jpg', 300, 5, '這是來自日本北海道的蘋果！', '2022-03-19 18:30:00', '2022-03-19 18:30:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('好吃又鮮甜的蘋果橘子', 'FOOD', 'https://cdn.pixabay.com/photo/2021/07/30/04/17/orange-6508617_1280.jpg', 10, 50, null, '2022-03-20 09:00:00', '2022-03-24 15:00:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Toyota', 'CAR', 'https://cdn.pixabay.com/photo/2014/05/18/19/13/toyota-347288_1280.jpg', 100000, 5, null, '2022-03-20 09:20:00', '2022-03-20 09:20:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('BMW', 'CAR', 'https://cdn.pixabay.com/photo/2018/02/21/03/15/bmw-m4-3169357_1280.jpg', 500000, 3, '渦輪增壓，直列4缸，DOHC雙凸輪軸，16氣門', '2022-03-20 12:30:00', '2022-03-20 12:30:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Benz', 'CAR', 'https://cdn.pixabay.com/photo/2017/03/27/14/56/auto-2179220_1280.jpg', 600000, 2, null, '2022-03-21 20:10:00', '2022-03-22 10:50:00');
INSERT INTO product (product_name, category, image_url, price, stock, description, created_date, last_modified_date) VALUES ('Tesla', 'CAR', 'https://cdn.pixabay.com/photo/2021/01/15/16/49/tesla-5919764_1280.jpg', 450000, 5, '世界最暢銷的充電式汽車', '2022-03-21 23:30:00', '2022-03-21 23:30:00');



insert into user (user_id, email, password, created_date, last_modified_date) values (1, 'test3@gmail.com', '123', '2023-08-05 00:06:15', '2023-08-05 00:06:15');
insert into user (user_id, email, password, created_date, last_modified_date) values (2, 'test4@gmail.com', '123', '2023-08-06 23:16:02', '2023-08-06 23:16:02');
insert into user (user_id, email, password, created_date, last_modified_date) values (3, 'test1@gmail.com', '202cb962ac59075b964b07152d234b70', '2023-08-13 23:38:38', '2023-08-13 23:38:38');
insert into user (user_id, email, password, created_date, last_modified_date) values (4, '222@gmail.com', 'ccf5538dc31d435d6bab145c924041d8', '2023-09-24 16:00:42', '2023-09-24 16:00:42');

insert into `order` (order_id, user_id, total_amount, created_date, last_modified_date) values (1, 1, 100110, '2022-06-02 16:51:49', '2022-06-02 16:51:49');
