package com.easemob.im.server.api.room.delete;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DeleteRoomTest extends AbstractApiTest {
    DeleteRoomTest() {
        this.server.addHandler("DELETE /easemob/demo/chatrooms/r1", this::handleDeleteRoomRequest);
    }

    private JsonNode handleDeleteRoomRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("id", "r1");
        data.put("success", true);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    @Test
    void testDeleteRoom() {
        assertDoesNotThrow(() -> DeleteRoom.byId(this.context, "r1").block(Duration.ofSeconds(3)));
    }
}