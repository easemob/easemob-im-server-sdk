package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Codec;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
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
                .responseSingle((rsp, buf) -> {
                    return buf.switchIfEmpty(
                                    Mono.error(new EMUnknownException("response is null")))
                            .flatMap(byteBuf -> {
                                ErrorMapper mapper = new DefaultErrorMapper();
                                mapper.statusCode(rsp);
                                mapper.checkError(byteBuf);
                                return Mono.just(byteBuf);
                            });
                })
                .map(buf -> {
                    GetDnsConfigResponse response =
                            this.codec.decode(buf, GetDnsConfigResponse.class);
                    buf.release();
                    return response;
                })
                .map(GetDnsConfigResponse::toEndpoints);
    }
}
