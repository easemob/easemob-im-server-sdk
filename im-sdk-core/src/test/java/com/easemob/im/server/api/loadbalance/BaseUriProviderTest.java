package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.AbstractApiTest;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BaseUriProviderTest extends AbstractApiTest {
    BaseUriProviderTest() {
        this.properties = EMProperties.builder()
                .setBaseUri("http://test.easemob.com")
                .setAppkey("easemob#demo")
                .setClientId("clientId")
                .setClientSecret("ClientSecret")
                .build();
    }

    @Test
    void testGetDnsConfig() {
        FixedEndpointProvider provider = new FixedEndpointProvider(this.properties);
        List<Endpoint> endpoints = provider.endpoints().block();
        assertEquals(1, endpoints.size());
        assertEquals(new Endpoint("http", "test.easemob.com", -1), endpoints.get(0));
    }
}
