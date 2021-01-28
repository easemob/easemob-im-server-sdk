package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.codec.JsonCodec;
import com.easemob.im.server.api.token.allocate.DefaultTokenProvider;
import com.easemob.im.server.api.token.allocate.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;

public class DefaultContext implements Context {

    private final EMProperties properties;

    private final HttpClient httpClient;

    private final TokenProvider tokenProvider;

    private final BearerAuthorization bearerAuthorization;

    private final Codec codec;

    private final ErrorMapper errorMapper;

    @Override
    public EMProperties getProperties() {
        return this.properties;
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

    @Override
    public Codec getCodec() {
        return this.codec;
    }

    @Override
    public ErrorMapper getErrorMapper() {
        return this.errorMapper;
    }

    public DefaultContext(EMProperties properties) {
        this.properties = properties;
        this.httpClient = HttpClient.create(ConnectionProvider.create("easemob-sdk", properties.getHttpConnectionPoolSize()));
        this.codec = new JsonCodec();
        this.errorMapper = new DefaultErrorMapper();
        this.tokenProvider = new DefaultTokenProvider(properties, this.httpClient, this.codec, this.errorMapper);
        this.bearerAuthorization = new BearerAuthorization(this.tokenProvider);
    }
}
