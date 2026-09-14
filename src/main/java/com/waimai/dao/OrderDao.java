package com.waimai.dao;

import com.waimai.entity.Order;
import org.springframework.stereotype.Repository;

import java.sql.Timestamp;
import java.util.List;

/**
 * 订单表的数据访问。
 */
@Repository
public class OrderDao extends BaseDao {

    // 下单：插入订单，返回自动生成的订单 id
    public long insert(Order order) {
        String sql = "INSERT INTO orders(user_id, merchant_id, total_price, status, create_time) VALUES(?, ?, ?, ?, ?)";
        return executeInsert(sql,
                order.getUserId(),
                order.getMerchantId(),
                order.getTotalPrice(),
                order.getStatus(),
                Timestamp.valueOf(order.getCreateTime()));
    }

    // 查某用户的所有订单（按时间倒序，最新的在前）
    public List<Order> findByUserId(Long userId) {
        String sql = "SELECT id, user_id, merchant_id, total_price, status, create_time FROM orders WHERE user_id = ? ORDER BY create_time DESC";
        return executeQuery(sql, rs -> {
            Order o = new Order();
            o.setId(rs.getLong("id"));
            o.setUserId(rs.getLong("user_id"));
            o.setMerchantId(rs.getLong("merchant_id"));
            o.setTotalPrice(rs.getBigDecimal("total_price"));
            o.setStatus(rs.getString("status"));
            o.setCreateTime(rs.getTimestamp("create_time").toLocalDateTime());
            return o;
        }, userId);
    }

    // 修改订单状态
    public int updateStatus(Long id, String status) {
        String sql = "UPDATE orders SET status = ? WHERE id = ?";
        return executeUpdate(sql, status, id);
    }
}
