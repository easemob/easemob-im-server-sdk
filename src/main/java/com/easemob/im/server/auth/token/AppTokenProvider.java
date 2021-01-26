package com.easemob.im.server.auth.token;

import reactor.core.publisher.Mono;

public interface AppTokenProvider {

    Mono<Token> fetchAppToken();

}
