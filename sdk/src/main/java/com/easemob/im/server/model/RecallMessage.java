package com.easemob.im.server.model;

import com.easemob.im.server.api.recallmessage.ChatType;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class RecallMessage {
    /**
     * 撤回消息的消息id
     */
    @JsonProperty("msg_id")
    private String messageId;

    /**
     * 撤回消息的接收⽅
     */
    private String to;

    /**
     * 撤回消息的消息类型，有单聊chat和群聊groupchat两种类型
     */
    @JsonProperty("chat_type")
    private ChatType chatType;
}
