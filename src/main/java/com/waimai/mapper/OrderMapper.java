package com.waimai.mapper;

import com.waimai.entity.Order;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 订单表的数据访问（MyBatis 版）。
 */
@Mapper
public interface OrderMapper {

    // useGeneratedKeys：插入后把数据库自增的 id 回填到 order.id（替代之前 BaseDao 的 executeInsert）
    @Insert("INSERT INTO orders(user_id, merchant_id, total_price, status, create_time) VALUES(#{userId}, #{merchantId}, #{totalPrice}, #{status}, #{createTime})")
    @Options(useGeneratedKeys = true, keyProperty = "id")
    int insert(Order order);

    @Select("SELECT id, user_id, merchant_id, total_price, status, create_time FROM orders WHERE user_id = #{userId} ORDER BY create_time DESC")
    List<Order> findByUserId(@Param("userId") Long userId);

    @Update("UPDATE orders SET status = #{status} WHERE id = #{id}")
    int updateStatus(@Param("id") Long id, @Param("status") String status);
}
