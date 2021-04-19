package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.AbstractApiTest;
import org.junit.jupiter.api.Test;
import reactor.netty.http.client.HttpClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class DomainProviderTest extends AbstractApiTest {
    DomainProviderTest() {
        this.properties = EMProperties.builder()
                .setDomain("test.easemob.com")
                .setAppkey("easemob#demo")
                .setClientId("clientId")
                .setClientSecret("ClientSecret")
                .build();
    }

    @Test
    void testGetDnsConfig() {
        HttpClient httpClient = HttpClient.newConnection().baseUrl(this.server.uri());
        DnsConfigEndpointProvider provider = new DnsConfigEndpointProvider(this.properties, this.context.getCodec(), httpClient, this.context.getErrorMapper());
        List<Endpoint> endpoints = provider.endpoints().block();
        assertEquals(1, endpoints.size());
        assertEquals(new Endpoint("http", "test.easemob.com", 80), endpoints.get(0));
    }
}