package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.DefaultErrorMapper;
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
                .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.codec.decode(buf, GetDnsConfigResponse.class))
                .map(GetDnsConfigResponse::toEndpoints);
    }
}
