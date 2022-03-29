package com.easemob.im.server.model;

public class EMMute {

    private final String username;

    private final Integer chatMuteRemain;

    private final Integer groupChatMuteRemain;

    private final Integer chatroomMuteRemain;

    private final Integer unixTime;

    public EMMute(String username, Integer chatMuteRemain, Integer groupChatMuteRemain,
            Integer chatroomMuteRemain, Integer unixTime) {
        this.username = username;
        this.chatMuteRemain = chatMuteRemain;
        this.groupChatMuteRemain = groupChatMuteRemain;
        this.chatroomMuteRemain = chatroomMuteRemain;
        this.unixTime = unixTime;
    }

    public String getUsername() {
        return username;
    }

    public Integer getChatMuteRemain() {
        return chatMuteRemain;
    }

    public Integer getGroupChatMuteRemain() {
        return groupChatMuteRemain;
    }

    public Integer getChatroomMuteRemain() {
        return chatroomMuteRemain;
    }

    public Integer getUnixTime() {
        return unixTime;
    }
}
