package com.easemob.im.server.api.room.member.remove;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class RemoveRoomMemberResponse {

    @JsonProperty("data")
    private Wrapper wrapper;

    public static class Wrapper {
        @JsonProperty("result")
        private boolean result;

        @JsonCreator
        public Wrapper(@JsonProperty("result") boolean result) {
            this.result = result;
        }

        public boolean getResult() {
            return this.result;
        }
    }

    @JsonCreator
    public RemoveRoomMemberResponse(@JsonProperty("data") Wrapper wrapper) {
        this.wrapper = wrapper;
    }

    public boolean isSuccess() {
        return this.wrapper != null && this.wrapper.getResult();
    }
}
