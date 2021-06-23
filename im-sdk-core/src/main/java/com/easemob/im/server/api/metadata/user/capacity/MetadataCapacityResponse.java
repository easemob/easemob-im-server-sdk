package com.easemob.im.server.api.metadata.user.capacity;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataCapacityResponse {
    @JsonProperty("data")
    private Long capacity;

    @JsonCreator
    public MetadataCapacityResponse(@JsonProperty("data") Long capacity) {
        this.capacity = capacity;
    }

    public Long getCapacity() {
        return capacity;
    }
}
