package com.easemob.im.server.api.room.superadmin.promote;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class PromoteRoomSuperAdminResponse {
    @JsonProperty("data")
    private Wrapper wrapper;

    @JsonCreator
    public PromoteRoomSuperAdminResponse(@JsonProperty("data") Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public boolean isSuccess() {
        return this.wrapper != null && "success".equals(this.wrapper.getResult());
    }

    public static class Wrapper {
        @JsonProperty("result")
        private String result;
        @JsonProperty("resource")
        private String resource;

        @JsonCreator
        public Wrapper(@JsonProperty("result") String result,
                @JsonProperty("resource") String resource) {
            this.result = result;
            this.resource = resource;
        }

        private String getResult() {
            return this.result;
        }
    }
}
