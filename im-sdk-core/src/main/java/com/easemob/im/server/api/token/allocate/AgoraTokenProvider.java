package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.loadbalance.EndpointRegistry;
import com.easemob.im.server.api.loadbalance.LoadBalancer;
import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.exception.EMInvalidStateException;
import org.apache.logging.log4j.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.time.Duration;

public class AgoraTokenProvider implements TokenProvider {

    private static final Logger log = LoggerFactory.getLogger(AgoraTokenProvider.class);
    private static final String EXPIRE_IN_SECONDS_STRING =
            System.getenv("IM_TOKEN_EXPIRE_IN_SECONDS");
    // Both token and chat privilege will expire in an hour by default
    private static final int EXPIRE_IN_SECONDS = Strings.isNotBlank(EXPIRE_IN_SECONDS_STRING) ?
            Integer.parseInt(EXPIRE_IN_SECONDS_STRING) : 3600;

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

    public static Mono<Token> exchangeForEasemobToken(HttpClient httpClient, String baseUrl,
            String agoraToken,
            Codec codec, ErrorMapper errorMapper) {
        return httpClient.baseUrl(baseUrl)
                .headers(headers -> headers
                        .set("Authorization", String.format("Bearer %s", agoraToken)))
                .post().uri("/token")
                .send(Mono.create(sink -> sink
                        .success(codec.encode(ExchangeTokenRequest.getInstance()))))
                .responseSingle((rsp, buf) -> errorMapper.apply(rsp).then(buf))
                .map(buf -> codec.decode(buf, ExchangeTokenResponse.class))
                .map(ExchangeTokenResponse::asToken);
    }

    @Override
    public Mono<Token> fetchAppToken() {
        return this.appToken;
    }

    private Mono<Token> fetchEasemobToken(String appId, String appCert) {
        String agoraChatAppToken = buildAppToken(appId, appCert);
        return endpointRegistry.endpoints().map(this.loadBalancer::loadBalance)
                .flatMap(endpoint ->
                        exchangeForEasemobToken(
                                this.httpClient,
                                String.format("%s/%s", endpoint.getUri(),
                                        this.properties.getAppkeySlashDelimited()),
                                agoraChatAppToken, this.codec, this.errorMapper
                        )
                );
    }

    private String buildAppToken(String appId, String appCert) {

        AccessToken2 accessToken = new AccessToken2(appId, appCert, EXPIRE_IN_SECONDS);
        AccessToken2.Service serviceChat = new AccessToken2.ServiceChat();
        serviceChat
                .addPrivilegeChat(AccessToken2.PrivilegeChat.PRIVILEGE_CHAT_APP, EXPIRE_IN_SECONDS);
        accessToken.addService(serviceChat);
        try {
            return accessToken.build();
        } catch (Exception e) {
            log.error("building accessToken2 failed", e);
            throw new EMInvalidStateException("building accessToken2 failed");
        }
    }
}
