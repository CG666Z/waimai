package com.waimai.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * 订单实体类，对应数据库 orders 表。
 * status 取值：待支付 / 已支付 / 配送中 / 已完成 / 已取消
 */
public class Order {

    private Long id;
    private Long userId;         // 谁下的单
    private Long merchantId;     // 哪家店
    private BigDecimal totalPrice; // 总价
    private String status;       // 订单状态
    private LocalDateTime createTime; // 下单时间

    public Order() {
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

    public Long getMerchantId() {
        return merchantId;
    }

    public void setMerchantId(Long merchantId) {
        this.merchantId = merchantId;
    }

    public BigDecimal getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(BigDecimal totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Order{id=" + id + ", userId=" + userId + ", totalPrice=" + totalPrice + ", status='" + status + "'}";
    }
}
