package com.easemob.im.server.api.room.delete;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DeleteRoomTest extends AbstractApiTest {

    private DeleteRoom deleteRoom;

    DeleteRoomTest() {
        this.server.addHandler("DELETE /easemob/demo/chatrooms/r1", this::handleDeleteRoomRequest);
        this.deleteRoom = new DeleteRoom(this.context);
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
        assertDoesNotThrow(() -> this.deleteRoom.byId("r1").block(Utilities.UT_TIMEOUT));
    }
}
