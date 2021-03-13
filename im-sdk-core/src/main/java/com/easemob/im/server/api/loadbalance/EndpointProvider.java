package com.easemob.im.server.api.loadbalance;

import reactor.core.publisher.Mono;

import java.util.List;

public interface EndpointProvider {
    Mono<List<Endpoint>> endpoints();
}
