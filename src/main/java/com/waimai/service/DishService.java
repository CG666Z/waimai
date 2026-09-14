package com.waimai.service;

import com.waimai.dao.DishDao;
import com.waimai.entity.Dish;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 菜品业务逻辑。
 */
@Service
public class DishService {

    @Autowired
    private DishDao dishDao;

    public List<Dish> listByMerchant(Long merchantId) {
        return dishDao.findByMerchantId(merchantId);
    }

    public Dish getDish(Long dishId) {
        return dishDao.findById(dishId);
    }
}
