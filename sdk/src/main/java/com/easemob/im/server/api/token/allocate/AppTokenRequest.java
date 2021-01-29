package com.easemob.im.server.api.token.allocate;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class AppTokenRequest implements TokenRequest {

    @JsonProperty("grant_type")
    private final String grantType = "client_credentials";

    @JsonProperty("client_id")
    private final String clientId;

    @JsonProperty("client_secret")
    private final String clientSecret;

    @JsonCreator
    public static AppTokenRequest of(@JsonProperty("client_id") String clientId,
                                     @JsonProperty("client_secret") String clientSecret) {
        return new AppTokenRequest(clientId, clientSecret);
    }

    public String getGrantType() {
        return grantType;
    }

    public String getClientId() {
        return clientId;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    private AppTokenRequest(String clientId, String clientSecret) {
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

}
