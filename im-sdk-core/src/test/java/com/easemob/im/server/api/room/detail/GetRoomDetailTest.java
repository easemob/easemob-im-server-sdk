package com.easemob.im.server.api.room.detail;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMRoom;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GetRoomDetailTest extends AbstractApiTest {

    GetRoomDetailTest() {
        this.server.addHandler("GET /easemob/demo/chatrooms/r1", this::handleGetRoomDetailRequest);
    }

    private JsonNode handleGetRoomDetailRequest(JsonNode jsonNode) {
        ObjectNode member1 = this.objectMapper.createObjectNode();
        member1.put("owner", "alice");
        ObjectNode member2 = this.objectMapper.createObjectNode();
        member2.put("member", "rabbit");
        ObjectNode member3 = this.objectMapper.createObjectNode();
        member3.put("member", "madhat");
        ArrayNode members = this.objectMapper.createArrayNode();
        members.add(member1);
        members.add(member2);
        members.add(member3);
        ObjectNode room1 = this.objectMapper.createObjectNode();
        room1.put("id", "r1");
        room1.put("name", "room1");
        room1.put("description", "The first room.");
        room1.put("owner", "alice");
        room1.put("membersonly", true);
        room1.put("allowinvites", false);
        room1.put("maxusers", 200);
        room1.set("affiliations", members);
        ArrayNode rooms = this.objectMapper.createArrayNode();
        rooms.add(room1);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", rooms);
        return rsp;
    }

    @Test
    void testGetRoomDetail() {
        EMRoom r1 = GetRoomDetail.byId(this.context, "r1").block(Duration.ofSeconds(3));
        assertEquals("r1", r1.id());
        assertEquals("room1", r1.name());
        assertEquals("The first room.", r1.description());
        assertEquals("alice", r1.owner());
        assertEquals(200, r1.maxMembers());
        List<String> members = r1.members();
        assertEquals(3, members.size());
        assertEquals("alice", members.get(0));
        assertEquals("rabbit", members.get(1));
        assertEquals("madhat", members.get(2));
    }

}