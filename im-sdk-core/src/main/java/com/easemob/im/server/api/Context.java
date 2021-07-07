package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.token.allocate.TokenProvider;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

public interface Context {

    Mono<HttpClient> getHttpClient();

    EMProperties getProperties();

    TokenProvider getTokenProvider();

    Codec getCodec();

    ErrorMapper getErrorMapper();

}
