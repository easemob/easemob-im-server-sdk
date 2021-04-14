package com.easemob.im.server.api;

import com.easemob.im.server.api.loadbalance.Endpoint;
import com.easemob.im.server.api.loadbalance.EndpointProvider;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;

public class MockingEndpointProvider implements EndpointProvider {

    @Override
    public Mono<List<Endpoint>> endpoints() {
        Endpoint endpoint1 = new Endpoint("https", "test.easemob.com", 443);
        Endpoint endpoint2 = new Endpoint("http", "1.2.3.4", 80);
        List<Endpoint> endpoints = new ArrayList<>();
        endpoints.add(endpoint1);
        endpoints.add(endpoint2);
        return Mono.just(endpoints);
    }
}
