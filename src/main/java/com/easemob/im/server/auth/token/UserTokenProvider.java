package com.easemob.im.server.auth.token;

import reactor.core.publisher.Mono;

public interface UserTokenProvider {

    Mono<Token> fetchUserToken(String username, String password);

}
