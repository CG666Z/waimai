package com.waimai.service;

import com.waimai.dao.CartDao;
import com.waimai.entity.Cart;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车业务逻辑。
 */
@Service
public class CartService {

    @Autowired
    private CartDao cartDao;

    /**
     * 加购物车。
     * 核心业务规则：如果购物车里已经有这个菜，就「合并数量」；否则新增一项。
     * 这就是为什么同一个菜加两次，购物车里只有一行、数量变成 2。
     */
    public void add(Long userId, Long dishId, int quantity) {
        Cart existing = cartDao.findByUserAndDish(userId, dishId);
        if (existing != null) {
            // 已存在：合并数量
            cartDao.updateQuantity(existing.getId(), existing.getQuantity() + quantity);
        } else {
            // 不存在：新增一项
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setDishId(dishId);
            cart.setQuantity(quantity);
            cartDao.insert(cart);
        }
    }

    public List<Cart> listCart(Long userId) {
        return cartDao.findByUserId(userId);
    }

    public void remove(Long cartId) {
        cartDao.deleteById(cartId);
    }
}
