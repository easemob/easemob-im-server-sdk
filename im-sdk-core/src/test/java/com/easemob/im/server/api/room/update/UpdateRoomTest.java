package com.easemob.im.server.api.room.update;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.exception.EMUnknownException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class UpdateRoomTest extends AbstractApiTest {

    private UpdateRoom updateRoom;

    UpdateRoomTest() {
        this.server
                .addHandler("PUT /easemob/demo/chatrooms/r1", this::handleUpdateRoomRequestSuccess);
        this.server.addHandler("PUT /easemob/demo/chatrooms/r2", this::handleUpdateRoomRequestFail);
        this.updateRoom = new UpdateRoom(this.context);
    }

    private JsonNode handleUpdateRoomRequestSuccess(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        if (jsonNode.has("name")) {
            data.put("groupname", true);
        }
        if (jsonNode.has("description")) {
            data.put("description", true);
        }
        if (jsonNode.has("maxusers")) {
            data.put("maxusers", true);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    private JsonNode handleUpdateRoomRequestFail(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        if (jsonNode.has("name")) {
            data.put("groupname", false);
        }
        if (jsonNode.has("description")) {
            data.put("description", false);
        }
        if (jsonNode.has("maxusers")) {
            data.put("maxusers", false);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    @Test
    void testUpdateRoomSuccess() {
        assertDoesNotThrow(() -> this.updateRoom.byId("r1", req -> req.withName("room-one"))
                .block(Duration.ofSeconds(3)));
    }

    @Test
    void testUpdateRoomFail() {
        assertThrows(EMUnknownException.class,
                () -> this.updateRoom.byId("r2", req -> req.withMaxMembers(1000))
                        .block(Duration.ofSeconds(3)));
    }
}
