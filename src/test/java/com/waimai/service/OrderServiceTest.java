package com.waimai.service;

import com.waimai.entity.Order;
import com.waimai.entity.User;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

/**
 * OrderService 的单元测试，重点测「下单总价计算」和「空购物车拦截」。
 */
@SpringBootTest
class OrderServiceTest {

    @Autowired
    private UserService userService;
    @Autowired
    private CartService cartService;
    @Autowired
    private OrderService orderService;

    private Long userId;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setUsername("order_test_" + System.currentTimeMillis());
        user.setPassword("123");
        userService.register(user);
        userId = userService.login(user.getUsername(), "123").getId();
    }

    /**
     * 测试：下单后总价正确、购物车被清空。
     * dish1 黄焖鸡米饭 22 元 × 2 份 + dish3 羊肉串 3 元 × 1 份 = 47.00 元
     */
    @Test
    void testPlaceOrder() {
        cartService.add(userId, 1L, 2); // 22 × 2 = 44
        cartService.add(userId, 3L, 1); // 3 × 1 = 3

        orderService.placeOrder(userId);

        List<Order> orders = orderService.listMyOrders(userId);
        assertEquals(1, orders.size());
        assertEquals(new BigDecimal("47.00"), orders.get(0).getTotalPrice());
        assertEquals("待支付", orders.get(0).getStatus());

        // 下单后购物车应该被清空
        assertTrue(cartService.listCart(userId).isEmpty());
    }

    /**
     * 测试：空购物车下单，应该抛异常。
     */
    @Test
    void testPlaceOrderWithEmptyCart() {
        assertThrows(RuntimeException.class, () -> orderService.placeOrder(userId));
    }
}
