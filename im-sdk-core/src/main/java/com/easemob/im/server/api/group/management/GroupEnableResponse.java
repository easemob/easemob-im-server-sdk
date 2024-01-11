package com.easemob.im.server.api.group.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupEnableResponse {
    @JsonProperty("data")
    private GroupEnableResource resource;

    @JsonCreator
    public GroupEnableResponse(@JsonProperty("data") GroupEnableResource resource) {
        this.resource = resource;
    }

    public GroupEnableResource getResource() {
        return resource;
    }

    public static class GroupEnableResource {
        @JsonProperty("disabled")
        private Boolean disabled;

        @JsonCreator
        public GroupEnableResource(@JsonProperty("disabled") Boolean disabled) {
            this.disabled = disabled;
        }

        public Boolean getDisabled() {
            return disabled;
        }
    }
}
