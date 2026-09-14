-- =====================================================
-- 外卖点餐系统 数据库初始化脚本
-- 作用：建库、建 6 张表、插入测试数据
-- 用法：在 MySQL 里运行一次即可；想清空重来，重新运行一次即可
-- =====================================================

CREATE DATABASE IF NOT EXISTS waimai DEFAULT CHARACTER SET utf8mb4;
USE waimai;

-- 先删后建（保证脚本可重复执行）
DROP TABLE IF EXISTS order_item;
DROP TABLE IF EXISTS orders;
DROP TABLE IF EXISTS cart;
DROP TABLE IF EXISTS dish;
DROP TABLE IF EXISTS merchant;
DROP TABLE IF EXISTS user;

-- 1. 用户表
CREATE TABLE user (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50)  NOT NULL UNIQUE,
    password VARCHAR(100) NOT NULL,
    phone    VARCHAR(20),
    address  VARCHAR(200)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 2. 商家表
CREATE TABLE merchant (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    name     VARCHAR(100) NOT NULL,
    phone    VARCHAR(20),
    address  VARCHAR(200),
    category VARCHAR(50)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 3. 菜品表（属于某个商家）
CREATE TABLE dish (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    merchant_id BIGINT         NOT NULL,
    name        VARCHAR(100)   NOT NULL,
    price       DECIMAL(10, 2) NOT NULL,
    stock       INT            NOT NULL DEFAULT 0,
    status      TINYINT        NOT NULL DEFAULT 1 COMMENT '1=在售 0=下架',
    FOREIGN KEY (merchant_id) REFERENCES merchant (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 4. 购物车表
CREATE TABLE cart (
    id       BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id  BIGINT NOT NULL,
    dish_id  BIGINT NOT NULL,
    quantity INT    NOT NULL DEFAULT 1,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (dish_id) REFERENCES dish (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 5. 订单表
CREATE TABLE orders (
    id          BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id     BIGINT         NOT NULL,
    merchant_id BIGINT         NOT NULL,
    total_price DECIMAL(10, 2) NOT NULL,
    status      VARCHAR(20)    NOT NULL DEFAULT '待支付',
    create_time DATETIME       NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user (id),
    FOREIGN KEY (merchant_id) REFERENCES merchant (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- 6. 订单明细表（一个订单含多个菜品，冗余存菜名和价格）
CREATE TABLE order_item (
    id        BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id  BIGINT         NOT NULL,
    dish_id   BIGINT         NOT NULL,
    dish_name VARCHAR(100)   NOT NULL,
    price     DECIMAL(10, 2) NOT NULL,
    quantity  INT            NOT NULL,
    FOREIGN KEY (order_id) REFERENCES orders (id),
    FOREIGN KEY (dish_id) REFERENCES dish (id)
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4;

-- ===== 测试数据 =====

INSERT INTO merchant (name, phone, address, category) VALUES
('张记黄焖鸡', '13800000001', '南山区科技园', '快餐'),
('老王烧烤', '13800000002', '福田区华强北', '烧烤'),
('李记拉面', '13800000003', '罗湖区东门', '面食');

INSERT INTO dish (merchant_id, name, price, stock) VALUES
(1, '黄焖鸡米饭', 22.00, 100),
(1, '黄焖排骨饭', 25.00, 80),
(2, '羊肉串', 3.00, 500),
(2, '烤茄子', 8.00, 50),
(3, '牛肉拉面', 18.00, 100),
(3, '凉拌牛肉', 28.00, 40);
