package com.easemob.im.server.exception;

import com.easemob.im.server.EMException;

public class EMJsonException extends EMException {

    public EMJsonException(String message) {
        super(message);
    }

    public EMJsonException(String message, Throwable cause) {
        super(message, cause);
    }
}
