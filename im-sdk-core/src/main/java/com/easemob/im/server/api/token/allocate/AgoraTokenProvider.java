package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.exception.EMInvalidStateException;
import io.agora.chat.ChatTokenBuilder2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Instant;

public class AgoraTokenProvider implements TokenProvider {
    private static final Logger log = LoggerFactory.getLogger(DefaultTokenProvider.class);
    private static final int DEFAULT_EXPIRE = 600;

    private final ChatTokenBuilder2 tokenBuilder = new ChatTokenBuilder2();

    private final EMProperties properties;
    private final String appId;
    private final String appCertificate;
    private final Token appToken;

    public AgoraTokenProvider(EMProperties properties) {

        this.properties = properties;

        final EMProperties.Realm realm = properties.getRealm();
        if (realm != EMProperties.Realm.AGORA_REALM) {
            throw new EMInvalidStateException("realm is not AGORA_REALM");
        }

        this.appId = properties.getAppId();
        this.appCertificate = properties.getAppCertificate();

        final String appTokenValue = tokenBuilder.buildAppToken(appId, appCertificate, DEFAULT_EXPIRE);
        final Instant expireAt = Instant.now().plusSeconds(DEFAULT_EXPIRE);
        this.appToken = new Token(appTokenValue, expireAt);
    }

    @Override
    public Mono<Token> fetchAppToken() {
        return Mono.just(this.appToken);
    }

    @Override
    public Mono<Token> fetchUserToken(String username, String password) {
        final Token userToken = generateUserToken(username);
        return Mono.just(userToken);
    }

    private Token generateUserToken(String userUUIDString) {
        final String userTokenValue = tokenBuilder.buildUserToken(appId, appCertificate, userUUIDString, DEFAULT_EXPIRE);
        final Instant expireAt = Instant.now().plusSeconds(DEFAULT_EXPIRE);
        return new Token(userTokenValue, expireAt);
    }
}
