package com.easemob.im.server.api;

import com.easemob.im.server.EMLog;
import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMVersion;
import com.easemob.im.server.api.codec.JsonCodec;
import com.easemob.im.server.api.token.allocate.DefaultTokenProvider;
import com.easemob.im.server.api.token.allocate.TokenProvider;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.netty.handler.logging.LogLevel;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

public class DefaultContext implements Context {

    private final EMProperties properties;

    private final HttpClient httpClient;

    private final TokenProvider tokenProvider;

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
    public Codec getCodec() {
        return this.codec;
    }

    @Override
    public ErrorMapper getErrorMapper() {
        return this.errorMapper;
    }

    public DefaultContext(EMProperties properties) {
        this.properties = properties;
        ConnectionProvider connectionProvider = ConnectionProvider.create("easemob-sdk", properties.getHttpConnectionPoolSize());
        HttpClient httpClient = HttpClient.create(connectionProvider)
            .baseUrl(properties.getBaseUri())
            .headers(headers -> headers.add("User-Agent", String.format("EasemobServerSDK/%s", EMVersion.getVersion())));
        if (EMLog.isDebugEnabled()) {
            httpClient = httpClient.wiretap("com.easemob.im.http", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        }
        this.codec = new JsonCodec();
        this.errorMapper = new DefaultErrorMapper();
        this.tokenProvider = new DefaultTokenProvider(properties, httpClient, this.codec, this.errorMapper);
        this.httpClient = httpClient
            .headersWhen(headers -> this.tokenProvider.fetchAppToken().map(token -> headers.add("Authorization", String.format("Bearer %s", token.getValue()))));
    }
}
