package com.fm.framework.web.response;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * <p>API Response</p>
 *
 * @author clufeng
 */
@Data
@AllArgsConstructor
public class ApiResponse<T> {

    private int code;

    private String message;

    private T data;

    private String traceId;

    public static <T> ApiResponse<T> of(int code, String message, T data) {
        return new ApiResponse<>(code, message, data, null);
    }

    public static <T> ApiResponse<T> ofSuccess(T data) {
        return of(ApiStatus.SUCCESS, data);
    }

    public static <T> ApiResponse<T> ofFailed(String errorMsg) {
        return of(ApiStatus.FAILED, errorMsg);
    }

    public static <T> ApiResponse<T> of(int code, String message) {
        return of(code, message, null);
    }

    public static <T> ApiResponse<T> of(ApiStatus apiStatus, String message) {
        return of(apiStatus.getCode(), message, null);
    }

    public static <T> ApiResponse<T> of(ApiStatus apiStatus) {
        return of(apiStatus.getCode(), apiStatus.getMessage(), null);
    }


    public static <T> ApiResponse<T> of(ApiStatus apiStatus, T data) {
        return of(apiStatus.getCode(), apiStatus.getMessage(), data);
    }

}
