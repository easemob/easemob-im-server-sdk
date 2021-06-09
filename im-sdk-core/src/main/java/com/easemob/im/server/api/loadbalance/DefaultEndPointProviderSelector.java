package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.ErrorMapper;
import reactor.netty.http.client.HttpClient;

public class DefaultEndPointProviderSelector implements EndPointProviderSelector {

    private final EMProperties properties;

    private final DnsConfigEndpointProvider dnsConfigEndpointProvider;

    private final FixedEndpointProvider fixedEndpointProvider;

    public DefaultEndPointProviderSelector(EMProperties properties, Codec codec, HttpClient httpClient, ErrorMapper errorMapper) {
        this.properties = properties;
        this.dnsConfigEndpointProvider = new DnsConfigEndpointProvider(this.properties, codec, httpClient, errorMapper);
        this.fixedEndpointProvider = new FixedEndpointProvider(this.properties);
    }

    @Override
    public EndpointProvider selectProvider() {
        if (this.properties.getBaseUri() == null || this.properties.getBaseUri().isEmpty()) {
            return this.dnsConfigEndpointProvider;
        } else {
            return this.fixedEndpointProvider;
        }
    }
}
