package com.easemob.im.server.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class User {
    /**
     * 操作用户的事件
     */
    private OperationUserEvent event;

    /**
     * 用户名
     */
    private String username;

    /**
     * 记录了用户详细的信息
     */
    private List<Map<String, Object>> entities;

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
