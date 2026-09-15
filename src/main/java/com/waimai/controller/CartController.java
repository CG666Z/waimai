package com.waimai.controller;

import com.waimai.entity.Cart;
import com.waimai.service.CartService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 购物车相关接口。
 */
@RestController
@RequestMapping("/cart")
public class CartController {

    @Autowired
    private CartService cartService;

    // POST /cart  请求体：{ "userId":1, "dishId":1, "quantity":2 }
    @PostMapping
    public String add(@RequestBody @Valid Cart cart) {
        cartService.add(cart.getUserId(), cart.getDishId(), cart.getQuantity());
        return "已加入购物车";
    }

    // GET /cart/{userId}  查某用户的购物车
    @GetMapping("/{userId}")
    public List<Cart> list(@PathVariable Long userId) {
        return cartService.listCart(userId);
    }
}
