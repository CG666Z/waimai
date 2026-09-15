package com.waimai.service;

import com.waimai.entity.Dish;
import com.waimai.mapper.DishMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜品业务逻辑。
 */
@Service
public class DishService {

    @Autowired
    private DishMapper dishMapper;

    public List<Dish> listByMerchant(Long merchantId) {
        return dishMapper.findByMerchantId(merchantId);
    }

    public Dish getDish(Long dishId) {
        return dishMapper.findById(dishId);
    }
}
