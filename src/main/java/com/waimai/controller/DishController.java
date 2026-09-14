package com.waimai.controller;

import com.waimai.entity.Dish;
import com.waimai.service.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 菜品相关接口。
 */
@RestController
@RequestMapping("/merchants")
public class DishController {

    @Autowired
    private DishService dishService;

    // GET /merchants/{merchantId}/dishes  查某商家的菜品
    @GetMapping("/{merchantId}/dishes")
    public List<Dish> listByMerchant(@PathVariable Long merchantId) {
        return dishService.listByMerchant(merchantId);
    }
}
