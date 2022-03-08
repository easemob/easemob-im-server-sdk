package com.easemob.im.server.api.moderation;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExportResource {

    @JsonProperty("id")
    private String id;

    @JsonProperty("uuid")
    private String uuid;

    /**
     * 文件名
     */
    @JsonProperty("fileName")
    private String fileName;

    /**
     * 导出任务状态，('Creating':生成中,'Failed':导出失败,'Abort':任务终止,'Finished':文件已生成)
     */
    @JsonProperty("status")
    private String status;

    /**
     * 导出查询条件
     */
    @JsonProperty("cond")
    private String cond;

    /**
     * 导出详情
     */
    @JsonProperty("detail")
    private String detail;

    /**
     * 文件大小
     */
    @JsonProperty("size")
    private Long size;

    /**
     * 过期时间
     */
    @JsonProperty("expiredAt")
    private String expiredTime;

    /**
     * 任务创建时间
     */
    @JsonProperty("createdAt")
    private String createdTime;

    /**
     * 任务数据更新时间
     */
    @JsonProperty("updatedAt")
    private String updatedTime;

    @JsonCreator
    public ExportResource(@JsonProperty("id") String id, @JsonProperty("uuid") String uuid,
            @JsonProperty("fileName") String fileName, @JsonProperty("status") String status,
            @JsonProperty("cond") String cond,
            @JsonProperty("detail") String detail, @JsonProperty("size") Long size,
            @JsonProperty("expiredAt") String expiredTime,
            @JsonProperty("createdAt") String createdTime,
            @JsonProperty("updatedAt") String updatedTime) {
        this.id = id;
        this.uuid = uuid;
        this.fileName = fileName;
        this.status = status;
        this.cond = cond;
        this.detail = detail;
        this.size = size;
        this.expiredTime = expiredTime;
        this.createdTime = createdTime;
        this.updatedTime = updatedTime;
    }

    @Override public String toString() {
        return "ExportResource{" +
                "id='" + id + '\'' +
                ", uuid='" + uuid + '\'' +
                ", fileName='" + fileName + '\'' +
                ", status='" + status + '\'' +
                ", cond='" + cond + '\'' +
                ", detail='" + detail + '\'' +
                ", size=" + size +
                ", expiredTime='" + expiredTime + '\'' +
                ", createdTime='" + createdTime + '\'' +
                ", updatedTime='" + updatedTime + '\'' +
                '}';
    }
}
