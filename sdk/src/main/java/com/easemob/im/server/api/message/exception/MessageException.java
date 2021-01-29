package com.easemob.im.server.api.message.exception;

import com.easemob.im.server.api.ApiException;

public class MessageException extends ApiException {
    public MessageException(String message) {
        super(message);
    }

    public MessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
