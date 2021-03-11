package com.easemob.im.server.model;

import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@Builder
public class User {
    /**
     * 用户名
     */
    private String username;

    /**
     * 返回的用户信息
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
