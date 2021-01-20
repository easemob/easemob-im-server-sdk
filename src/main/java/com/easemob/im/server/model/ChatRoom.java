package com.easemob.im.server.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatRoom {
    /**
     * 聊天室id
     */
    private String roomId;

    /**
     * 返回的聊天室信息
     */
    private Object data;

    /**
     * 游标
     */
    private String cursor;

    /**
     * 数量
     */
    private Integer count;

    /**
     * 操作时间
     */
    private Long timeStamp;
}
