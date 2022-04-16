package com.summer.handler.exception;

import com.summer.enums.ResponseStatus;
import com.summer.exception.SystemException;
import com.summer.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author Summer
 * @since 2022/4/16 16:11
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    @ExceptionHandler(SystemException.class)
    public R systemExceptionHandler(SystemException e) {
        // 打印异常信息
        log.error("出现了异常：{}", e);
        return R.fail(e.getCode(), e.getMsg());
    }

    @ExceptionHandler(Exception.class)
    public R exceptionHandler(Exception e) {
        // 打印异常信息
        log.error("出现了异常：{}", e);
        return R.fail(ResponseStatus.HTTP_STATUS_500.getResponseCode(), e.getMessage());
    }
}
