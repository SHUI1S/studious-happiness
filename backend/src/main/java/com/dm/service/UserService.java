package com.dm.service;

import com.dm.entity.User;

/**
 * 用户Service接口
 */
public interface UserService {

    /**
     * 登录
     */
    User login(String username, String password);

    /**
     * 注册
     */
    User register(String username, String password, String nickname);

    /**
     * 根据ID查询用户
     */
    User getById(Integer id);
}
