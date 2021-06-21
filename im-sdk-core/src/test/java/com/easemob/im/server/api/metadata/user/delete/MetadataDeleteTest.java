package com.easemob.im.server.api.metadata.user.delete;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Throwables;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class MetadataDeleteTest extends AbstractApiTest {
    MetadataDelete metadataDelete = new MetadataDelete(this.context);

    public MetadataDeleteTest() {
        this.server.addHandler("DELETE /easemob/demo/metadata/user/bob", this::handleMetadataDeleteSuccess);
        this.server.addHandler("DELETE /easemob/demo/metadata/user/cat", this::handleMetadataDeleteNull);
    }

    @Test
    public void testMetadataDeleteFromUserSuccess() {
        boolean deletionSuccess = assertDoesNotThrow(() -> this.metadataDelete.fromUser("bob").block(Duration.ofSeconds(3)));
        assertTrue(deletionSuccess);
    }

    @Test
    public void testMetadataDeleteFromUserNull() {
        Throwable throwable = assertThrows(Throwable.class, () -> this.metadataDelete.fromUser("cat").block(Duration.ofSeconds(3)));
        assertEquals(EMInvalidArgumentException.class, Throwables.getRootCause(throwable).getClass());
    }

    public JsonNode handleMetadataDeleteSuccess(JsonNode req) {
        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.put("data", true);
        return jsonRsp;
    }

    public JsonNode handleMetadataDeleteNull(JsonNode req) {
        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        return jsonRsp;
    }
}
