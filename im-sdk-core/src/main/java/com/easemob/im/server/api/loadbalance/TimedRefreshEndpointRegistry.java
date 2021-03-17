package com.easemob.im.server.api.loadbalance;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.Disposable;
import reactor.core.publisher.Flux;
import reactor.util.retry.Retry;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;

public class TimedRefreshEndpointRegistry implements EndpointRegistry {

    private static final Logger log = LoggerFactory.getLogger(TimedRefreshEndpointRegistry.class);

    private final EndpointProvider endpointProvider;

    private final AtomicReference<List<Endpoint>> endpoints;

    private final Disposable refreshDisposable;

    public TimedRefreshEndpointRegistry(EndpointProvider endpointProvider, Duration refreshInterval) {
        this.endpointProvider = endpointProvider;
        this.endpoints = new AtomicReference<>();

        // make sure endpoints is not empty
        endpointProvider.endpoints()
                .timeout(Duration.ofSeconds(10))
                .doOnNext(nextEndpoints -> this.endpoints.set(nextEndpoints))
                .doOnError(error -> log.warn("load endpoints error: {}", error.getMessage()))
                .retryWhen(Retry.fixedDelay(10, Duration.ofSeconds(3)))
                .doOnError(error -> log.warn("load endpoints error, retry exhausted: {}", error.getMessage()))
                .block();

        this.refreshDisposable = Flux.interval(refreshInterval)
                .concatMap(i -> endpointProvider.endpoints().doOnNext(nextEndpoints -> this.endpoints.set(nextEndpoints)).then())
                .retry()
                .subscribe();
    }

    public void close() {
        this.refreshDisposable.dispose();
    }

    @Override
    public List<Endpoint> endpoints() {
        return this.endpoints.get();
    }
}
