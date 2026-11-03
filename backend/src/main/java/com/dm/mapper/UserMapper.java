package com.dm.mapper;

import com.dm.entity.User;
import org.apache.ibatis.annotations.Param;

/**
 * 用户Mapper接口
 */
public interface UserMapper {

    /**
     * 根据用户名查询用户
     */
    User selectByUsername(@Param("username") String username);

    /**
     * 根据ID查询用户
     */
    User selectById(@Param("id") Integer id);

    /**
     * 新增用户
     */
    int insert(User user);
}
