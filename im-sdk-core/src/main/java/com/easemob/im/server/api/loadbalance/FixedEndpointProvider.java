package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.regex.Pattern;

public class FixedEndpointProvider implements EndpointProvider {

    private static final Pattern BASE_URI = Pattern.compile("^(https|http)://([a-z0-9.:]{0,64})$");

    private final EMProperties properties;

    public FixedEndpointProvider(EMProperties properties) {
        this.properties = properties;
    }

    @Override
    public Mono<List<Endpoint>> endpoints() {
        String baseUri = this.properties.getBaseUri();
        validateBaserUri(baseUri);

        if (search(baseUri, ":") > 1) {
            String[] split = baseUri.split(":");

        }

        String[] splitBaseUri = baseUri.split("://");
        int port;
        if (splitBaseUri[0].equals("http")) {
            port = 80;
        } else {
            port = 443;
        }

        String host = splitBaseUri[1];
        if (host.contains(":")) {
            String[] splitHost = host.split(":");
            return Mono.just(Collections.singletonList(new Endpoint(splitBaseUri[0], splitHost[0], Integer.parseInt(splitHost[1]))));
        } else {
            return Mono.just(Collections.singletonList(new Endpoint(splitBaseUri[0], splitBaseUri[1], port)));
        }
    }

    public static int search(String source, String find) {
        int num = 0;
        while(source.contains(find)) {
            int i = source.indexOf(find);
            num++;
            source = source.substring(i + 1);
        }
        return num;
    }

    public static void validateBaserUri(String baseUri) {
        if (baseUri == null || baseUri.isEmpty()) {
            throw new EMInvalidArgumentException("baseUri must not be null or empty");
        }
        if (!BASE_URI.matcher(baseUri).matches()) {
            throw new EMInvalidArgumentException(String.format("baseUri '%s' should match regex %s", baseUri, BASE_URI.toString()));
        }
    }
}
