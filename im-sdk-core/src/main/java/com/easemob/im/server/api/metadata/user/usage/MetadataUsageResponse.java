package com.easemob.im.server.api.metadata.user.usage;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataUsageResponse {
    @JsonProperty("data")
    private Long bytesUsed;

    @JsonCreator
    public MetadataUsageResponse(@JsonProperty("data") Long bytesUsed) {
        this.bytesUsed = bytesUsed;
    }

    public Long getBytesUsed() {
        return bytesUsed;
    }
}
