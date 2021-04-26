package com.easemob.im.server.api.metadata.user.capacity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataCapacityResponse {
    @JsonProperty("data")
    private Long data;

    @JsonCreator
    public MetadataCapacityResponse(@JsonProperty("data") Long data) {
        this.data = data;
    }

    public Long getData() {
        return data;
    }
}
