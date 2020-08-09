package com.fm.framework.web.response;

/**
 * <p>Api 状态</p>
 *
 * @author clufeng
 */
public enum ApiStatus {

    SUCCESS(1, "success"),
    FAILED(0, "failed"),
    BAD_REQUEST(400, "Bad Request, Invalid param"),
    UNAUTHORIZED(401, "Unauthorized"),
    FORBIDDEN(403, "Forbidden"),
    NOT_FOUND(404, "Not Found"),
    LOCKED(423, "Locked"),
    INTERNAL_SERVER_ERROR(500, "Internal Server Error"),
    SERVICE_UNAVAILABLE(503, "Service Unavailable"),
    SERVICE_EXCEPTION(50000, "请求异常，请检查参数后重试！");

    private final int code;

    private final String message;

    ApiStatus(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

}
