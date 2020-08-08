package com.fm.framework.core.exception;

import lombok.Getter;

/**
 * <p>业务异常</p>
 *
 * @author clufeng
 */
@Getter
public class BusinessException extends RuntimeException {

    private final String errorCode;

    public BusinessException(String errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

}
