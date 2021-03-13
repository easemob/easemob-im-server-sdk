package com.easemob.im.server.api.room.member.add;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class AddRoomMemberTest extends AbstractApiTest {
    AddRoomMemberTest() {
        this.server.addHandler("POST /easemob/demo/chatrooms/r1/users/alice", this::handleAddRoomMember);
    }

    private JsonNode handleAddRoomMember(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("result", true);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

    @Test
    void testAddRoomMember() {
        assertDoesNotThrow(() -> AddRoomMember.single(this.context, "r1", "alice").block(Duration.ofSeconds(3)));
    }
}