package com.easemob.im.server.api.dnsconfig;

import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.codec.JsonCodec;
import com.easemob.im.server.api.loadbalance.Endpoint;
import com.easemob.im.server.api.loadbalance.GetDnsConfigResponse;
import com.easemob.im.server.exception.EMUnsupportedEncodingException;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;


public class DnsConfigApi {

    private HttpClient httpClient;

    private DefaultErrorMapper errorMapper;

    private JsonCodec codec;

    public DnsConfigApi(HttpClient httpClient, DefaultErrorMapper errorMapper, JsonCodec codec) {
        this.httpClient = httpClient;
        this.errorMapper = errorMapper;
        this.codec = codec;
    }

    /**
     * 获取appkey所在的集群
     * @param appkey appkey
     * @return 返回domain
     */
    public Mono<String> getCluster(String appkey) {
        return get(appkey);
    }

    private Mono<String> get(String appkey) {
        return this.httpClient.baseUrl("http://rs.easemob.com")
                .get()
                .uri(String.format("/easemob/server.json?app_key=%s", getAppkeyUrlEncoded(appkey)))
                .responseSingle((rsp, buf) -> this.errorMapper.apply(rsp).then(buf))
                .map(buf -> this.codec.decode(buf, GetDnsConfigResponse.class))
                .map(GetDnsConfigResponse::toEndpoints)
                .map(DnsConfigApi::getDomain);
    }

    public static String getDomain(List<Endpoint> endpoints) {
        String domain = "";
        for (Endpoint endpoint : endpoints) {
            String host = endpoint.getHost();
            if (host.contains("easemob.com")) {
                domain = host;
                break;
            }
        }
        return domain;
    }

    private static String getAppkeyUrlEncoded(String appkey) {
        try {
            return URLEncoder.encode(appkey, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new EMUnsupportedEncodingException(e.getMessage());
        }
    }
}
