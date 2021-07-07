package com.easemob.im.server.api.user.forcelogout;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UserForceLogoutResponse {
    @JsonProperty("data")
    private UserForceLogoutResultResource resultResource;

    @JsonCreator
    public UserForceLogoutResponse(
            @JsonProperty("data") UserForceLogoutResultResource resultResource) {
        this.resultResource = resultResource;
    }

    public boolean isSuccessful() {
        return this.resultResource.result;
    }

    private static class UserForceLogoutResultResource {
        @JsonProperty("result")
        private boolean result;

        @JsonCreator
        public UserForceLogoutResultResource(@JsonProperty("result") boolean result) {
            this.result = result;
        }
    }
}
