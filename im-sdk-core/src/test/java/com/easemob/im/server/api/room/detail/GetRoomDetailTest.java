package com.easemob.im.server.api.room.detail;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMRoom;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GetRoomDetailTest extends AbstractApiTest {

    private GetRoomDetail getRoomDetail;

    GetRoomDetailTest() {
        this.server.addHandler("GET /easemob/demo/chatrooms/r1", this::handleGetRoomDetailRequest);
        this.getRoomDetail = new GetRoomDetail(this.context);
    }

    private JsonNode handleGetRoomDetailRequest(JsonNode jsonNode) {
        ObjectNode room1 = this.objectMapper.createObjectNode();
        room1.put("id", "r1");
        room1.put("name", "room1");
        room1.put("description", "The first room.");
        room1.put("owner", "alice");
        room1.put("membersonly", true);
        room1.put("allowinvites", false);
        room1.put("maxusers", 200);
        ArrayNode rooms = this.objectMapper.createArrayNode();
        rooms.add(room1);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", rooms);
        return rsp;
    }

    @Test
    void testGetRoomDetail() {
        EMRoom r1 = this.getRoomDetail.byId("r1").block(Utilities.UT_TIMEOUT);
        assertEquals("r1", r1.id());
        assertEquals("room1", r1.name());
        assertEquals("The first room.", r1.description());
        assertEquals("alice", r1.owner());
        assertEquals(200, r1.maxMembers());
    }

}