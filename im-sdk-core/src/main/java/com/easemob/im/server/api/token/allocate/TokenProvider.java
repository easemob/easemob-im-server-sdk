package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.api.token.Token;
import com.easemob.im.server.api.token.agora.AccessToken2;
import com.easemob.im.server.exception.EMNotImplementedException;
import reactor.core.publisher.Mono;

import java.util.function.Consumer;

public interface TokenProvider {

    Mono<Token> fetchAppToken();

}
