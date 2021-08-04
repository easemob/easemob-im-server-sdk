package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.codec.JsonCodec;
import com.easemob.im.server.api.loadbalance.*;
import com.easemob.im.server.api.token.allocate.AgoraTokenProvider;
import com.easemob.im.server.api.token.allocate.DefaultTokenProvider;
import com.easemob.im.server.api.token.allocate.TokenProvider;
import com.easemob.im.server.exception.EMInvalidStateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

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

    public DefaultContext(EMProperties properties) {
        this.properties = properties;
        HttpClient httpClient = EMHttpClientFactory.create(properties);
        this.codec = new JsonCodec();
        this.errorMapper = new DefaultErrorMapper();
        this.loadBalancer = new UniformRandomLoadBalancer();
        EndpointProviderFactory endpointProviderFactory =
                new DefaultEndpointProviderFactory(this.properties, this.codec,
                        httpClient.baseUrl("http://rs.easemob.com"), this.errorMapper);
        this.endpointProvider = endpointProviderFactory.create();
        this.endpointRegistry =
                new TimedRefreshEndpointRegistry(this.endpointProvider, Duration.ofMinutes(5));

        EMProperties.Realm realm = properties.getRealm();
        if (realm == EMProperties.Realm.AGORA_REALM) {
            this.tokenProvider = new AgoraTokenProvider(properties.getAppId(), properties.getAppCert());
        } else if (realm == EMProperties.Realm.EASEMOB_REALM) {
            this.tokenProvider = new DefaultTokenProvider(properties, httpClient, this.endpointRegistry,
                    this.loadBalancer, this.codec, this.errorMapper);
        } else {
            throw new EMInvalidStateException(String.format("Realm value = %d is illegal", realm.intValue));
        }

        this.httpClient = httpClient.headersWhen(headers -> this.tokenProvider.fetchAppToken()
                .map(token -> headers
                        .set("Authorization", String.format("Bearer %s", token.getValue()))));
    }

    @Override
    public EMProperties getProperties() {
        return this.properties;
    }

    @Override
    public Mono<HttpClient> getHttpClient() {
        return this.endpointRegistry.endpoints().map(endpoints -> {
            Endpoint endpoint = this.loadBalancer.loadBalance(endpoints);
            String baseUri = String.format("%s/%s", endpoint.getUri(),
                    this.properties.getAppkeySlashDelimited());
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
}
