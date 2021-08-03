package com.easemob.im.server.api.token.allocate;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.loadbalance.EndpointRegistry;
import com.easemob.im.server.api.loadbalance.LoadBalancer;
import com.easemob.im.server.exception.EMInvalidStateException;
import reactor.netty.http.client.HttpClient;

public class DefaultTokenProviderFactory implements TokenProviderFactory {

    private final EMProperties properties;
    private final HttpClient httpClient;
    private final EndpointRegistry endpointRegistry;
    private final LoadBalancer loadBalancer;
    private final Codec codec;
    private final ErrorMapper errorMapper;

    public DefaultTokenProviderFactory(EMProperties properties, HttpClient httpClient,
            EndpointRegistry endpointRegistry, LoadBalancer loadBalancer, Codec codec,
            ErrorMapper errorMapper) {
        this.properties = properties;
        this.codec = codec;
        this.httpClient = httpClient;
        this.errorMapper = errorMapper;
        this.loadBalancer = loadBalancer;
        this.endpointRegistry = endpointRegistry;
    }

    @Override
    public TokenProvider create() {
        EMProperties.Realm realm = properties.getRealm();
        if (realm == EMProperties.Realm.AGORA_REALM) {
            return new AgoraTokenProvider(properties.getAppId(), properties.getAppCert(), properties.getExpireSeconds());
        } else if (realm == EMProperties.Realm.EASEMOB_REALM) {
            return new DefaultTokenProvider(properties, httpClient, this.endpointRegistry,
                    this.loadBalancer, this.codec, this.errorMapper);
        } else {
            throw new EMInvalidStateException(String.format("Realm value = %d is illegal", realm.intValue));
        }
    }
}
