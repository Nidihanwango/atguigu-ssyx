package com.atguigu.ssyx.common.result;

import lombok.Data;

/**
 * 统一返回结果类
 *
 * @param <T> 数据类型
 */
@Data
public class Result<T> {
    private int code;
    private String message;
    private T data;

    private Result() {
    }

    public static <T> Result<T> build(ResultCodeEnum status, T data) {
        Result<T> result = new Result<>();
        result.setCode(status.getCode());
        result.setMessage(status.getMessage());
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T> Result<T> build(int code, String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        if (data != null) {
            result.setData(data);
        }
        return result;
    }

    public static <T> Result<T> ok(T data) {
        return build(ResultCodeEnum.SUCCESS, data);
    }

    public static <T> Result<T> fail(){
        return build(ResultCodeEnum.FAIL, null);
    }

    public static <T> Result<T> fail(T data){
        return build(ResultCodeEnum.FAIL, data);
    }

}
