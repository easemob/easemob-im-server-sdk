package com.easemob.im.server.api;

import com.easemob.im.server.api.token.allocate.TokenProvider;
import io.netty.handler.codec.http.HttpHeaders;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class BearerAuthorization implements Function<HttpHeaders, Mono<? extends HttpHeaders>> {

    private TokenProvider tokenProvider;

    public BearerAuthorization(TokenProvider tokenProvider) {
        this.tokenProvider = tokenProvider;
    }

    @Override
    public Mono<? extends HttpHeaders> apply(HttpHeaders headers) {
        return this.tokenProvider.fetchAppToken()
            .map(t -> headers.add("Authorization", String.format("Bearer %s", t.getValue())));
    }

}
