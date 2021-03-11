package com.easemob.im.server.model;

public class EMMessageStatus {
    private String messageId;

    private boolean isDelivered;

    private String toUsername;

    public EMMessageStatus(String messageId, boolean isDelivered) {
        this(messageId, isDelivered, null);
    }

    public EMMessageStatus(String messageId, boolean isDelivered, String toUsername) {
        this.messageId = messageId;
        this.isDelivered = isDelivered;
        this.toUsername = toUsername;
    }

    public String getMessageId() {
        return this.messageId;
    }

    public boolean isDelivered() {
        return this.isDelivered;
    }

    public String getToUsername() {
        return this.toUsername;
    }

    public EMMessageStatus withToUsername(String toUsername) {
        this.toUsername = toUsername;
        return this;
    }
}
