package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.api.token.agora.AccessToken2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.function.Consumer;

public class AgoraTokenProvider implements TokenProvider {
    private static final Logger log = LoggerFactory.getLogger(DefaultTokenProvider.class);

    private final String appId;
    private final String appCert;
    private final Mono<Token> appToken;

    public AgoraTokenProvider(String appId, String appCert, int expireSeconds) {
        this.appId = appId;
        this.appCert = appCert;
        this.appToken = Mono.fromCallable(() -> {
            final String appTokenValue =
                    AccessToken2Utils.buildAppToken(appId, appCert, expireSeconds);
            final Instant expireAt = Instant.now().plusSeconds(expireSeconds);
            return new Token(appTokenValue, expireAt);
        }).cache(token -> Duration.ofSeconds(expireSeconds).dividedBy(2),
                error -> Duration.ofSeconds(10),
                () -> Duration.ofSeconds(10)
        );
    }

    @Override
    public Mono<Token> fetchAppToken() {
        return this.appToken;
    }


    @Override
    public Mono<Token> buildUserToken(String userId, int expireSeconds,
            Consumer<AccessToken2> tokenConfigurer) throws Exception {
        String token2Value = AccessToken2Utils
                .buildUserCustomizedToken(appId, appCert, userId, expireSeconds, tokenConfigurer);
        final Instant expireAt = Instant.now().plusSeconds(expireSeconds);
        return Mono.just(new Token(token2Value, expireAt));
    }
}
