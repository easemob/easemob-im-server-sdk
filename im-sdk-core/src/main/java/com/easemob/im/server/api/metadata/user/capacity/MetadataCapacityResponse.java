package com.easemob.im.server.api.metadata.user.capacity;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataCapacityResponse {
    // capacity would never be null so just use the primitive type here
    @JsonProperty("data")
    private long capacity;

    @JsonCreator
    public MetadataCapacityResponse(@JsonProperty("data") Long capacity) {
        if (capacity == null) {
            throw new EMInvalidArgumentException("metadata capacity value is null");
        }
        this.capacity = capacity;
    }

    public long getCapacity() {
        return capacity;
    }
}
