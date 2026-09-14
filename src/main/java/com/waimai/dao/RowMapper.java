package com.waimai.dao;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * 行映射器：把查询结果的一行，转成一个对象。
 * 这是一个「函数式接口」——只有一个方法，所以能用 lambda 简写。
 */
public interface RowMapper<T> {

    /**
     * 把 ResultSet 的当前这一行，转成一个 T 类型的对象。
     */
    T mapRow(ResultSet rs) throws SQLException;
}
