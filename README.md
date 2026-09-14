# 外卖点餐系统（WaiMai）

一个基于 **Spring Boot + MySQL** 的外卖点餐系统，包含**控制台版**和 **REST 接口版**两种形态。项目用于学习 Java 后端开发、分层架构设计和接口测试。

## 技术栈

- Java 17
- Spring Boot 2.7
- JDBC + MySQL 8.0
- Maven

## 功能

- 用户注册 / 登录
- 浏览商家、查看菜品
- 购物车（同一菜品自动合并数量）
- 下单（生成订单 + 订单明细 + 扣减库存）
- 查询订单

## 项目结构（分层架构）

```
com.waimai
├── entity/      实体类（6 个）
├── dao/         数据访问层（JDBC）
├── service/     业务逻辑层（购物车合并、下单流程等）
├── controller/  REST 接口层
├── view/        控制台界面（控制台版入口）
├── util/        数据库连接工具类
├── Main.java            控制台版入口
└── WaimaiApplication    Spring Boot 启动类（接口版入口）
```

分层的好处：业务逻辑全部在 `service` 层，`view`（控制台）和 `controller`（REST 接口）只是两种不同的「外壳」，核心代码复用。

## 数据库

6 张表：`user`、`merchant`、`dish`、`cart`、`orders`、`order_item`。
建表脚本在 `db/schema.sql`。

## 运行方式

### 接口版（Spring Boot）

1. 在 MySQL 运行 `db/schema.sql` 建库建表
2. 修改 `DBUtil.java` 里的数据库密码
3. 运行 `WaimaiApplication.java`
4. 浏览器访问 `http://localhost:8080/merchants`

### 控制台版

运行 `Main.java`。

## 接口列表

| 功能 | 方式 | 网址 |
|------|------|------|
| 查商家 | GET | `/merchants` |
| 查菜品 | GET | `/merchants/{merchantId}/dishes` |
| 注册 | POST | `/users/register` |
| 登录 | POST | `/users/login` |
| 加购物车 | POST | `/cart` |
| 查购物车 | GET | `/cart/{userId}` |
| 下单 | POST | `/orders` |
| 查订单 | GET | `/orders/{userId}` |
