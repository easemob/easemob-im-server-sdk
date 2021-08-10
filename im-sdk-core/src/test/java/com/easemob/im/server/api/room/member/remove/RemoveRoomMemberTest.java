package com.easemob.im.server.api.room.member.remove;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import static org.junit.jupiter.api.Assertions.*;

class RemoveRoomMemberTest extends AbstractApiTest {

    private RemoveRoomMember removeRoomMember;

    RemoveRoomMemberTest() {
        this.server.addHandler("DELETE /easemob/demo/chatrooms/r1/users/alice",
                this::handleRemoveMemberRequest);
        removeRoomMember = new RemoveRoomMember(this.context);
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
        assertDoesNotThrow(
                () -> this.removeRoomMember.single("r1", "alice").block(Utilities.UT_TIMEOUT));
    }
}
