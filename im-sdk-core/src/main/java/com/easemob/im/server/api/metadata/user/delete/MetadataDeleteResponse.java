package com.easemob.im.server.api.metadata.user.delete;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataDeleteResponse {
    @JsonProperty("data")
    private boolean success;

    @JsonCreator
    public MetadataDeleteResponse(@JsonProperty("data") Boolean success) {
        if (success == null) {
            this.success = false;
        } else {
            this.success = success;
        }
    }

    public boolean getSuccess() {
        return success;
    }
}
