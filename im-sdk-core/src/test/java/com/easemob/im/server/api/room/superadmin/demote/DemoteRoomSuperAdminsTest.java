package com.easemob.im.server.api.room.superadmin.demote;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class DemoteRoomSuperAdminsTest extends AbstractApiTest {
    DemoteRoomSuperAdminsTest() {
        this.server.addHandler("DELETE /easemob/demo/chatrooms/super_admin/rabbit", this::handleDemoteRoomSuperAdmin);
    }

        private JsonNode handleDemoteRoomSuperAdmin(JsonNode jsonNode) {
            ObjectNode data = this.objectMapper.createObjectNode();
            data.put("newSuperAdmin", "rabbit");
            data.put("resource", "");
            ObjectNode rsp = this.objectMapper.createObjectNode();
            rsp.set("data", data);
            return rsp;
        }

    @Test
    void testDemoteRoomSuperAdmin() { assertDoesNotThrow(() -> DemoteRoomSuperAdmin.singnle(this.context, "rabbit").block(Duration.ofSeconds(3)));}
}
