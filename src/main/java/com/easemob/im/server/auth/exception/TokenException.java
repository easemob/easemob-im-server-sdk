package com.easemob.im.server.auth.exception;

import com.easemob.im.server.api.ApiException;

public class TokenException extends ApiException {

    public TokenException(String message) {
        super(message);
    }

    public TokenException(String message, Throwable cause) {
        super(message, cause);
    }

}
