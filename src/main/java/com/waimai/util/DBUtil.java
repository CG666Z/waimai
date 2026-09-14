package com.waimai.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * 数据库连接工具类：负责和 MySQL 建立连接。
 * 整个项目只有这一个地方写数据库地址、账号、密码。
 */
public class DBUtil {

    // ===== 这里改成你自己的数据库信息 =====
    private static final String URL = "jdbc:mysql://localhost:3306/waimai?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8";
    private static final String USERNAME = "root";
    private static final String PASSWORD = "123456"; // TODO: 改成你自己的 MySQL 密码
    // =====================================

    // 静态代码块：类第一次被加载时执行一次，用于注册 MySQL 驱动
    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException("MySQL 驱动加载失败，请检查 pom.xml 依赖", e);
        }
    }

    /**
     * 获取一个数据库连接。
     * 用完记得关闭（配合 try-with-resources 会自动关）。
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USERNAME, PASSWORD);
    }
}
