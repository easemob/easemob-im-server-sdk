package com.easemob.im.server.api.chatmessages.exception;

import com.easemob.im.server.api.ApiException;

public class ChatMessagesException extends ApiException {
    public ChatMessagesException(String message) {
        super(message);
    }

    public ChatMessagesException(String message, Throwable cause) {
        super(message, cause);
    }
}
