package com.dm.dto;

import lombok.Data;

/**
 * 统一返回结果类
 */
@Data
public class Result {
    private Integer code;
    private String message;
    private Object data;

    public static Result success() {
        Result r = new Result();
        r.setCode(200);
        r.setMessage("操作成功");
        return r;
    }

    public static Result success(Object data) {
        Result r = success();
        r.setData(data);
        return r;
    }

    public static Result success(String message, Object data) {
        Result r = success(data);
        r.setMessage(message);
        return r;
    }

    public static Result error(String message) {
        Result r = new Result();
        r.setCode(500);
        r.setMessage(message);
        return r;
    }

    public static Result error(Integer code, String message) {
        Result r = new Result();
        r.setCode(code);
        r.setMessage(message);
        return r;
    }
}
