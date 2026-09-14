package com.waimai.entity;

import java.math.BigDecimal;

/**
 * 订单明细实体类，对应数据库 order_item 表。
 * 一个订单里的一行菜。dish_name 和 price 是冗余存的，
 * 因为菜品价格以后会变，订单要留住「下单那一刻」的名字和价格。
 */
public class OrderItem {

    private Long id;
    private Long orderId;     // 属于哪个订单
    private Long dishId;      // 哪个菜
    private String dishName;  // 菜名（冗余）
    private BigDecimal price; // 下单时的单价（冗余）
    private Integer quantity; // 数量

    public OrderItem() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "OrderItem{id=" + id + ", orderId=" + orderId + ", dishName='" + dishName + "', quantity=" + quantity + "}";
    }
}
