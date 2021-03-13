package com.easemob.im.server.api.room.member.list;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListRoomMembersTest extends AbstractApiTest {
    ListRoomMembersTest() {
        this.server.addHandler("GET /easemob/demo/chatrooms/r1/users?limit=2", this::handleListMembersFirst);
        this.server.addHandler("GET /easemob/demo/chatrooms/r1/users?limit=2&cursor=1", this::handleListMembersLast);
    }

    private JsonNode handleListMembersLast(JsonNode jsonNode) {
        ArrayNode data = this.objectMapper.createArrayNode();

        ObjectNode member = this.objectMapper.createObjectNode();
        member.put("member", "madhat");
        data.add(member);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    private JsonNode handleListMembersFirst(JsonNode jsonNode) {
        ArrayNode data = this.objectMapper.createArrayNode();

        ObjectNode owner = this.objectMapper.createObjectNode();
        owner.put("owner", "alice");
        data.add(owner);

        ObjectNode member = this.objectMapper.createObjectNode();
        member.put("member", "rabbit");
        data.add(member);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        rsp.put("cursor", 1);

        return rsp;
    }

    @Test
    void testListRoomMembers() {
        List<String> members = ListRoomMembers.all(this.context, "r1", 2).collectList().block(Duration.ofSeconds(3));
        assertEquals(3, members.size());
        assertEquals("alice", members.get(0));
        assertEquals("rabbit", members.get(1));
        assertEquals("madhat", members.get(2));
    }
}