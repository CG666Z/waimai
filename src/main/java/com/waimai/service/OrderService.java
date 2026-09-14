package com.waimai.service;

import com.waimai.dao.CartDao;
import com.waimai.dao.DishDao;
import com.waimai.dao.OrderDao;
import com.waimai.dao.OrderItemDao;
import com.waimai.entity.Cart;
import com.waimai.entity.Dish;
import com.waimai.entity.Order;
import com.waimai.entity.OrderItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 订单业务逻辑——整个项目最核心的一层。
 */
@Service
public class OrderService {

    @Autowired
    private CartDao cartDao;
    @Autowired
    private DishDao dishDao;
    @Autowired
    private OrderDao orderDao;
    @Autowired
    private OrderItemDao orderItemDao;

    /**
     * 下单。完整流程：
     *   1. 取购物车
     *   2. 确定商家（MVP 简化：假设购物车里的菜都属于同一家店）
     *   3. 算总价
     *   4. 创建订单
     *   5. 逐条插入订单明细
     *   6. 扣减库存
     *   7. 清空购物车
     */
    public void placeOrder(Long userId) {
        // 1. 拿购物车
        List<Cart> cartItems = cartDao.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("购物车是空的，无法下单");
        }

        // 2. 确定商家（简化：取第一个菜的商家）
        Dish firstDish = dishDao.findById(cartItems.get(0).getDishId());
        Long merchantId = firstDish.getMerchantId();

        // 3. 算总价 = 每个菜的 单价 × 数量 累加
        BigDecimal total = BigDecimal.ZERO;
        for (Cart item : cartItems) {
            Dish dish = dishDao.findById(item.getDishId());
            total = total.add(dish.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // 4. 创建订单（拿到自动生成的订单 id）
        Order order = new Order();
        order.setUserId(userId);
        order.setMerchantId(merchantId);
        order.setTotalPrice(total);
        order.setStatus("待支付");
        order.setCreateTime(LocalDateTime.now());
        long orderId = orderDao.insert(order);

        // 5. 逐条插入订单明细（冗余存菜名和单价）
        for (Cart item : cartItems) {
            Dish dish = dishDao.findById(item.getDishId());
            OrderItem oi = new OrderItem();
            oi.setOrderId(orderId);
            oi.setDishId(dish.getId());
            oi.setDishName(dish.getName());
            oi.setPrice(dish.getPrice());
            oi.setQuantity(item.getQuantity());
            orderItemDao.insert(oi);
        }

        // 6. 扣库存
        for (Cart item : cartItems) {
            dishDao.decreaseStock(item.getDishId(), item.getQuantity());
        }

        // 7. 清空购物车
        cartDao.deleteByUserId(userId);

        // 注意：上面第 4~7 步涉及多张表，理想情况应该用「事务」包起来，
        // 保证「要么全部成功、要么全部回滚」。纯 JDBC 手写事务比较绕（要共享同一个连接），
        // 所以控制台版先简化成顺序执行；升级 Spring Boot 后，加一个 @Transactional 注解就能自动搞定。
    }

    /**
     * 查我的所有订单。
     */
    public List<Order> listMyOrders(Long userId) {
        return orderDao.findByUserId(userId);
    }
}
