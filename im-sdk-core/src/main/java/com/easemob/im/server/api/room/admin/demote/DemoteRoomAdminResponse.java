package com.easemob.im.server.api.room.admin.demote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DemoteRoomAdminResponse {
    @JsonProperty("data")
    private Wrapper wrapper;

    public static class Wrapper {
        @JsonProperty("result")
        private String result;
        @JsonProperty("oldadmin")
        private String username;

        @JsonCreator
        public Wrapper(@JsonProperty("result") String result,
                       @JsonProperty("oldadmin") String username) {
            this.result = result;
            this.username = username;
        }

        public String getResult() {
            return this.result;
        }
    }

    @JsonCreator
    public DemoteRoomAdminResponse(@JsonProperty("data") Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public boolean isSuccess() {
        return this.wrapper != null && "success".equals(this.wrapper.getResult());
    }
}
