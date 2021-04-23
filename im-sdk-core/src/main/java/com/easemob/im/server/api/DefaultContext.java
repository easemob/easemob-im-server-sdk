package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMVersion;
import com.easemob.im.server.api.codec.JsonCodec;
import com.easemob.im.server.api.loadbalance.*;
import com.easemob.im.server.api.token.allocate.DefaultTokenProvider;
import com.easemob.im.server.api.token.allocate.TokenProvider;
import io.netty.handler.logging.LogLevel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;
import reactor.netty.resources.ConnectionProvider;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.time.Duration;

public class DefaultContext implements Context {

    private static final Logger log = LoggerFactory.getLogger(DefaultContext.class);

    private final EMProperties properties;

    private final HttpClient httpClient;

    private final TokenProvider tokenProvider;

    private final Codec codec;

    private final ErrorMapper errorMapper;

    private final LoadBalancer loadBalancer;

    private final EndpointProvider endpointProvider;

    private final EndpointRegistry endpointRegistry;


    @Override
    public EMProperties getProperties() {
        return this.properties;
    }

    @Override
    public Mono<HttpClient> getHttpClient() {
        return this.endpointRegistry.endpoints().map(endpoints -> {
            Endpoint endpoint = this.loadBalancer.loadBalance(endpoints);
            String baseUri = String.format("%s/%s", endpoint.getUri(), this.properties.getAppkeySlashDelimited());
            if (log.isDebugEnabled()) {
                log.debug("load balanced base uri: {}", baseUri);
             }
            return this.httpClient.baseUrl(baseUri);
        });
    }

    @Override
    public TokenProvider getTokenProvider() {
        return this.tokenProvider;
    }

    @Override
    public Codec getCodec() {
        return this.codec;
    }

    @Override
    public ErrorMapper getErrorMapper() {
        return this.errorMapper;
    }

    public DefaultContext(EMProperties properties) {
        this.properties = properties;
        ConnectionProvider connectionProvider = ConnectionProvider.create("easemob-sdk", properties.getHttpConnectionPoolSize());
        HttpClient httpClient = HttpClient.create(connectionProvider)
            .headers(headers -> headers.add("User-Agent", String.format("EasemobServerSDK/%s", EMVersion.getVersion())))
            .wiretap("com.easemob.im.http", LogLevel.DEBUG, AdvancedByteBufFormat.TEXTUAL);
        this.codec = new JsonCodec();
        this.errorMapper = new DefaultErrorMapper();
        this.loadBalancer = new UniformRandomLoadBalancer();
        this.endpointProvider = new DnsConfigEndpointProvider(this.properties, this.codec, httpClient.baseUrl("http://rs.easemob.com"), this.errorMapper);
        this.endpointRegistry = new TimedRefreshEndpointRegistry(this.endpointProvider, Duration.ofMinutes(5));
        this.tokenProvider = new DefaultTokenProvider(properties, httpClient, this.endpointRegistry, this.loadBalancer, this.codec, this.errorMapper);
        this.httpClient = httpClient.headersWhen(headers -> this.tokenProvider.fetchAppToken().map(token -> headers.set("Authorization", String.format("Bearer %s", token.getValue())).set("Content-Type", "application/json")));

    }
}
