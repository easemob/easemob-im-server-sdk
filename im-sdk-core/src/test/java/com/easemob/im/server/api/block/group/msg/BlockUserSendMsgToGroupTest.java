package com.easemob.im.server.api.block.group.msg;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMBlock;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import java.time.Duration;
import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BlockUserSendMsgToGroupTest extends AbstractApiTest {

    private BlockUserSendMsgToGroup blockUserSendMsgToGroup;

    BlockUserSendMsgToGroupTest() {
        super();
        this.server.addHandler("GET /easemob/demo/chatgroups/1/mute",
                this::handleGetBlockedUserRequest);
        this.server.addHandler("POST /easemob/demo/chatgroups/1/mute",
                this::handleBlockUserRequestSuccess);
        this.server.addHandler("POST /easemob/demo/chatgroups/2/mute",
                this::handleBlockUserRequestFail);
        this.server.addHandler("DELETE /easemob/demo/chatgroups/1/mute/alice",
                req -> handleUnblockUserRequestSuccess(req, "alice"));
        this.server.addHandler("DELETE /easemob/demo/chatgroups/2/mute/alice",
                req -> handleUnblockUserRequestFail(req, "alice"));
        this.blockUserSendMsgToGroup = new BlockUserSendMsgToGroup(this.context);
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
        List<EMBlock> blocks = this.blockUserSendMsgToGroup.getBlockedUsers("1").collectList()
                .block(Utilities.UT_TIMEOUT);
        assertEquals(2, blocks.size());
        assertTrue(blocks.contains(new EMBlock("alice", Instant.ofEpochMilli(1000000))));
        assertTrue(blocks.contains(new EMBlock("rabbit", Instant.ofEpochMilli(1000000))));
    }

    @Test
    void testBlockUserSuccess() {
        assertDoesNotThrow(() -> this.blockUserSendMsgToGroup.blockUser("alice", "1", null)
                .block(Utilities.UT_TIMEOUT));
    }

    @Test
    void testBlockUserFail() {
        assertThrows(EMUnknownException.class,
                () -> this.blockUserSendMsgToGroup.blockUser("alice", "2", null)
                        .block(Utilities.UT_TIMEOUT));
    }

    @Test
    void testBlockUserMaxDuration() {
        assertDoesNotThrow(() -> this.blockUserSendMsgToGroup
                .blockUser("alice", "1", Duration.ofMillis(Integer.MAX_VALUE))
                .block(Utilities.UT_TIMEOUT));
    }

    @Test
    void testUnblockUserSuccess() {
        assertDoesNotThrow(() -> this.blockUserSendMsgToGroup.unblockUser("alice", "1")
                .block(Utilities.UT_TIMEOUT));
    }

    @Test
    void testUnblockUserFail() {
        assertThrows(EMUnknownException.class,
                () -> this.blockUserSendMsgToGroup.unblockUser("alice", "2")
                        .block(Utilities.UT_TIMEOUT));
    }

}
