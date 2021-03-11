package com.easemob.im.server.api;

import com.easemob.im.server.api.token.allocate.TokenProvider;
import com.easemob.im.server.model.EMToken;
import reactor.core.publisher.Mono;

import java.time.Instant;

public class MockingTokenProvider implements TokenProvider {

    @Override
    public Mono<EMToken> fetchAppToken() {
        return Mono.just(new EMToken("token", Instant.MAX));
    }

    @Override
    public Mono<EMToken> fetchUserToken(String username, String password) {
        return Mono.just(new EMToken("token", Instant.MAX));
    }

}
