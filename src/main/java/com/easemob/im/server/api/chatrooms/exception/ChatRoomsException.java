package com.easemob.im.server.api.chatrooms.exception;

import com.easemob.im.server.api.ApiException;

public class ChatRoomsException extends ApiException {
    public ChatRoomsException(String message) {
        super(message);
    }

    public ChatRoomsException(String message, Throwable cause) {
        super(message, cause);
    }
}
