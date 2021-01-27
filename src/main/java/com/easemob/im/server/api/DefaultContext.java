package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.codec.JsonCodec;
import com.easemob.im.server.api.token.allocate.DefaultTokenProvider;
import com.easemob.im.server.api.token.allocate.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

public class DefaultContext implements Context {

    private final HttpClient httpClient;

    private final TokenProvider tokenProvider;

    private final BearerAuthorization bearerAuthorization;

    private final Codec codec;

    public HttpClient getHttpClient() {
        return this.httpClient;
    }

    public TokenProvider getTokenProvider() {
        return this.tokenProvider;
    }

    public BearerAuthorization getBearerAuthorization() {
        return this.bearerAuthorization;
    }

    public Codec getCodec() {
        return this.codec;
    }

    public DefaultContext(EMProperties properties) {
        this.httpClient = HttpClient.create(ConnectionProvider.create("easemob-sdk", properties.getHttpConnectionPoolSize()));
        // TODO: DefaultTokenProvider 应该用JsonCodec！！！
        this.tokenProvider = new DefaultTokenProvider(properties, httpClient, new ObjectMapper());
        this.bearerAuthorization = new BearerAuthorization(this.tokenProvider);
        this.codec = new JsonCodec();
    }
}
