package com.easemob.im.server.api.moderation.export;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ExportMessageRecordRequest {

    @JsonProperty("dateFrom")
    private Long dateFrom;

    @JsonProperty("dateTo")
    private Long dateTo;

    @JsonProperty("targetType")
    private String targetType;

    @JsonProperty("msgType")
    private String msgType;

    @JsonProperty("moderationResult")
    private String moderationResult;

    @JsonProperty("providerResult")
    private String providerResult;

    @JsonCreator
    public ExportMessageRecordRequest(@JsonProperty("dateFrom") Long dateFrom,
            @JsonProperty("dateTo") Long dateTo,
            @JsonProperty("targetType") String targetType,
            @JsonProperty("msgType") String msgType,
            @JsonProperty("moderationResult") String moderationResult,
            @JsonProperty("providerResult") String providerResult) {

        this.dateFrom = dateFrom;
        this.dateTo = dateTo;
        this.targetType = targetType;
        this.msgType = msgType;
        this.moderationResult = moderationResult;
        this.providerResult = providerResult;
    }
}
