package com.easemob.im.server.api.room.member.list;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class ListRoomMembersTest extends AbstractApiTest {

    private ListRoomMembers listRoomMembers;

    ListRoomMembersTest() {
        this.server.addHandler("GET /easemob/demo/chatrooms/r1/users?version=v3&limit=2",
                this::handleListMembersFirst);
        this.server.addHandler("GET /easemob/demo/chatrooms/r1/users?version=v3&limit=2&cursor=1",
                this::handleListMembersLast);
        this.listRoomMembers = new ListRoomMembers(this.context);
    }

    private JsonNode handleListMembersLast(JsonNode jsonNode) {
        ArrayNode data = this.objectMapper.createArrayNode();
        data.add("madhat");

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    private JsonNode handleListMembersFirst(JsonNode jsonNode) {
        ArrayNode data = this.objectMapper.createArrayNode();
        data.add("alice");
        data.add("rabbit");

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        rsp.put("cursor", 1);

        return rsp;
    }

    @Test
    void testListRoomMembers() {
        List<String> members =
                this.listRoomMembers.all("r1", 2).collectList().block(Utilities.UT_TIMEOUT);
        assertEquals(3, members.size());
        assertEquals("alice", members.get(0));
        assertEquals("rabbit", members.get(1));
        assertEquals("madhat", members.get(2));
    }
}
