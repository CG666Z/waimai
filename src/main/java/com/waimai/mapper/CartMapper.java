package com.waimai.mapper;

import com.waimai.entity.Cart;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 购物车表的数据访问（MyBatis 版）。
 */
@Mapper
public interface CartMapper {

    @Insert("INSERT INTO cart(user_id, dish_id, quantity) VALUES(#{userId}, #{dishId}, #{quantity})")
    int insert(Cart cart);

    @Select("SELECT id, user_id, dish_id, quantity FROM cart WHERE user_id = #{userId} AND dish_id = #{dishId}")
    Cart findByUserAndDish(@Param("userId") Long userId, @Param("dishId") Long dishId);

    @Update("UPDATE cart SET quantity = #{quantity} WHERE id = #{id}")
    int updateQuantity(@Param("id") Long id, @Param("quantity") int quantity);

    @Select("SELECT id, user_id, dish_id, quantity FROM cart WHERE user_id = #{userId}")
    List<Cart> findByUserId(@Param("userId") Long userId);

    @Delete("DELETE FROM cart WHERE id = #{id}")
    int deleteById(@Param("id") Long id);

    @Delete("DELETE FROM cart WHERE user_id = #{userId}")
    int deleteByUserId(@Param("userId") Long userId);
}
