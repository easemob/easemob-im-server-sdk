package com.easemob.im.server.api.metadata.user.capacity;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Throwables;
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

    @Test
    public void testNullCapacity() {
        this.server.addHandler("GET /easemob/demo/metadata/user/capacity", this::handleNullMetadataCapacity);
        Throwable th = assertThrows(Throwable.class, () -> this.metadataCapacity.getCapacity().block(Duration.ofSeconds(3)));
        Throwable rootCause = Throwables.getRootCause(th);
        assertEquals(EMInvalidArgumentException.class, rootCause.getClass());
    }

    private JsonNode handleMetadataCapacity(JsonNode req) {
        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.put("data", 1600);
        return jsonRsp;
    }

    private JsonNode handleNullMetadataCapacity(JsonNode req) {
        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.put("dummyRandomKey", 1600);
        return jsonRsp;
    }
}
