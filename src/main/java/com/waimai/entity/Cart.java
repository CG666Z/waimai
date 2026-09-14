package com.waimai.entity;

/**
 * 购物车实体类，对应数据库 cart 表。
 * 购物车的一项 = 某个用户 + 某个菜品 + 数量。
 */
public class Cart {

    private Long id;
    private Long userId;   // 谁的购物车
    private Long dishId;   // 哪个菜
    private Integer quantity; // 数量

    public Cart() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getDishId() {
        return dishId;
    }

    public void setDishId(Long dishId) {
        this.dishId = dishId;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Cart{id=" + id + ", userId=" + userId + ", dishId=" + dishId + ", quantity=" + quantity + "}";
    }
}
