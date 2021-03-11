package com.easemob.im.server.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class ChatGroup {
    /**
     * 群组id
     */
    private String groupId;

    /**
     * 返回的群组信息
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
