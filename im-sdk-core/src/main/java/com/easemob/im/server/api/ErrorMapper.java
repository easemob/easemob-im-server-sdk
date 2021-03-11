package com.easemob.im.server.api;

import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

public interface ErrorMapper {
    Mono<HttpClientResponse> apply(HttpClientResponse response);
}
