package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.loadbalance.EndpointRegistry;
import com.easemob.im.server.api.loadbalance.LoadBalancer;
import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.api.token.agora.AccessToken2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

public class AgoraTokenProvider implements TokenProvider {

    private static final Logger log = LoggerFactory.getLogger(AgoraTokenProvider.class);
    private static final int EXPIRE_IN_SECONDS = 20;

    private final ExchangeTokenRequest exchangeTokenRequest = new ExchangeTokenRequest();

    private final EMProperties properties;

    private final HttpClient httpClient;

    private final EndpointRegistry endpointRegistry;

    private final LoadBalancer loadBalancer;

    private final Codec codec;

    private final ErrorMapper errorMapper;

    private final Mono<Token> appToken;

    public AgoraTokenProvider(EMProperties properties, HttpClient httpClient,
            EndpointRegistry endpointRegistry, LoadBalancer loadBalancer, Codec codec,
            ErrorMapper errorMapper) {
        this.properties = properties;
        this.httpClient = httpClient;
        this.endpointRegistry = endpointRegistry;
        this.loadBalancer = loadBalancer;
        this.codec = codec;
        this.errorMapper = errorMapper;
        this.appToken = fetchEasemobToken(properties.getAppId(), properties.getAppCert())
                .cache(token -> Duration.ofSeconds(EXPIRE_IN_SECONDS)
                                .dividedBy(2),
                        error -> Duration.ofSeconds(10),
                        () -> Duration.ofSeconds(10));
    }

    @Override
    public Mono<Token> fetchAppToken() {
        return this.appToken;
    }

    @Override
    public Mono<Token> buildUserToken(String userId, int expireInSeconds,
            Consumer<AccessToken2> tokenConfigurer) throws Exception {
        String token2Value = AccessToken2Utils
                .buildUserCustomizedToken(properties.getAppId(), properties.getAppCert(), userId,
                        expireInSeconds, tokenConfigurer);
        final Instant expireAt = Instant.now().plusSeconds(expireInSeconds);
        return Mono.just(new Token(token2Value, expireAt));
    }

    private Mono<Token> fetchEasemobToken(String appId, String appCert) {
        return endpointRegistry.endpoints()
                .map(this.loadBalancer::loadBalance)
                .flatMap(endpoint -> this.httpClient
                        .baseUrl(String.format("%s/%s/token", endpoint.getUri(),
                                this.properties.getAppkeySlashDelimited()))
                        .headersWhen(headers -> buildAppTokenMono(appId, appCert)
                                .map(token -> headers
                                        .set("Authorization", String.format("Bearer %s", token))))
                        .post()
                        .send(Mono.create(sink -> sink
                                .success(this.codec.encode(exchangeTokenRequest))))
                        .responseSingle((rsp, buf) -> this.errorMapper.apply(rsp).then(buf)))
                .map(buf -> this.codec.decode(buf, TokenResponse.class))
                .map(TokenResponse::asTokenWithEpochMilli);
    }

    private Mono<String> buildAppTokenMono(String appId, String appCert) {
        return Mono.fromCallable(
                () -> AccessToken2Utils.buildAppToken(appId, appCert, EXPIRE_IN_SECONDS));
    }
}
