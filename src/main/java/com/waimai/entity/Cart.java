package com.waimai.entity;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * 购物车实体类，对应数据库 cart 表。
 * 购物车的一项 = 某个用户 + 某个菜品 + 数量。
 */
public class Cart {

    private Long id;

    @NotNull(message = "用户id不能为空")
    private Long userId;   // 谁的购物车

    @NotNull(message = "菜品id不能为空")
    private Long dishId;   // 哪个菜

    @Min(value = 1, message = "数量必须大于等于1")
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
