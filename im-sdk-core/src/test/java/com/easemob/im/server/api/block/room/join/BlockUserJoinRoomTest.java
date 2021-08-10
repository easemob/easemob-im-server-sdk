package com.easemob.im.server.api.block.room.join;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.block.room.join.BlockUserJoinRoom;
import com.easemob.im.server.model.EMBlock;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class BlockUserJoinRoomTest extends AbstractApiTest {

    private BlockUserJoinRoom blockUserJoinRoom;

    BlockUserJoinRoomTest() {
        super();
        this.server.addHandler("GET /easemob/demo/chatrooms/1/blocks/users",
                this::handleGetBlockedUserRequest);
        this.server.addHandler("POST /easemob/demo/chatrooms/1/blocks/users/alice",
                req -> handleBlockUserRequest(req, "alice"));
        this.server.addHandler("DELETE /easemob/demo/chatrooms/1/blocks/users/alice",
                req -> handleUnblockUserRequest(req, "alice"));
        this.blockUserJoinRoom = new BlockUserJoinRoom(this.context);
    }

    private JsonNode handleGetBlockedUserRequest(JsonNode jsonNode) {
        ArrayNode res = this.objectMapper.createArrayNode();
        res.add("alice");
        res.add("rabbit");

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", res);
        return rsp;
    }

    private JsonNode handleBlockUserRequest(JsonNode req, String username) {
        ObjectNode res = this.objectMapper.createObjectNode();
        res.put("result", true);
        res.put("user", username);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", res);

        return rsp;
    }

    private JsonNode handleUnblockUserRequest(JsonNode req, String username) {
        ObjectNode res = this.objectMapper.createObjectNode();
        res.put("result", true);
        res.put("user", username);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", res);
        return rsp;
    }

    @Test
    public void testBlockUserJoinRoom() {
        assertDoesNotThrow(() -> {
            this.blockUserJoinRoom.blockUser("alice", "1").block(Utilities.UT_TIMEOUT);
        });
    }

    @Test
    public void testUnBlockUserJoinRoom() {
        assertDoesNotThrow(() -> {
            this.blockUserJoinRoom.unblockUser("alice", "1").block(Utilities.UT_TIMEOUT);
        });
    }

    @Test
    public void testGetUsersBlockedJoinRoom() {
        assertDoesNotThrow(() -> {
            List<EMBlock> blocks = this.blockUserJoinRoom.getBlockedUsers("1")
                    .collect(Collectors.toList())
                    .block(Utilities.UT_TIMEOUT);

            assertEquals(2, blocks.size());
            assertTrue(blocks.contains(new EMBlock("alice", null)));
            assertTrue(blocks.contains(new EMBlock("rabbit", null)));
        });
    }

}
