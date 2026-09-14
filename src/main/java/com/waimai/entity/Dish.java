package com.waimai.entity;

import java.math.BigDecimal;

/**
 * 菜品实体类，对应数据库 dish 表。
 * 注意：价格用 BigDecimal，绝不能用 double（double 算钱会有精度误差）。
 */
public class Dish {

    private Long id;
    private Long merchantId;   // 属于哪个商家
    private String name;
    private BigDecimal price;  // 价格
    private Integer stock;     // 库存
    private Integer status;    // 1=在售 0=下架

    public Dish() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "Dish{id=" + id + ", name='" + name + "', price=" + price + ", stock=" + stock + "}";
    }
}
