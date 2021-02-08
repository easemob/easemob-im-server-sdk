package com.easemob.im.server.api.block;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.block.user.Login;
import com.easemob.im.server.api.block.user.SendMsgToUser;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class BlockSendMsgTest extends AbstractApiTest {

    public BlockSendMsgTest() {
        super();
        this.server.addHandler("POST /easemob/demo/users/alice/blocks/users", this::handleUserBlockFromSendMsgRequest);
        this.server.addHandler("DELETE /easemob/demo/users/alice/blocks/users", this::handleUserUnblockFromSendMsgRequest);
        this.server.addHandler("GET /easemob/demo/users/alice/blocks/users", this::handleGetUserBlockedFromSendMsgRequest);
        this.server.addHandler("POST /easemob/demo/users/alice/deactivate", this::handleUserBlockFromLoginRequest);
        this.server.addHandler("POST /easemob/demo/users/alice/activate", this::handleUserUnblockFromLoginRequest);
    }

    @Test
    public void testBlockUserFromSendMsg() {
        List<String> blockUsers = new ArrayList<>();
        blockUsers.add("queen");
        blockUsers.add("madhat");
        blockUsers.add("rabbit");

        assertDoesNotThrow(() -> {
            SendMsgToUser.blockUsers(this.context, blockUsers, "alice").block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testUnBlockUserFromSendMsg() {
        List<String> unblockUsers = new ArrayList<>();
        unblockUsers.add("queen");
        unblockUsers.add("madhat");
        unblockUsers.add("rabbit");

        assertDoesNotThrow(() -> {
            SendMsgToUser.unblockUsers(this.context, unblockUsers, "alice").block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testGetUserBlockedFromSendMsg() {
        Set<String> blockedUsers = SendMsgToUser.getUsersBlocked(this.context, "alice").collect(Collectors.toSet()).block(Duration.ofSeconds(3));
        assertTrue(blockedUsers.contains("queen"));
        assertTrue(blockedUsers.contains("madhat"));
        assertTrue(blockedUsers.contains("rabbit"));
    }

    @Test
    public void testBlockUserFromLogin() {
        assertDoesNotThrow(() -> {
            Login.blockUser(context, "alice").block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testUnblockUserFromLogin() {
        assertDoesNotThrow(() -> {
            Login.unblockUser(context, "alice").block(Duration.ofSeconds(3));
        });
    }

    private JsonNode handleGetUserBlockedFromSendMsgRequest(JsonNode jsonNode) {
        JsonNode usernames = this.objectMapper.createArrayNode().add("queen").add("madhat").add("rabbit");
        JsonNode response = this.objectMapper.createObjectNode().set("data", usernames);
        return response;
    }

    private JsonNode handleUserUnblockFromSendMsgRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

    private JsonNode handleUserBlockFromSendMsgRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

    private JsonNode handleUserBlockFromLoginRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

    private JsonNode handleUserUnblockFromLoginRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }
}