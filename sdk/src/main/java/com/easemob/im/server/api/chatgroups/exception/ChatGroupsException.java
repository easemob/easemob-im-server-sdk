package com.easemob.im.server.api.chatgroups.exception;

import com.easemob.im.server.api.ApiException;

public class ChatGroupsException extends ApiException {
    public ChatGroupsException(String message) {
        super(message);
    }

    public ChatGroupsException(String message, Throwable cause) {
        super(message, cause);
    }
}
