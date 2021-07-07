package com.easemob.im.server.api.room.admin.promote;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class PromoteRoomAdminTest extends AbstractApiTest {

    private PromoteRoomAdmin promoteRoomAdmin;

    PromoteRoomAdminTest() {
        this.server.addHandler("POST /easemob/demo/chatrooms/r1/admin", this::handleAddRoomAdmin);
        this.promoteRoomAdmin = new PromoteRoomAdmin(this.context);
    }

    private JsonNode handleAddRoomAdmin(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("result", "success");
        data.put("newadmin", "rabbit");

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    @Test
    void testPromoteRoomAdmin() {
        assertDoesNotThrow(
                () -> this.promoteRoomAdmin.single("r1", "rabbit").block(Duration.ofSeconds(3)));
    }
}
