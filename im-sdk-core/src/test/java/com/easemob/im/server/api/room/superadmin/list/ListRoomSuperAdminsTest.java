package com.easemob.im.server.api.room.superadmin.list;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListRoomSuperAdminsTest extends AbstractApiTest {
    ListRoomSuperAdminsTest() {
        this.server.addHandler("GET /easemob/demo/chatrooms/super_admin?pagenum=1&pagesize=10", this::handleListRoomSuperAdminsFirst);
        this.server.addHandler("GET /easemob/demo/chatrooms/super_admin?pagenum=2&pagesize=10", this::handleListRoomSuperAdminsLast);
    }

    private JsonNode handleListRoomSuperAdminsFirst(JsonNode jsonNode) {
        ArrayNode admins = this.objectMapper.createArrayNode();
        admins.add("rabbit");
        admins.add("madhat");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", admins);
        return rsp;
    }

    private JsonNode handleListRoomSuperAdminsLast(JsonNode jsonNode) {
        ArrayNode admins = this.objectMapper.createArrayNode();
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", admins);
        return rsp;
    }

    @Test
    void testListRoomSuperAdmins() {
        List<String> admins = ListRoomSuperAdmins.all(this.context, 10).collectList().block(Duration.ofSeconds(3));
        assertEquals(2, admins.size());
        assertEquals("rabbit", admins.get(0));
        assertEquals("madhat", admins.get(1));
    }
}