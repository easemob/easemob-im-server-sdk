package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.ErrorMapper;
import org.apache.logging.log4j.util.Strings;
import reactor.netty.http.client.HttpClient;

public class DefaultEndpointProviderFactory implements EndpointProviderFactory {

    private final EMProperties properties;

    private final DnsConfigEndpointProvider dnsConfigEndpointProvider;

    private final FixedEndpointProvider fixedEndpointProvider;

    public DefaultEndpointProviderFactory(EMProperties properties, Codec codec, HttpClient httpClient, ErrorMapper errorMapper) {
        this.properties = properties;
        this.dnsConfigEndpointProvider = new DnsConfigEndpointProvider(this.properties, codec, httpClient, errorMapper);
        this.fixedEndpointProvider = new FixedEndpointProvider(this.properties);
    }

    @Override
    public EndpointProvider createEndpointProvider() {
        final String baseUri = this.properties.getBaseUri();
        if (Strings.isBlank(baseUri)) {
            return this.dnsConfigEndpointProvider;
        } else {
            return this.fixedEndpointProvider;
        }
    }
}
