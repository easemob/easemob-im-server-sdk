package com.easemob.im.server.exception;

import com.easemob.im.server.EMException;

public class EMTooManyRequestsException extends EMException {

    public EMTooManyRequestsException(String message) {
        super(message);
    }

}
