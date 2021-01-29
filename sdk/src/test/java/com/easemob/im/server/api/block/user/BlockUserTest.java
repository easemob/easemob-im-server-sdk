package com.easemob.im.server.api.block.user;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class BlockUserTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    private MockingHttpServer server = MockingHttpServer.builder()
        .addHandler("POST /easemob/demo/users/alice/blocks/users", this::handleBlockUser)
        .addHandler("DELETE /easemob/demo/users/alice/blocks/users", this::handleUnblockUser)
        .addHandler("GET /easemob/demo/users/alice/blocks/users", this::handleGetUserBlockedByUser)
        .build();

    private EMProperties properties = EMProperties.builder()
        .baseUri(this.server.uri())
        .appkey("easemob#demo")
        .clientId("clientId")
        .clientSecret("clientSecret")
        .build();

    private MockingContext context = new MockingContext(properties);

    @Test
    public void testBlockUser() {
        List<String> block = new ArrayList<>();
        block.add("queen");
        block.add("madhat");
        block.add("rabbit");

        BlockUser blockUser = new BlockUser(this.context, block);
        assertDoesNotThrow(() -> blockUser.fromSendingMessagesToUser("alice").block(Duration.ofSeconds(3)));
    }
    @Test
    public void testUnBlockUser() {
        List<String> unblock = new ArrayList<>();
        unblock.add("queen");
        unblock.add("madhat");
        unblock.add("rabbit");

        BlockUser blockUser = new BlockUser(this.context, unblock);
        assertDoesNotThrow(() -> blockUser.fromSendingMessagesToUser("alice").block(Duration.ofSeconds(3)));
    }

    @Test
    public void testGetUserBlocked() {
        GetUserBlocked getUserBlocked = new GetUserBlocked(this.context);
        List<String> blocked = getUserBlocked.fromSendingMessagesTo("alice").collectList().block(Duration.ofSeconds(3));
        assertEquals(3, blocked.size());
        assertTrue(blocked.contains("queen"));
        assertTrue(blocked.contains("madhat"));
        assertTrue(blocked.contains("rabbit"));

    }

    private JsonNode handleGetUserBlockedByUser(JsonNode jsonNode) {
        JsonNode usernames = this.objectMapper.createArrayNode().add("queen").add("madhat").add("rabbit");
        JsonNode response = this.objectMapper.createObjectNode().set("data", usernames);
        return response;
    }

    private JsonNode handleUnblockUser(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

    private JsonNode handleBlockUser(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }
}