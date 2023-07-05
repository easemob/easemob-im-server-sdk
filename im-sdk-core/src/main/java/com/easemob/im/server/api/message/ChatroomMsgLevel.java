package com.easemob.im.server.api.message;

public enum ChatroomMsgLevel {
    LOW("low"),
    NORMAL("normal"),
    HIGH("high");

    private String level;

    ChatroomMsgLevel(String level) {
        this.level = level;
    }

    public String getLevel() {
        return level;
    }

    public static ChatroomMsgLevel of(String name) {
        for (ChatroomMsgLevel level : ChatroomMsgLevel.values()) {
            if (level.getLevel().equalsIgnoreCase(name)) {
                return level;
            }
        }
        return null;
    }
}
