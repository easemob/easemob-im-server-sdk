package com.easemob.im.server.api.user.exception;


import com.easemob.im.server.api.ApiException;

public class UserException extends ApiException {

    public UserException(String message) {
        super(message);
    }

    public UserException(String message, Throwable cause) {
        super(message, cause);
    }

}
