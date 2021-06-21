package com.easemob.im.server.api.metadata.user.delete;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataDeleteResponse {
    @JsonProperty("data")
    private boolean success;

    @JsonCreator
    public MetadataDeleteResponse(@JsonProperty("data") Boolean success) {
        if (success == null) {
            throw new EMInvalidArgumentException("metadata deletion success flag is null");
        }
        this.success = success;
    }

    public boolean getSuccess() {
        return success;
    }
}
