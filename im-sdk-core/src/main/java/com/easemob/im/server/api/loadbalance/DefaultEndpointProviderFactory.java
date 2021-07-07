package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.ErrorMapper;
import org.apache.logging.log4j.util.Strings;
import reactor.netty.http.client.HttpClient;

public class DefaultEndpointProviderFactory implements EndpointProviderFactory {

    private final EMProperties properties;
    private final Codec codec;
    private final HttpClient httpClient;
    private final ErrorMapper errorMapper;

    public DefaultEndpointProviderFactory(EMProperties properties, Codec codec, HttpClient httpClient, ErrorMapper errorMapper) {
        this.properties = properties;
        this.codec = codec;
        this.httpClient = httpClient;
        this.errorMapper = errorMapper;
    }

    @Override
    public EndpointProvider create() {
        final String baseUri = properties.getBaseUri();
        if (Strings.isBlank(baseUri)) {
            return new DnsConfigEndpointProvider(properties, codec, httpClient, errorMapper);
        } else {
            return new FixedEndpointProvider(properties);
        }
    }
}
