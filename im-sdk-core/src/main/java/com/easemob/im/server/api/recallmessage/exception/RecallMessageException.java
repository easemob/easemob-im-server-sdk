package com.easemob.im.server.api.recallmessage.exception;

import com.easemob.im.server.api.ApiException;

public class RecallMessageException extends ApiException {
    public RecallMessageException(String message) {
        super(message);
    }

    public RecallMessageException(String message, Throwable cause) {
        super(message, cause);
    }
}
