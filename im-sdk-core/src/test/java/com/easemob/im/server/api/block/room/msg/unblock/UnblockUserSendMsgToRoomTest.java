package com.easemob.im.server.api.block.room.msg.unblock;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class UnblockUserSendMsgToRoomTest extends AbstractApiTest {
    UnblockUserSendMsgToRoomTest() {
        this.server.addHandler("DELETE /easemob/demo/chatrooms/r1/mute/rabbit", this::handleUnblockUserSendMsgToRoom);
    }

    private JsonNode handleUnblockUserSendMsgToRoom(JsonNode jsonNode) {
        ArrayNode results = this.objectMapper.createArrayNode();

        ObjectNode result1 = this.objectMapper.createObjectNode();
        result1.put("result", true);
        result1.put("user", "rabbit");
        results.add(result1);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", results);

        return rsp;
    }

    @Test
    void testUnblockUserSendMsgToRoom() {
        assertDoesNotThrow(() -> UnblockUserSendMsgToRoom.single(this.context, "rabbit", "r1").block(Duration.ofSeconds(3)));
    }
}