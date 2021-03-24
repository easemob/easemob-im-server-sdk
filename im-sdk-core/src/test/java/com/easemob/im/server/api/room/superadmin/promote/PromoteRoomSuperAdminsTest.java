package com.easemob.im.server.api.room.superadmin.promote;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class PromoteRoomSuperAdminsTest extends AbstractApiTest {

    private PromoteRoomSuperAdmin promoteRoomSuperAdmin;

    PromoteRoomSuperAdminsTest() {
        this.server.addHandler("POST /easemob/demo/chatrooms/super_admin", this::handleAddRoomSuperAdmin);
        this.promoteRoomSuperAdmin = new PromoteRoomSuperAdmin(this.context);
    }

    private JsonNode handleAddRoomSuperAdmin(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("result", "success");
        data.put("resource", "");

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    @Test
    void testPromoteRoomSuperAdmin() {
        assertDoesNotThrow(() -> this.promoteRoomSuperAdmin.single("rabbit").block(Duration.ofSeconds(3)));
    }
}
