package com.easemob.im.server.api.metadata.user.get;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class MetadataBatchGetRequest {

    @JsonProperty("targets")
    private List<String> targets;

    @JsonProperty("properties")
    private List<String> properties;

    @JsonCreator
    public MetadataBatchGetRequest(@JsonProperty("targets") List<String> targets,
            @JsonProperty("properties") List<String> properties) {
        this.targets = targets;
        this.properties = properties;
    }

    public List<String> getTargets() {
        return targets;
    }

    public List<String> getProperties() {
        return properties;
    }
}
