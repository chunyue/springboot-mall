CREATE SCHEMA IF NOT EXISTS mall;

CREATE TABLE IF NOT EXISTS mall.product(
    product_id         INT  NOT NULL PRIMARY KEY AUTO_INCREMENT,
    product_name       VARCHAR(128)  NOT NULL,
    category           VARCHAR(32)  NOT NULL,
    image_url          VARCHAR(256) NOT NULL,
    price              INT          NOT NULL,
    stock              INT          NOT NULL,
    description        VARCHAR(1024),
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
    );

CREATE TABLE IF NOT EXISTS mall.user(
    user_id            INT          NOT NULL PRIMARY KEY AUTO_INCREMENT,
    email              VARCHAR(256) NOT NULL UNIQUE,
    password           VARCHAR(256) NOT NULL,
    created_date       TIMESTAMP    NOT NULL,
    last_modified_date TIMESTAMP    NOT NULL
    );

CREATE TABLE IF NOT EXISTS  mall."order"(
    order_id           int       NOT NULL PRIMARY KEY AUTO_INCREMENT,
    user_id            int       not null,
    total_amount       int       not null,
    created_date       timestamp not null,
    last_modified_date timestamp not null
);

CREATE TABLE IF NOT EXISTS mall.order_item(
    order_item_id int NOT NULL PRIMARY KEY AUTO_INCREMENT,
    order_id      int not null,
    product_id    int not null,
    quantity      int not null,
    amount        int not null
);