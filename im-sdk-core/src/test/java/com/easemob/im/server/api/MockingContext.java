package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMVersion;
import com.easemob.im.server.api.codec.JsonCodec;
import com.easemob.im.server.api.token.allocate.TokenProvider;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public class MockingContext implements Context {

    private EMProperties properties;

    private Mono<HttpClient> httpClient;

    private TokenProvider tokenProvider;

    private BearerAuthorization bearerAuthorization;

    private Codec codec;

    private ErrorMapper errorMapper;

    public MockingContext(EMProperties properties, String serverUri) {
        this.properties = properties;
        this.httpClient = Mono.just(HttpClient.newConnection()
                .baseUrl(String.format("%s/%s", serverUri, properties.getAppkeySlashDelimited()))
                .headers(headers -> headers.add("User-Agent",
                        String.format("EasemobServerSDK/%s", EMVersion.getVersion()))));
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
    public Mono<HttpClient> getHttpClient() {
        return this.httpClient;
    }

    public void setHttpClient(Mono<HttpClient> httpClient) {
        this.httpClient = httpClient;
    }

    @Override
    public TokenProvider getTokenProvider() {
        return this.tokenProvider;
    }

    public void setTokenProvider(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Codec getCodec() {
        return this.codec;
    }

    public void setCodec(Codec codec) {
        this.codec = codec;
    }

    @Override
    public ErrorMapper getErrorMapper() {
        return this.errorMapper;
    }

    public void setErrorMapper(ErrorMapper errorMapper) {
        this.errorMapper = errorMapper;
    }

    public void setBearerAuthorization(BearerAuthorization bearerAuthorization) {
        this.bearerAuthorization = bearerAuthorization;
    }
}
