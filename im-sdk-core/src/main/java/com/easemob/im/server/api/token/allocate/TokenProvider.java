package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.token.Token;
import reactor.core.publisher.Mono;

public interface TokenProvider {

    Mono<Token> fetchAppToken();

    Mono<Token> fetchUserToken(String username, String password);

}
