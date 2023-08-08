package com.atguigu.ssyx.common.exception;

import com.atguigu.ssyx.common.result.ResultCodeEnum;
import lombok.Data;

@Data
public class SsyxException extends RuntimeException {
    private int code;
    public SsyxException(ResultCodeEnum status) {
        super(status.getMessage());
        this.code = status.getCode();
    }

    public SsyxException(int code, String message) {
        super(message);
        this.code = code;
    }

    @Override
    public String toString() {
        return "SsyxException{" +
                "code=" + code +
                "message=" + this.getMessage() +
                '}';
    }
}
