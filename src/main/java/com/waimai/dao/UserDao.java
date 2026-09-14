package com.waimai.dao;

import com.waimai.entity.User;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 用户表的数据访问。
 * 继承 BaseDao，只用写 SQL 和「怎么把结果变成 User 对象」。
 */
@Repository
public class UserDao extends BaseDao {

    /**
     * 新增用户（注册）。
     * 注意：SQL 里用 ? 占位符，值由后面的参数按顺序填进去，
     * 绝不能把值直接拼进 SQL 字符串——那样会有 SQL 注入风险。
     */
    public int insert(User user) {
        String sql = "INSERT INTO user(username, password, phone, address) VALUES(?, ?, ?, ?)";
        return executeUpdate(sql,
                user.getUsername(), user.getPassword(), user.getPhone(), user.getAddress());
    }

    /**
     * 根据用户名查用户（登录用）。
     * 查到返回 User，查不到返回 null。
     */
    public User findByUsername(String username) {
        String sql = "SELECT id, username, password, phone, address FROM user WHERE username = ?";
        List<User> list = executeQuery(sql, rs -> {
            User u = new User();
            u.setId(rs.getLong("id"));
            u.setUsername(rs.getString("username"));
            u.setPassword(rs.getString("password"));
            u.setPhone(rs.getString("phone"));
            u.setAddress(rs.getString("address"));
            return u;
        }, username);
        // 列表为空说明没查到，返回 null；否则返回第一条
        return list.isEmpty() ? null : list.get(0);
    }
}
