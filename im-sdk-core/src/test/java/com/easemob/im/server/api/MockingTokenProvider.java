package com.easemob.im.server.api;

import com.easemob.im.server.api.token.allocate.TokenProvider;
import com.easemob.im.server.api.token.Token;
import reactor.core.publisher.Mono;

import java.time.Instant;

public class MockingTokenProvider implements TokenProvider {

    @Override
    public Mono<Token> fetchAppToken() {
        return Mono.just(new Token("token", Instant.MAX));
    }

}
