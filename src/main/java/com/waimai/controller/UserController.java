package com.waimai.controller;

import com.waimai.entity.User;
import com.waimai.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户相关接口。
 */
@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    // POST /users/register  请求体：{ "username":"chen", "password":"123", "phone":"...", "address":"..." }
    @PostMapping("/register")
    public String register(@RequestBody User user) {
        userService.register(user);
        return "注册成功";
    }

    // POST /users/login  请求体：{ "username":"chen", "password":"123" }
    @PostMapping("/login")
    public User login(@RequestBody User user) {
        User loginUser = userService.login(user.getUsername(), user.getPassword());
        if (loginUser == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        return loginUser;
    }
}
