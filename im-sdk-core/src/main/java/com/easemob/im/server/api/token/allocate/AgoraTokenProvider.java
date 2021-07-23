package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.api.token.agora.ChatTokenBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Instant;

public class AgoraTokenProvider implements TokenProvider {
    private static final Logger log = LoggerFactory.getLogger(DefaultTokenProvider.class);
    private  final ChatTokenBuilder tokenBuilder = new ChatTokenBuilder();

    private final String appId;
    private final String appCertificate;
    private final int expire;
    private final Mono<Token> appToken;

    public AgoraTokenProvider(String appId, String appCertificate, int expire) {
        this.appId = appId;
        this.appCertificate = appCertificate;
        this.expire = expire;
        this.appToken = generateAppToken(appId, appCertificate, expire);
    }

    @Override
    public Mono<Token> fetchAppToken() {
        // TODO: Ken this wont automatically re-generate a new token for the caller because its not reactive
        return this.appToken;
    }

    @Override
    public Mono<Token> fetchUserToken(String userId, String password) {
        final Token userToken = generateUserToken(userId, expire);
        return Mono.just(userToken);
    }

    // TODO: Ken this is not reactive
    private Mono<Token> generateAppToken(String appId, String appCertificate, final int expire) {
        final String appTokenValue = tokenBuilder.buildAppToken(appId, appCertificate, expire);
        final Instant expireAt = Instant.now().plusSeconds(expire);
        Token appToken = new Token(appTokenValue, expireAt);
        return Mono.just(appToken);
    }

    private Token generateUserToken(String userId, final int expire) {
        final String userTokenValue = tokenBuilder.buildUserToken(appId, appCertificate, userId, expire);
        final Instant expireAt = Instant.now().plusSeconds(expire);
        return new Token(userTokenValue, expireAt);
    }
}
