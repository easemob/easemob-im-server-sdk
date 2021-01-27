package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.codec.JsonCodec;
import com.easemob.im.server.api.token.allocate.TokenProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.netty.http.client.HttpClient;

public class MockingContext implements Context {

    private HttpClient httpClient;

    private TokenProvider tokenProvider;

    private BearerAuthorization bearerAuthorization;

    private Codec codec;

    public MockingContext(EMProperties properties) {
        this.httpClient = HttpClient.newConnection().baseUrl(properties.getBaseUri());
        this.tokenProvider = new MockingTokenProvider();
        this.bearerAuthorization = new BearerAuthorization(tokenProvider);
        this.codec = new JsonCodec();
    }
    @Override
    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    @Override
    public TokenProvider getTokenProvider() {
        return this.tokenProvider;
    }

    @Override
    public BearerAuthorization getBearerAuthorization() {
        return this.bearerAuthorization;
    }

    public Codec getCodec() {
        return this.codec;
    }

    public void setHttpClient(HttpClient httpClient) {
        this.httpClient = httpClient;
    }

    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    public void setBearerAuthorization(BearerAuthorization bearerAuthorization) {
        this.bearerAuthorization = bearerAuthorization;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }
}
