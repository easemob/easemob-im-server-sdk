package com.easemob.im.server.api.dnsconfig;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class DnsConfigApiTest extends AbstractApiTest {
    public DnsConfigApiTest() {
        this.server.addHandler("GET /easemob/server.json?app_key=easemob%23demo", this::handleGetDnsConfig);
    }

    @Test
    public void testGetDnsConfig() {
        assertDoesNotThrow(() -> new DnsConfigApi(this.context.getHttpClient().block(), new DefaultErrorMapper(), this.codec).getCluster("easemob#demo").block(Duration.ofSeconds(3)));
    }

    private JsonNode handleGetDnsConfig(JsonNode jsonNode) {
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.put("file_version", "243");
        rsp.put("deploy_name", "magic");

        ArrayNode hosts = this.objectMapper.createArrayNode();

        ObjectNode host1 = this.objectMapper.createObjectNode();
        host1.put("protocol", "https");
        host1.put("domain", "test.easemob.com");
        host1.put("port", 443);
        hosts.add(host1);

        ObjectNode host2 = this.objectMapper.createObjectNode();
        host2.put("protocol", "http");
        host2.put("ip", "1.2.3.4");
        host2.put("port", 80);
        hosts.add(host2);

        ObjectNode rest = this.objectMapper.createObjectNode();
        rest.set("hosts", hosts);

        rsp.set("rest", rest);

        return rsp;
    }
}