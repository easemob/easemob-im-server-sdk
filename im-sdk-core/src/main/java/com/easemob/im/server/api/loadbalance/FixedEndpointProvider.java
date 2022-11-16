package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import io.netty.util.internal.StringUtil;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Collections;
import java.util.List;

public class FixedEndpointProvider implements EndpointProvider {

    private final EMProperties properties;

    public FixedEndpointProvider(EMProperties properties) {
        this.properties = properties;
    }

    @Override
    public Mono<List<Endpoint>> endpoints() {
        String baseUri = this.properties.getBaseUri();
        URI uri = baseUriStringToURI(baseUri);
        return Mono.just(Collections
                .singletonList(new Endpoint(uri.getScheme(), uri.getHost(), uri.getPort())));
    }

    private URI baseUriStringToURI(String baseUri) {
        if (StringUtil.isNullOrEmpty(baseUri)) {
            throw new EMInvalidArgumentException("baseUri must not be null or blank");
        }
        try {
            return new URI(baseUri);
        } catch (URISyntaxException e) {
            throw new EMInvalidArgumentException(String.format("baseUri %s is illegal", baseUri));
        }
    }
}
