package com.easemob.im.server.model;

public enum EMNotificationType {
    /**
     * 默认值，采用全局配置
     */
    DEFAULT("DEFAULT"),

    /**
     * 接收全部离线消息的推送通知
     */
    ALL("ALL"),

    /**
     * 只接收提及当前用户的离线消息的推送通知
     */
    AT("AT"),

    /**
     * 不接收离线消息的推送通知
     */
    NONE("NONE")
    ;

    public String notificationType;

    EMNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }

    public String getNotificationType() {
        return notificationType;
    }

    public void setNotificationType(String notificationType) {
        this.notificationType = notificationType;
    }
}
