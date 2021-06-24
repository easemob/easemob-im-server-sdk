package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.MockingEndpointProvider;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimedRefreshEndpointRegistryTest extends AbstractApiTest {

    @Test
    public void testTimedRefreshEndpoint() {
        MockingEndpointProvider provider = new MockingEndpointProvider();
        TimedRefreshEndpointRegistry endpointRegistry = new TimedRefreshEndpointRegistry(provider, Duration.ofSeconds(10));
        List<Endpoint> endpointList = endpointRegistry.endpoints().block(Duration.ofSeconds(3));
        assertEquals(2, endpointList.size());
    }
}
