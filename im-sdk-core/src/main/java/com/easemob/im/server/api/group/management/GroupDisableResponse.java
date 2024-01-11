package com.easemob.im.server.api.group.management;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class GroupDisableResponse {
    @JsonProperty("data")
    private GroupDisableResource resource;

    @JsonCreator
    public GroupDisableResponse(@JsonProperty("data") GroupDisableResource resource) {
        this.resource = resource;
    }

    public GroupDisableResource getResource() {
        return resource;
    }

    public static class GroupDisableResource {
        @JsonProperty("disabled")
        private Boolean disabled;

        @JsonCreator
        public GroupDisableResource(@JsonProperty("disabled") Boolean disabled) {
            this.disabled = disabled;
        }

        public Boolean getDisabled() {
            return disabled;
        }
    }
}
