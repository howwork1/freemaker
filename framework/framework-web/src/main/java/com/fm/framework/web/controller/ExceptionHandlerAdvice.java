package com.fm.framework.web.controller;

import com.fm.framework.core.exception.BusinessException;
import com.fm.framework.web.response.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 统一异常处理
 * @author clufeng
 * @version 1.0.0
 **/
@ControllerAdvice
@ResponseBody
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler
    public ApiResponse<?> handleBusinessException(BusinessException e) {
        log.error("[errorCode:{}, errorMsg:{}], e: {}", e.getErrorCode(), e.getMessage(), e);
        return ApiResponse.ofFailed(e.getMessage());
    }

    @ExceptionHandler
    public ApiResponse<?> handleException(Exception e) {
        log.error(e.getMessage(), e);
        return ApiResponse.ofFailed("系统开小差~~~");
    }

}
