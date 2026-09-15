package com.waimai.mapper;

import com.waimai.entity.Dish;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 菜品表的数据访问（MyBatis 版）。
 */
@Mapper
public interface DishMapper {

    @Select("SELECT id, merchant_id, name, price, stock, status FROM dish WHERE merchant_id = #{merchantId} AND status = 1")
    List<Dish> findByMerchantId(@Param("merchantId") Long merchantId);

    @Select("SELECT id, merchant_id, name, price, stock, status FROM dish WHERE id = #{id}")
    Dish findById(@Param("id") Long id);

    @Update("UPDATE dish SET stock = stock - #{quantity} WHERE id = #{id}")
    int decreaseStock(@Param("id") Long id, @Param("quantity") int quantity);
}
