package com.easemob.im.server.api.metadata.user.delete;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class MetadataDeleteUserResponse {
    @JsonProperty("data")
    private Boolean data;

    @JsonCreator
    public MetadataDeleteUserResponse(@JsonProperty("data") Boolean data) {
        this.data = data;
    }

    public Boolean getData() {
        return data;
    }
}
