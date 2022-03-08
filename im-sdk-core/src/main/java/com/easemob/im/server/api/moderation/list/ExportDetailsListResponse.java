package com.easemob.im.server.api.moderation.list;

import com.easemob.im.server.api.moderation.ExportResource;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ExportDetailsListResponse {

    @JsonProperty("entities")
    private List<ExportResource> entities;

    /**
     * 是否为第一页
     */
    @JsonProperty("first")
    private Boolean first;

    /**
     * 是否为最后一页
     */
    @JsonProperty("last")
    private Boolean last;

    /**
     * 页大小
     */
    @JsonProperty("size")
    private Long size;

    /**
     * 页码
     */
    @JsonProperty("number")
    private Long number;

    /**
     * 页中元素个数
     */
    @JsonProperty("numberOfElements")
    private Long numberOfElements;

    /**
     * 总页码数
     */
    @JsonProperty("totalPages")
    private Long totalPages;

    /**
     * 总元素数
     */
    @JsonProperty("totalElements")
    private Long totalElements;

    @JsonCreator
    public ExportDetailsListResponse(
            @JsonProperty("entities") List<ExportResource> entities,
            @JsonProperty("first") Boolean first, @JsonProperty("last") Boolean last,
            @JsonProperty("size") Long size,
            @JsonProperty("number") Long number,
            @JsonProperty("numberOfElements") Long numberOfElements,
            @JsonProperty("totalPages") Long totalPages,
            @JsonProperty("totalElements") Long totalElements) {
        this.entities = entities;
        this.first = first;
        this.last = last;
        this.size = size;
        this.number = number;
        this.numberOfElements = numberOfElements;
        this.totalPages = totalPages;
        this.totalElements = totalElements;
    }

    @Override public String toString() {
        return "ExportDetailsListResponse{" +
                "entities=" + entities +
                ", first=" + first +
                ", last=" + last +
                ", size=" + size +
                ", number=" + number +
                ", numberOfElements=" + numberOfElements +
                ", totalPages=" + totalPages +
                ", totalElements=" + totalElements +
                '}';
    }
}
