package com.easemob.im.server.api.block.room.msg.list;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMBlock;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListUsersBlockedSendMsgToRoomTest extends AbstractApiTest {
    ListUsersBlockedSendMsgToRoomTest() {
        this.server.addHandler("GET /easemob/demo/chatrooms/r1/mute", this::handleListUsersBlockedSendMsgToRoom);
    }

    private JsonNode handleListUsersBlockedSendMsgToRoom(JsonNode jsonNode) {
        ArrayNode blockedUsers = this.objectMapper.createArrayNode();

        ObjectNode user1 = this.objectMapper.createObjectNode();
        user1.put("expire", 1000000);
        user1.put("user", "rabbit");
        blockedUsers.add(user1);

        ObjectNode user2 = this.objectMapper.createObjectNode();
        user2.put("expire", 1000000);
        user2.put("user", "madhat");
        blockedUsers.add(user2);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", blockedUsers);

        return rsp;
    }

    @Test
    void testListUsersBlockedSendMsgToRoom() {
        List<EMBlock> blocks = ListUsersBlockedSendMsgToRoom.all(this.context, "r1").collectList().block(Duration.ofSeconds(3));
        assertEquals(2, blocks.size());
        assertEquals(new EMBlock("rabbit", Instant.ofEpochMilli(1000000)), blocks.get(0));
        assertEquals(new EMBlock("madhat", Instant.ofEpochMilli(1000000)), blocks.get(1));
    }
}