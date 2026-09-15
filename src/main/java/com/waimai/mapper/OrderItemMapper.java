package com.waimai.mapper;

import com.waimai.entity.OrderItem;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 订单明细表的数据访问（MyBatis 版）。
 */
@Mapper
public interface OrderItemMapper {

    @Insert("INSERT INTO order_item(order_id, dish_id, dish_name, price, quantity) VALUES(#{orderId}, #{dishId}, #{dishName}, #{price}, #{quantity})")
    int insert(OrderItem item);

    @Select("SELECT id, order_id, dish_id, dish_name, price, quantity FROM order_item WHERE order_id = #{orderId}")
    List<OrderItem> findByOrderId(@Param("orderId") Long orderId);
}
