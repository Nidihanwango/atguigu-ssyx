package com.atguigu.ssyx.common.exception;

import com.atguigu.ssyx.common.result.Result;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(Exception.class)
    public Result error(Exception e){
        e.printStackTrace();
        return Result.fail();
    }
    @ExceptionHandler(SsyxException.class)
    public Result error(SsyxException e){
        e.printStackTrace();
        return Result.build(e.getCode(), e.getMessage(), null);
    }
}
