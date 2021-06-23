package com.easemob.im.server.api.metadata.user.usage;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMMetadata;
import com.easemob.im.server.model.EMMetadataUsage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class MetadataUsageTest extends AbstractApiTest {
    MetadataUsage metadataUsage = new MetadataUsage(this.context);

    public MetadataUsageTest() {
        this.server.addHandler("GET /easemob/demo/metadata/user/capacity", this::handleMetadataUsage);
    }

    @Test
    public void testUsage() {
        EMMetadataUsage emMetadataUsage = assertDoesNotThrow(() -> this.metadataUsage.getUsage().block(Duration.ofSeconds(3)));
        assertEquals(1600, emMetadataUsage.getBytesUsed());
    }

    private JsonNode handleMetadataUsage(JsonNode req) {
        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.put("data", 1600);
        return jsonRsp;
    }
}
