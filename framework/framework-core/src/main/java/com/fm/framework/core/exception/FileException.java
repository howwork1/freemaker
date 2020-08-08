package com.fm.framework.core.exception;

/**
 * Jss文件上传下载异常
 *
 * @author clufeng
 **/
public class FileException extends RuntimeException {

    public FileException(String errorMessage) {
        super(errorMessage);
    }

    public FileException(String errorMessage, Throwable t) {
        super(errorMessage, t);
    }

}
