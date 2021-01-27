package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.model.EMToken;
import reactor.core.publisher.Mono;

public interface TokenProvider {

    Mono<EMToken> fetchAppToken();

    Mono<EMToken> fetchUserToken(String username, String password);

}
