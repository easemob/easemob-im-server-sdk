package com.easemob.im.server.api.room.admin.promote;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class PromoteRoomAdminTest extends AbstractApiTest {

    PromoteRoomAdminTest() {
        this.server.addHandler("POST /easemob/demo/chatrooms/r1/admin", this::handleAddRoomAdmin);
    }

    private JsonNode handleAddRoomAdmin(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("result", true);
        data.put("newadmin", "rabbit");

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    @Test
    void testPromoteRoomAdmin() {
        assertDoesNotThrow(() -> PromoteRoomAdmin.single(this.context, "r1", "rabbit").block(Duration.ofSeconds(3)));
    }
}