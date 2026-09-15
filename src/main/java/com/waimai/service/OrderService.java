package com.waimai.service;

import com.waimai.entity.Cart;
import com.waimai.entity.Dish;
import com.waimai.entity.Order;
import com.waimai.entity.OrderItem;
import com.waimai.mapper.CartMapper;
import com.waimai.mapper.DishMapper;
import com.waimai.mapper.OrderItemMapper;
import com.waimai.mapper.OrderMapper;
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
    private CartMapper cartMapper;
    @Autowired
    private DishMapper dishMapper;
    @Autowired
    private OrderMapper orderMapper;
    @Autowired
    private OrderItemMapper orderItemMapper;

    /**
     * 下单。完整流程：
     *   取购物车 → 确定商家 → 算总价 → 创建订单 → 插明细 → 扣库存 → 清空购物车
     */
    public void placeOrder(Long userId) {
        List<Cart> cartItems = cartMapper.findByUserId(userId);
        if (cartItems.isEmpty()) {
            throw new RuntimeException("购物车是空的，无法下单");
        }

        // 确定商家（简化：取第一个菜的商家）
        Dish firstDish = dishMapper.findById(cartItems.get(0).getDishId());
        Long merchantId = firstDish.getMerchantId();

        // 算总价 = 每个菜的 单价 × 数量 累加
        BigDecimal total = BigDecimal.ZERO;
        for (Cart item : cartItems) {
            Dish dish = dishMapper.findById(item.getDishId());
            total = total.add(dish.getPrice().multiply(BigDecimal.valueOf(item.getQuantity())));
        }

        // 创建订单。MyBatis 的 @Options 会把自增 id 回填到 order.id
        Order order = new Order();
        order.setUserId(userId);
        order.setMerchantId(merchantId);
        order.setTotalPrice(total);
        order.setStatus("待支付");
        order.setCreateTime(LocalDateTime.now());
        orderMapper.insert(order);
        long orderId = order.getId();

        // 逐条插入订单明细（冗余存菜名和单价）
        for (Cart item : cartItems) {
            Dish dish = dishMapper.findById(item.getDishId());
            OrderItem oi = new OrderItem();
            oi.setOrderId(orderId);
            oi.setDishId(dish.getId());
            oi.setDishName(dish.getName());
            oi.setPrice(dish.getPrice());
            oi.setQuantity(item.getQuantity());
            orderItemMapper.insert(oi);
        }

        // 扣库存
        for (Cart item : cartItems) {
            dishMapper.decreaseStock(item.getDishId(), item.getQuantity());
        }

        // 清空购物车
        cartMapper.deleteByUserId(userId);
    }

    /**
     * 查我的所有订单。
     */
    public List<Order> listMyOrders(Long userId) {
        return orderMapper.findByUserId(userId);
    }
}
