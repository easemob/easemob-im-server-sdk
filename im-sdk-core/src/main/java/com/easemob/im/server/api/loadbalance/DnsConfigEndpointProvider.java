package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.ErrorMapper;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.util.List;

public class DnsConfigEndpointProvider implements EndpointProvider {

    private final EMProperties properties;

    private final Codec codec;

    private final HttpClient httpClient;

    private final ErrorMapper errorMapper;

    public DnsConfigEndpointProvider(EMProperties properties, Codec codec, HttpClient httpClient,
            ErrorMapper errorMapper) {
        this.properties = properties;
        this.codec = codec;
        this.httpClient = httpClient;
        this.errorMapper = errorMapper;
    }

    public Mono<List<Endpoint>> endpoints() {
        return this.httpClient.get()
                .uri(String.format("/easemob/server.json?app_key=%s",
                        this.properties.getAppkeyUrlEncoded()))
                .responseSingle((rsp, buf) -> this.errorMapper.apply(rsp).then(buf))
                .map(buf -> this.codec.decode(buf, GetDnsConfigResponse.class))
                .map(GetDnsConfigResponse::toEndpoints);
    }
}
