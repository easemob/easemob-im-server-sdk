package com.easemob.im.server;

public class EMException extends RuntimeException {
    /**
     *错误码(HttpResponseStatus.code)
     */
    private Integer errorCode;

    public EMException(String message) {
        super(message);
    }

    public EMException(String message, Throwable cause) {
        super(message, cause);
    }

    public Integer getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(Integer errorCode) {
        this.errorCode = errorCode;
    }
}
