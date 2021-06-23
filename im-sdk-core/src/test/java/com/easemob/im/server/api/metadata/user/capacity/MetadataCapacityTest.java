package com.easemob.im.server.api.metadata.user.capacity;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class MetadataCapacityTest extends AbstractApiTest {
    MetadataCapacity metadataCapacity = new MetadataCapacity(this.context);

    public MetadataCapacityTest() {
    }

    @Test
    public void testCapacity() {
        this.server.addHandler("GET /easemob/demo/metadata/user/capacity", this::handleMetadataCapacity);
        long capacity = assertDoesNotThrow(() -> this.metadataCapacity.getCapacity().block(Duration.ofSeconds(3)));
        assertEquals(1600, capacity);
    }

    private JsonNode handleMetadataCapacity(JsonNode req) {
        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.put("data", 1600);
        return jsonRsp;
    }
}
