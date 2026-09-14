package com.waimai.service;

import com.waimai.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * UserService 的单元测试。
 * @SpringBootTest 会启动 Spring 容器，注入真实的 UserService（连真实数据库）。
 */
@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    /**
     * 测试：注册成功 + 重复注册被拦截。
     */
    @Test
    void testRegisterAndDuplicate() {
        User user = new User();
        user.setUsername("test_" + System.currentTimeMillis()); // 用时间戳保证不重名
        user.setPassword("123");
        user.setPhone("111");
        user.setAddress("test");

        // 第一次注册：不抛异常就是成功
        userService.register(user);

        // 第二次注册同名用户：应该抛 RuntimeException
        assertThrows(RuntimeException.class, () -> userService.register(user));
    }

    /**
     * 测试：正确密码登录成功，错误密码登录失败。
     */
    @Test
    void testLogin() {
        String username = "login_" + System.currentTimeMillis();
        User user = new User();
        user.setUsername(username);
        user.setPassword("123");
        userService.register(user);

        // 正确密码 → 登录成功（返回非 null）
        assertNotNull(userService.login(username, "123"));

        // 错误密码 → 登录失败（返回 null）
        assertNull(userService.login(username, "wrong"));
    }
}
