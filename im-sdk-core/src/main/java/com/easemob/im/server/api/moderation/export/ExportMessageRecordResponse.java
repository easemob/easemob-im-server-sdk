package com.easemob.im.server.api.moderation.export;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

public class ExportMessageRecordResponse {

    @JsonProperty("entity")
    private Map<String, String> entity;

    @JsonCreator
    public ExportMessageRecordResponse(@JsonProperty("entity") Map<String, String> entity) {
        this.entity = entity;
    }

    public String getFileUuid() {
        return entity.get("uuid");
    }
}
