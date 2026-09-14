package com.waimai.service;

import com.waimai.entity.Cart;
import com.waimai.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * CartService 的单元测试，重点测「购物车合并数量」的逻辑。
 */
@SpringBootTest
class CartServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;

    private Long userId;

    // 每个测试方法运行前，先注册一个新用户（保证不重名、不互相影响）
    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("cart_test_" + System.currentTimeMillis());
        user.setPassword("123");
        userService.register(user);
        userId = userService.login(user.getUsername(), "123").getId();
    }

    /**
     * 测试：同一个菜加两次，应该合并成一行、数量相加。
     */
    @Test
    void testAddSameDishMerge() {
        // 加 dish1 两份，再加三份
        cartService.add(userId, 1L, 2);
        cartService.add(userId, 1L, 3);

        List<Cart> cart = cartService.listCart(userId);
        assertEquals(1, cart.size());              // 只有 1 行
        assertEquals(5, cart.get(0).getQuantity()); // 数量 = 2 + 3 = 5
    }

    /**
     * 测试：加两个不同的菜，应该有两行。
     */
    @Test
    void testAddDifferentDish() {
        cartService.add(userId, 1L, 1);
        cartService.add(userId, 2L, 1);

        List<Cart> cart = cartService.listCart(userId);
        assertEquals(2, cart.size()); // 两个不同的菜 → 2 行
    }
}
