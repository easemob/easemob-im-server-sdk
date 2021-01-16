package com.easemob.im.server.model;

import com.easemob.im.server.api.message.TargetType;
import lombok.Builder;
import lombok.Data;

import java.util.Map;


@Data
@Builder
public class Message {
    // 发送的目标类型
    private TargetType targetType;

    // 消息的发送方
    private String from;

    // 消息的接收方
    private Map<String, String> target;

    // 发送消息的内容
    private Map<String, Object> messageContext;

    // 发送消息的时间
    private Long sendMessageTimestamp;

    // 消息的ext
    private Map<String, Object> ext;
}
