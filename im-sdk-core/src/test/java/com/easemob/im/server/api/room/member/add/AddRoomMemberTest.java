package com.easemob.im.server.api.room.member.add;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import static org.junit.jupiter.api.Assertions.*;

class AddRoomMemberTest extends AbstractApiTest {

    private AddRoomMember addRoomMember;

    AddRoomMemberTest() {
        this.server.addHandler("POST /easemob/demo/chatrooms/r1/users/alice",
                this::handleAddRoomMember);
        this.addRoomMember = new AddRoomMember(this.context);
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
        assertDoesNotThrow(
                () -> this.addRoomMember.single("r1", "alice").block(Utilities.UT_TIMEOUT));
    }
}
