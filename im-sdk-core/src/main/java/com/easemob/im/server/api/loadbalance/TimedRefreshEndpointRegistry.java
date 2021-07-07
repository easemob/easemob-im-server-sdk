package com.easemob.im.server.api.loadbalance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

public class TimedRefreshEndpointRegistry implements EndpointRegistry {

    private static final Logger log = LoggerFactory.getLogger(TimedRefreshEndpointRegistry.class);

    private final EndpointProvider endpointProvider;

    private Mono<List<Endpoint>> endpoints;

    public TimedRefreshEndpointRegistry(EndpointProvider endpointProvider,
            Duration refreshInterval) {
        this.endpointProvider = endpointProvider;
        this.endpoints = endpointProvider.endpoints()
                .cache(endpoints -> Duration.ofSeconds(86400),
                        error -> Duration.ofSeconds(10),
                        () -> Duration.ofSeconds(10));
    }

    @Override
    public Mono<List<Endpoint>> endpoints() {
        return this.endpoints;
    }
}
