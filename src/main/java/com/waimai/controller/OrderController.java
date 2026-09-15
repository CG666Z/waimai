package com.waimai.controller;

import com.waimai.entity.Order;
import com.waimai.service.OrderService;
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
 * 订单相关接口。
 */
@RestController
@RequestMapping("/orders")
public class OrderController {

    @Autowired
    private OrderService orderService;

    // POST /orders  请求体：{ "userId":1 }
    @PostMapping
    public String place(@RequestBody @Valid Order order) {
        orderService.placeOrder(order.getUserId());
        return "下单成功";
    }

    // GET /orders/{userId}  查某用户的订单
    @GetMapping("/{userId}")
    public List<Order> list(@PathVariable Long userId) {
        return orderService.listMyOrders(userId);
    }
}
