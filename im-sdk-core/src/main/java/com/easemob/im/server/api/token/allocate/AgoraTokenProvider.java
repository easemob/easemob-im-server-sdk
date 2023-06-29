package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.loadbalance.EndpointRegistry;
import com.easemob.im.server.api.loadbalance.LoadBalancer;
import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.exception.EMInvalidStateException;
import com.easemob.im.server.exception.EMUnknownException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;
import java.time.Instant;

public class AgoraTokenProvider implements TokenProvider {

    private static final Logger log = LoggerFactory.getLogger(AgoraTokenProvider.class);
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
        this.appToken = fetchEasemobToken(properties.getAppId(), properties.getAppCert(),
                properties.getAgoraTokenExpireInSeconds())
                .cache(token -> Duration.between(Instant.now(), token.getExpireAt()).dividedBy(2),
                        error -> Duration.ofSeconds(10),
                        () -> Duration.ofSeconds(10));
    }

    // exchangeForEasemobToken(httpClient, baseUrl, agoraToken, codec, errorMapper)
    public static Mono<Token> exchangeForEasemobToken(HttpClient httpClient, String baseUrl,
            Mono<String> agoraToken, Codec codec, ErrorMapper errorMapper){
        return httpClient.baseUrl(baseUrl)
                .headersWhen(headers -> agoraToken
                        .map(token -> headers.set("Authorization", String.format("Bearer %s", token))))
                .post().uri("/token")
                .send(Mono.create(sink -> sink
                        .success(codec.encode(ExchangeTokenRequest.getInstance()))))
                .responseSingle((rsp, buf) -> {
                    return buf.switchIfEmpty(
                                    Mono.error(new EMUnknownException("response is null")))
                            .flatMap(byteBuf -> {
                                ErrorMapper mapper = new DefaultErrorMapper();
                                mapper.statusCode(rsp);
                                mapper.checkError(byteBuf);
                                return Mono.just(byteBuf);
                            });
                })
                .map(buf -> codec.decode(buf, ExchangeTokenResponse.class))
                .map(ExchangeTokenResponse::asToken);
    }

    @Override
    public Mono<Token> fetchAppToken() {
        return this.appToken;
    }

    private Mono<Token> fetchEasemobToken(String appId, String appCert, int expireInSeconds) {
        return endpointRegistry.endpoints().map(this.loadBalancer::loadBalance)
                .flatMap(endpoint ->
                        exchangeForEasemobToken(
                                this.httpClient,
                                String.format("%s/%s", endpoint.getUri(), this.properties.getAppkeySlashDelimited()),
                                buildAppToken(appId, appCert, expireInSeconds), this.codec, this.errorMapper
                        )
                );
    }

    private Mono<String> buildAppToken(String appId, String appCert, int expireInSeconds) {
        return Mono.fromCallable(() -> {
            AccessToken2 accessToken = new AccessToken2(appId, appCert, expireInSeconds);
            AccessToken2.Service serviceChat = new AccessToken2.ServiceChat();
            serviceChat.addPrivilegeChat(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_APP, expireInSeconds);
            accessToken.addService(serviceChat);
            try {
                log.debug("building agoraAppToken upon expiration, with expireInSeconds = {}", expireInSeconds);
                return accessToken.build();
            } catch (Exception e) {
                log.error("building accessToken2 failed", e);
                throw new EMInvalidStateException("building accessToken2 failed");
            }
        });
    }
}
