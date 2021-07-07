package com.easemob.im.server.api.block.group.join;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UnblockUserJoinGroupResponse {
    @JsonProperty("data")
    private UnblockUserJoinGroupResource resource;

    @JsonCreator
    public UnblockUserJoinGroupResponse(
            @JsonProperty("data") UnblockUserJoinGroupResource resource) {
        this.resource = resource;
    }

    public boolean getSuccess() {
        return this.resource != null && this.resource.success;
    }

    public static class UnblockUserJoinGroupResource {
        @JsonProperty("result")
        private boolean success;

        @JsonCreator
        public UnblockUserJoinGroupResource(@JsonProperty("result") boolean success) {
            this.success = success;
        }

    }
}
