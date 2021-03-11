package com.easemob.im.server.api.chatfiles.exception;

import com.easemob.im.server.api.ApiException;

public class ChatFilesException extends ApiException {
    public ChatFilesException(String message) {
        super(message);
    }

    public ChatFilesException(String message, Throwable cause) {
        super(message, cause);
    }
}
