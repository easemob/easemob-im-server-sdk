package com.easemob.im.server.api.room.admin.list;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListRoomAdminsTest extends AbstractApiTest {
    ListRoomAdminsTest() {
        this.server.addHandler("GET /easemob/demo/chatrooms/r1/admin", this::handleListRoomAdmins);
    }

    private JsonNode handleListRoomAdmins(JsonNode jsonNode) {
        ArrayNode admins = this.objectMapper.createArrayNode();
        admins.add("rabbit");
        admins.add("madhat");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", admins);
        return rsp;
    }

    @Test
    void testListRoomAdmins() {
        List<String> admins = ListRoomAdmins.all(this.context, "r1").collectList().block(Duration.ofSeconds(3));
        assertEquals(2, admins.size());
        assertEquals("rabbit", admins.get(0));
        assertEquals("madhat", admins.get(1));
    }

}