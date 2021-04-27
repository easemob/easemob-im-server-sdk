package com.easemob.im.server.api.dnsconfig;

import com.easemob.im.server.EMService;
import com.easemob.im.server.api.AbstractIT;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class DnsConfigIT extends AbstractIT {
    public DnsConfigIT() {
        super();
    }

    @Test
    public void testDnsConfig() {
        assertDoesNotThrow(() -> EMService.getCluster("easemob-demo#chatdemoui").block());
    }
}
