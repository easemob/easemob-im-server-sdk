package com.easemob.im.server;

public class EMException extends RuntimeException {
    public EMException(String message) {
        super(message);
    }

    public EMException(String message, Throwable cause) {
        super(message, cause);
    }
}
