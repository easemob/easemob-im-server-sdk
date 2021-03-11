package com.easemob.im.server.api.chatrooms.member.remove;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class RemoveRoomMemberTest extends AbstractApiTest {

    RemoveRoomMemberTest() {
        this.server.addHandler("DELETE /easemob/demo/chatrooms/r1/users/alice", this::handleRemoveMemberRequest);
    }

    private JsonNode handleRemoveMemberRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("result", true);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

    @Test
    void testRemoveRoomMember() {
        assertDoesNotThrow(() -> RemoveRoomMember.single(this.context, "r1", "alice").block(Duration.ofSeconds(3)));
    }
}