package com.easemob.im.server.api.block.group.join;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class BlockUserJoinGroupResponse {
    @JsonProperty("data")
    private BlockUserJoinGroupResource resource;

    @JsonCreator
    public BlockUserJoinGroupResponse(@JsonProperty("data") BlockUserJoinGroupResource resource) {
        this.resource = resource;
    }

    public boolean getSuccess() {
        return this.resource != null && this.resource.success;
    }

    public static class BlockUserJoinGroupResource {
        @JsonProperty("result")
        private boolean success;

        @JsonCreator
        public BlockUserJoinGroupResource(@JsonProperty("result") boolean success) {
            this.success = success;
        }

    }
}
