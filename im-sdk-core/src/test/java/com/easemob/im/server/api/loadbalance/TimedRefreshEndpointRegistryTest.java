package com.easemob.im.server.api.loadbalance;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.MockingEndpointProvider;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TimedRefreshEndpointRegistryTest extends AbstractApiTest {

    @Test
    public void testTimedRefreshEndpoint() {
        MockingEndpointProvider provider = new MockingEndpointProvider();
        TimedRefreshEndpointRegistry endpointRegistry =
                new TimedRefreshEndpointRegistry(provider, Duration.ofSeconds(10));
        List<Endpoint> endpointList = endpointRegistry.endpoints().block(Utilities.UT_TIMEOUT);
        assertEquals(2, endpointList.size());
    }
}
