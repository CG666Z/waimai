package com.waimai.dao;

import com.waimai.entity.Dish;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 菜品表的数据访问。
 */
@Repository
public class DishDao extends BaseDao {

    // 查某个商家的所有在售菜品
    public List<Dish> findByMerchantId(Long merchantId) {
        String sql = "SELECT id, merchant_id, name, price, stock, status FROM dish WHERE merchant_id = ? AND status = 1";
        return executeQuery(sql, rs -> {
            Dish d = new Dish();
            d.setId(rs.getLong("id"));
            d.setMerchantId(rs.getLong("merchant_id"));
            d.setName(rs.getString("name"));
            d.setPrice(rs.getBigDecimal("price"));
            d.setStock(rs.getInt("stock"));
            d.setStatus(rs.getInt("status"));
            return d;
        }, merchantId);
    }

    // 按 id 查单个菜（加购物车时需要拿到它的价格和名字）
    public Dish findById(Long id) {
        String sql = "SELECT id, merchant_id, name, price, stock, status FROM dish WHERE id = ?";
        List<Dish> list = executeQuery(sql, rs -> {
            Dish d = new Dish();
            d.setId(rs.getLong("id"));
            d.setMerchantId(rs.getLong("merchant_id"));
            d.setName(rs.getString("name"));
            d.setPrice(rs.getBigDecimal("price"));
            d.setStock(rs.getInt("stock"));
            d.setStatus(rs.getInt("status"));
            return d;
        }, id);
        return list.isEmpty() ? null : list.get(0);
    }

    // 扣减库存（下单时用）
    public int decreaseStock(Long id, int quantity) {
        String sql = "UPDATE dish SET stock = stock - ? WHERE id = ?";
        return executeUpdate(sql, quantity, id);
    }
}
