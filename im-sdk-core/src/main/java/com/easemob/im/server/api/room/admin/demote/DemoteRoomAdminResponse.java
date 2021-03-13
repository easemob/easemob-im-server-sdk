package com.easemob.im.server.api.room.admin.demote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DemoteRoomAdminResponse {
    @JsonProperty("data")
    private Wrapper wrapper;

    public static class Wrapper {
        @JsonProperty("result")
        private boolean result;
        @JsonProperty("oldadmin")
        private String username;

        @JsonCreator
        public Wrapper(@JsonProperty("result") boolean result,
                       @JsonProperty("oldadmin") String username) {
            this.result = result;
            this.username = username;
        }

        public boolean getResult() {
            return this.result;
        }
    }

    @JsonCreator
    public DemoteRoomAdminResponse(@JsonProperty("data") Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public boolean isSuccess() {
        return this.wrapper != null && this.wrapper.getResult();
    }
}
