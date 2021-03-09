package com.easemob.im.server.api.chatrooms.admin.promote;


import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PromoteRoomAdminResponse {
    @JsonProperty("data")
    private Wrapper wrapper;

    public static class Wrapper {
        @JsonProperty("result")
        private boolean result;

        @JsonProperty("newadmin")
        private String username;

        @JsonCreator
        public Wrapper(@JsonProperty("result") boolean result,
                       @JsonProperty("newadmin") String username) {
            this.result = result;
            this.username = username;
        }

        private boolean getResult() {
            return this.result;
        }
    }

    @JsonCreator
    public PromoteRoomAdminResponse(@JsonProperty("data") Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public boolean isSuccess() {
        return this.wrapper != null && this.wrapper.getResult();
    }
}
