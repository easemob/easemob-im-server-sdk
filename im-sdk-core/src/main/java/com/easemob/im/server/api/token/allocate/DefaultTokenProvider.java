package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.loadbalance.Endpoint;
import com.easemob.im.server.api.loadbalance.EndpointRegistry;
import com.easemob.im.server.api.loadbalance.LoadBalancer;
import com.easemob.im.server.api.token.Token;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class DefaultTokenProvider implements TokenProvider {

    private final EMProperties properties;

    private final HttpClient httpClient;

    private final EndpointRegistry endpointRegistry;

    private final LoadBalancer loadBalancer;

    private final Codec codec;

    private final ErrorMapper errorMapper;

    private final Mono<Token> appToken;

    public DefaultTokenProvider(EMProperties properties, HttpClient httpClient, EndpointRegistry endpointRegistry, LoadBalancer loadBalancer, Codec codec, ErrorMapper errorMapper) {
        this.properties = properties;
        this.httpClient = httpClient;
        this.endpointRegistry = endpointRegistry;
        this.loadBalancer = loadBalancer;
        this.codec = codec;
        this.errorMapper = errorMapper;
        AppTokenRequest appTokenRequest = AppTokenRequest.of(this.properties.getClientId(), this.properties.getClientSecret());
        this.appToken = fetchToken(appTokenRequest)
                .cache(token -> Duration.between(Instant.now(), token.getExpireTimestamp()).dividedBy(2),
                        error -> Duration.ofSeconds(10),
                        () -> Duration.ofSeconds(10));
    }

    @Override
    public Mono<Token> fetchAppToken() {
        return this.appToken;
    }

    @Override
    public Mono<Token> fetchUserToken(String username, String password) {
        return fetchToken(UserTokenRequest.of(username, password));
    }

    private Mono<Token> fetchToken(TokenRequest tokenRequest) {
        List<Endpoint> endpoints = endpointRegistry.endpoints();
        Endpoint endpoint = this.loadBalancer.loadBalance(endpoints);

        return this.httpClient
            .baseUrl(String.format("%s/%s", endpoint.getUri(), this.properties.getAppkeySlashDelimited()))
            .post()
            .uri("/token")
            .send(Mono.create(sink -> sink.success(this.codec.encode(tokenRequest))))
            .responseSingle((rsp, buf) -> this.errorMapper.apply(rsp).then(buf))
            .map(buf -> this.codec.decode(buf, TokenResponse.class))
            .map(TokenResponse::asToken);
    }
}
