package com.easemob.im.server.api.room.admin.demote;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DemoteRoomAdminTest extends AbstractApiTest {
    DemoteRoomAdminTest() {
        this.server.addHandler("DELETE /easemob/demo/chatrooms/r1/admin/rabbit", this::handleDemoteRoomAdmin);
    }

    private JsonNode handleDemoteRoomAdmin(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("result", true);
        data.put("oldadmin", "rabbit");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

    @Test
    void testDemoteRoomAdmin() {
        assertDoesNotThrow(() -> DemoteRoomAdmin.single(this.context, "r1", "rabbit"));
    }
}