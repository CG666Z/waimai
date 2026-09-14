package com.waimai.dao;

import com.waimai.util.DBUtil;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * DAO 基类：封装了所有 DAO 共用的「执行 SQL」的逻辑。
 * 这样每个具体的 DAO 只需要关心两件事：
 *   1. 写什么 SQL；
 *   2. 怎么把查询结果变成对象。
 * 连接数据库、填参数、关资源这些重复劳动都交给这里。
 */
public abstract class BaseDao {

    /**
     * 执行增、删、改，返回受影响的行数。
     * params 是 SQL 里 ? 占位符对应的值（有几个 ? 就传几个参数）。
     */
    protected int executeUpdate(String sql, Object... params) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            // 把参数按顺序填进 ? 占位符（下标从 1 开始）
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            return ps.executeUpdate();
        } catch (SQLException e) {
            throw new RuntimeException("执行 SQL 失败：" + sql, e);
        }
    }

    /**
     * 执行插入，返回数据库自动生成的自增 id（比如订单 id）。
     * 下单时要用到它，才能把订单明细关联到刚生成的订单。
     */
    protected long executeInsert(String sql, Object... params) {
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            ps.executeUpdate();
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) {
                    return keys.getLong(1);
                }
                throw new RuntimeException("插入后没有拿到自增 id");
            }
        } catch (SQLException e) {
            throw new RuntimeException("执行插入失败：" + sql, e);
        }
    }

    /**
     * 执行查询，把每一行通过 mapper 转成对象，装进 List 返回。
     */
    protected <T> List<T> executeQuery(String sql, RowMapper<T> mapper, Object... params) {
        List<T> list = new ArrayList<>();
        try (Connection conn = DBUtil.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            for (int i = 0; i < params.length; i++) {
                ps.setObject(i + 1, params[i]);
            }
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    // 每循环一次，把当前这一行转成一个对象，加进 List
                    list.add(mapper.mapRow(rs));
                }
            }
            return list;
        } catch (SQLException e) {
            throw new RuntimeException("执行查询失败：" + sql, e);
        }
    }
}
