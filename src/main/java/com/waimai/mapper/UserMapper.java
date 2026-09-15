package com.waimai.mapper;

import com.waimai.entity.User;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

/**
 * 用户表的数据访问（MyBatis 版）。
 * 对比之前的 UserDao：不用再写连接、填参数、手动映射 ResultSet，一个注解搞定。
 */
@Mapper
public interface UserMapper {

    @Insert("INSERT INTO user(username, password, phone, address) VALUES(#{username}, #{password}, #{phone}, #{address})")
    int insert(User user);

    @Select("SELECT id, username, password, phone, address FROM user WHERE username = #{username}")
    User findByUsername(@Param("username") String username);
}
