package com.dm.entity;

import lombok.Data;
import java.util.Date;

/**
 * 用户实体类
 */
@Data
public class User {
    private Integer id;
    private String username;
    private String password;
    private String nickname;
    private Integer role;
    private Date createTime;
}
