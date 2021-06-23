package com.easemob.im.server.api.metadata.user.usage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataUsageResponse {
    @JsonProperty("data")
    private Long usage;

    @JsonCreator
    public MetadataUsageResponse(@JsonProperty("data") Long usage) {
        this.usage = usage;
    }

    public Long getUsage() {
        return usage;
    }
}
