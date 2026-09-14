package com.waimai.dao;

import com.waimai.entity.Cart;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 购物车表的数据访问。
 * 注意：这里的每个方法都只做「一件事」，合并数量等业务逻辑放 service 层。
 */
@Repository
public class CartDao extends BaseDao {

    // 新增一项
    public int insert(Cart cart) {
        String sql = "INSERT INTO cart(user_id, dish_id, quantity) VALUES(?, ?, ?)";
        return executeUpdate(sql, cart.getUserId(), cart.getDishId(), cart.getQuantity());
    }

    // 查「某用户购物车里是否已有某个菜」（用于判断是新增还是合并）
    public Cart findByUserAndDish(Long userId, Long dishId) {
        String sql = "SELECT id, user_id, dish_id, quantity FROM cart WHERE user_id = ? AND dish_id = ?";
        List<Cart> list = executeQuery(sql, rs -> {
            Cart c = new Cart();
            c.setId(rs.getLong("id"));
            c.setUserId(rs.getLong("user_id"));
            c.setDishId(rs.getLong("dish_id"));
            c.setQuantity(rs.getInt("quantity"));
            return c;
        }, userId, dishId);
        return list.isEmpty() ? null : list.get(0);
    }

    // 修改数量
    public int updateQuantity(Long id, int quantity) {
        String sql = "UPDATE cart SET quantity = ? WHERE id = ?";
        return executeUpdate(sql, quantity, id);
    }

    // 查某用户的整个购物车
    public List<Cart> findByUserId(Long userId) {
        String sql = "SELECT id, user_id, dish_id, quantity FROM cart WHERE user_id = ?";
        return executeQuery(sql, rs -> {
            Cart c = new Cart();
            c.setId(rs.getLong("id"));
            c.setUserId(rs.getLong("user_id"));
            c.setDishId(rs.getLong("dish_id"));
            c.setQuantity(rs.getInt("quantity"));
            return c;
        }, userId);
    }

    // 删除一项
    public int deleteById(Long id) {
        String sql = "DELETE FROM cart WHERE id = ?";
        return executeUpdate(sql, id);
    }

    // 清空某用户的购物车（下单后用）
    public int deleteByUserId(Long userId) {
        String sql = "DELETE FROM cart WHERE user_id = ?";
        return executeUpdate(sql, userId);
    }
}
