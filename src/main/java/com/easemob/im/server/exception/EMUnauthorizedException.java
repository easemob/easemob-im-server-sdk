package com.easemob.im.server.exception;

import com.easemob.im.server.EMException;

public class EMUnauthorizedException extends EMException {
    public EMUnauthorizedException(String message) {
        super(message);
    }
}
