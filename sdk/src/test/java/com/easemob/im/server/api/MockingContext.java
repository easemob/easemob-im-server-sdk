package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.codec.JsonCodec;
import com.easemob.im.server.api.token.allocate.TokenProvider;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import reactor.netty.http.client.HttpClient;

public class MockingContext implements Context {

    private EMProperties properties;

    private HttpClient httpClient;

    private TokenProvider tokenProvider;

    private BearerAuthorization bearerAuthorization;

    private Codec codec;

    private ErrorMapper errorMapper;

    public MockingContext(EMProperties properties) {
        this.properties = properties;
        this.httpClient = HttpClient.newConnection().baseUrl(properties.getBaseUri())
            .headers(headers -> headers.add("User-Agent", String.format("EasemobServerSDK/%s", getClass().getPackage().getImplementationVersion())));
        this.tokenProvider = new MockingTokenProvider();
        this.bearerAuthorization = new BearerAuthorization(tokenProvider);
        this.codec = new JsonCodec();
        this.errorMapper = new DefaultErrorMapper();
    }

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

    public void setErrorMapper(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }
}
