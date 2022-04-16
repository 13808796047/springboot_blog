package com.summer.exception;

import com.summer.enums.ResponseStatus;

/**
 * @author Summer
 * @since 2022/4/16 16:04
 */
public class SystemException extends RuntimeException {
    private String code;

    private String msg;


    public SystemException(ResponseStatus httpCodeEnum) {
        super(httpCodeEnum.getDescription());
        this.code = httpCodeEnum.getResponseCode();
        this.msg = httpCodeEnum.getDescription();
    }

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
