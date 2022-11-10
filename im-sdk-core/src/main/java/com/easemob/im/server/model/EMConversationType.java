package com.easemob.im.server.model;

public enum EMConversationType {
    /**
     * 用户，表示单聊
     */
    USER("user"),

    /**
     * 群组，表示群聊
     */
    CHAT_GROUP("chatgroup");

    public String conversationType;

    EMConversationType(String conversationType) {
        this.conversationType = conversationType;
    }

    public String getConversationType() {
        return conversationType;
    }

    public void setConversationType(String conversationType) {
        this.conversationType = conversationType;
    }
}
