package com.easemob.im.server.api.block.room.msg.block;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.block.group.msg.BlockUserSendMsgToGroup;
import com.easemob.im.server.api.block.room.msg.list.ListUsersBlockedSendMsgToRoom;
import com.easemob.im.server.api.block.room.msg.unblock.UnblockUserSendMsgToRoom;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMBlock;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlockUserSendMsgToRoomTest extends AbstractApiTest {

    BlockUserSendMsgToRoomTest() {
        super();
        this.server.addHandler("GET /easemob/demo/chatrooms/1/mute", this::handleGetBlockedUserRequest);
        this.server.addHandler("POST /easemob/demo/chatrooms/1/mute", this::handleBlockUserRequestSuccess);
        this.server.addHandler("POST /easemob/demo/chatrooms/2/mute", this::handleBlockUserRequestFail);
        this.server.addHandler("DELETE /easemob/demo/chatrooms/1/mute/alice", req -> handleUnblockUserRequestSuccess(req, "alice"));
        this.server.addHandler("DELETE /easemob/demo/chatrooms/2/mute/alice", req -> handleUnblockUserRequestFail(req, "alice"));

    }

    private JsonNode handleUnblockUserRequestSuccess(JsonNode req, String username) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("result", true);
        user.put("user", username);

        ArrayNode res = this.objectMapper.createArrayNode();
        res.add(user);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", res);

        return rsp;
    }

    private JsonNode handleUnblockUserRequestFail(JsonNode req, String username) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("result", false);
        user.put("user", username);

        ArrayNode res = this.objectMapper.createArrayNode();
        res.add(user);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", res);

        return rsp;
    }

    private JsonNode handleBlockUserRequestSuccess(JsonNode req) {
        ArrayNode res = this.objectMapper.createArrayNode();

        ObjectNode alice = this.objectMapper.createObjectNode();
        alice.put("result", true);
        alice.put("user", "alice");

        res.add(alice);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", res);
        return rsp;
    }

    private JsonNode handleBlockUserRequestFail(JsonNode req) {
        ArrayNode res = this.objectMapper.createArrayNode();

        ObjectNode alice = this.objectMapper.createObjectNode();
        alice.put("result", false);
        alice.put("user", "alice");

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", res);
        return rsp;
    }

    private JsonNode handleGetBlockedUserRequest(JsonNode req) {
        ObjectNode alice = this.objectMapper.createObjectNode();
        alice.put("user", "alice");
        alice.put("expire", 1000000);

        ObjectNode rabbit = this.objectMapper.createObjectNode();
        rabbit.put("user", "rabbit");
        rabbit.put("expire", 1000000);

        ArrayNode res = this.objectMapper.createArrayNode();
        res.add(alice);
        res.add(rabbit);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", res);

        return rsp;
    }

    @Test
    void testGetBlockedUsers() {
        List<EMBlock> blocks = ListUsersBlockedSendMsgToRoom.all(this.context, "1").collectList().block(Duration.ofSeconds(3));
        assertEquals(2, blocks.size());
        assertTrue(blocks.contains(new EMBlock("alice", Instant.ofEpochMilli(1000000))));
        assertTrue(blocks.contains(new EMBlock("rabbit", Instant.ofEpochMilli(1000000))));
    }

    @Test
    void testBlockUserSuccess() {
        assertDoesNotThrow(() -> BlockUserSendMsgToRoom.single(this.context, "alice",  "1", null).block(Duration.ofSeconds(3)));
    }

    @Test
    void testBlockUserFail() {
        assertThrows(EMUnknownException.class, () -> BlockUserSendMsgToRoom.single(this.context, "alice",  "2", null).block(Duration.ofSeconds(3)));
    }

    @Test
    void testBlockUserMaxDuration() {
        assertDoesNotThrow(() -> BlockUserSendMsgToRoom.single(this.context, "alice",  "1", Duration.ofMillis(Integer.MAX_VALUE)).block(Duration.ofSeconds(3)));
    }


    @Test
    void testUnblockUserSuccess() {
        assertDoesNotThrow(() -> UnblockUserSendMsgToRoom.single(this.context, "alice",  "1").block(Duration.ofSeconds(3)));
    }

    @Test
    void testUnblockUserFail() {
        assertThrows(EMUnknownException.class, () -> UnblockUserSendMsgToRoom.single(this.context, "alice",  "2").block(Duration.ofSeconds(3)));
    }

}