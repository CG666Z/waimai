package com.waimai.service;

import com.waimai.entity.Cart;
import com.waimai.mapper.CartMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 购物车业务逻辑。
 */
@Service
public class CartService {

    @Autowired
    private CartMapper cartMapper;

    /**
     * 加购物车。
     * 核心业务规则：如果购物车里已经有这个菜，就「合并数量」；否则新增一项。
     */
    public void add(Long userId, Long dishId, int quantity) {
        Cart existing = cartMapper.findByUserAndDish(userId, dishId);
        if (existing != null) {
            cartMapper.updateQuantity(existing.getId(), existing.getQuantity() + quantity);
        } else {
            Cart cart = new Cart();
            cart.setUserId(userId);
            cart.setDishId(dishId);
            cart.setQuantity(quantity);
            cartMapper.insert(cart);
        }
    }

    public List<Cart> listCart(Long userId) {
        return cartMapper.findByUserId(userId);
    }

    public void remove(Long cartId) {
        cartMapper.deleteById(cartId);
    }
}
