package com.easemob.im.server.api.message.recall;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RecallMessageSource {
    /**
     * 撤回消息的消息 ID
     */
    @JsonProperty("msg_id")
    private String messageId;

    /**
     * 撤回消息的三种消息类型：chat：单聊 ；groupchat：群聊 ；chatroom：聊天室
     */
    @JsonProperty("chat_type")
    private String chatType;

    /**
     * 消息撤回方，不传默认使用的是 admin，默认消息撤回方为原消息发送者。你可以通过用户 ID 指定消息撤回方
     */
    @JsonProperty("from")
    private String from;

    /**
     * 撤回消息的接收方，如果不提供则消息体找不到就撤回不了。单聊为接收方用户名，群组为群 ID，聊天室为聊天室 ID
     */
    @JsonProperty("to")
    private String to;

    /**
     * 是否为强制撤回：true：是，即超过服务器保存消息时间消息也可以被撤回，具体见服务器消息保存时长；
     * false：否，若设置的消息撤回时限超过服务端的消息保存时间，请求消息撤回时消息可能由于过期已在服务端删除，消息撤回请求会失败，即无法从收到该消息的客户端撤回该消息
     */
    @JsonProperty("force")
    private Boolean force;

    @JsonCreator
    public RecallMessageSource(@JsonProperty("msg_id") String messageId,
            @JsonProperty("chat_type") String chatType,
            @JsonProperty("from") String from,
            @JsonProperty("to") String to,
            @JsonProperty("force") Boolean force) {
        this.messageId = messageId;
        this.chatType = chatType;
        this.from = from;
        this.to = to;
        this.force = force;
    }
}
