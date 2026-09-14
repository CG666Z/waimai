package com.waimai.dao;

import com.waimai.entity.OrderItem;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 订单明细表的数据访问。
 */
@Repository
public class OrderItemDao extends BaseDao {

    // 新增一条明细
    public int insert(OrderItem item) {
        String sql = "INSERT INTO order_item(order_id, dish_id, dish_name, price, quantity) VALUES(?, ?, ?, ?, ?)";
        return executeUpdate(sql,
                item.getOrderId(), item.getDishId(), item.getDishName(), item.getPrice(), item.getQuantity());
    }

    // 查某个订单的所有明细
    public List<OrderItem> findByOrderId(Long orderId) {
        String sql = "SELECT id, order_id, dish_id, dish_name, price, quantity FROM order_item WHERE order_id = ?";
        return executeQuery(sql, rs -> {
            OrderItem i = new OrderItem();
            i.setId(rs.getLong("id"));
            i.setOrderId(rs.getLong("order_id"));
            i.setDishId(rs.getLong("dish_id"));
            i.setDishName(rs.getString("dish_name"));
            i.setPrice(rs.getBigDecimal("price"));
            i.setQuantity(rs.getInt("quantity"));
            return i;
        }, orderId);
    }
}
