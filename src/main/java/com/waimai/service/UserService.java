package com.waimai.service;

import com.waimai.entity.User;
import com.waimai.mapper.UserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 用户业务逻辑：注册、登录。
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;

    /**
     * 注册。用户名重复就抛异常（由调用方提示用户）。
     */
    public void register(User user) {
        if (userMapper.findByUsername(user.getUsername()) != null) {
            throw new RuntimeException("用户名已存在，请换一个");
        }
        userMapper.insert(user);
    }

    /**
     * 登录。成功返回用户对象，失败返回 null。
     */
    public User login(String username, String password) {
        User user = userMapper.findByUsername(username);
        if (user != null && user.getPassword().equals(password)) {
            return user;
        }
        return null;
    }
}
