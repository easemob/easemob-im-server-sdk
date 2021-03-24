package com.easemob.im.server.api.block;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.block.login.BlockUserLogin;
import com.easemob.im.server.api.block.user.SendMsgToUser;
import com.easemob.im.server.model.EMBlock;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class BlockSendMsgTest extends AbstractApiTest {

    private SendMsgToUser sendMsgToUser;
    
    private BlockUserLogin blockUserLogin;
    
    public BlockSendMsgTest() {
        super();
        this.server.addHandler("POST /easemob/demo/users/alice/blocks/users", this::handleUserBlockFromSendMsgRequest);
        this.server.addHandler("DELETE /easemob/demo/users/alice/blocks/users/rabbit", this::handleUserUnblockFromSendMsgRequest);
        this.server.addHandler("GET /easemob/demo/users/alice/blocks/users", this::handleGetUserBlockedFromSendMsgRequest);
        this.server.addHandler("POST /easemob/demo/users/alice/deactivate", this::handleUserBlockFromLoginRequest);
        this.server.addHandler("POST /easemob/demo/users/alice/activate", this::handleUserUnblockFromLoginRequest);
        sendMsgToUser = new SendMsgToUser(this.context);
        blockUserLogin = new BlockUserLogin(this.context);
    }

    @Test
    public void testBlockUserFromSendMsg() {
        assertDoesNotThrow(() -> {
            this.sendMsgToUser.blockUser("rabbit", "alice").block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testUnBlockUserFromSendMsg() {
        assertDoesNotThrow(() -> {
            this.sendMsgToUser.unblockUser("rabbit", "alice").block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testGetUserBlockedFromSendMsg() {
        Set<EMBlock> blockedUsers = this.sendMsgToUser.getUsersBlocked("alice").collect(Collectors.toSet()).block(Duration.ofSeconds(3));
        Set<String> blockedUsernames = blockedUsers.stream().map(EMBlock::getUsername).collect(Collectors.toSet());
        assertTrue(blockedUsernames.contains("queen"));
        assertTrue(blockedUsernames.contains("madhat"));
        assertTrue(blockedUsernames.contains("rabbit"));
    }

    @Test
    public void testBlockUserFromLogin() {
        assertDoesNotThrow(() -> {
            this.blockUserLogin.blockUser("alice").block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testUnblockUserFromLogin() {
        assertDoesNotThrow(() -> {
            this.blockUserLogin.unblockUser("alice").block(Duration.ofSeconds(3));
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