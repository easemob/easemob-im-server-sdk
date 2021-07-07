package com.easemob.im.server.api.user.unregister;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class DeleteUserTest extends AbstractApiTest {
    DeleteUser deleteUser = new DeleteUser(this.context);

    DeleteUserTest() {
        this.server.addHandler("DELETE /easemob/demo/users/username",
                this::handleUserUnregisterSingle);
        this.server.addHandler("DELETE /easemob/demo/users?limit=100",
                req -> handleUserUnregisterAll(req, 100, "cursor"));
        this.server.addHandler("DELETE /easemob/demo/users?limit=100&cursor=cursor",
                req -> handleUserUnregisterAll(req, 100, null));
    }

    @Test
    void testUserUnregisterSingle() {
        assertDoesNotThrow(() -> this.deleteUser.single("username").block(Duration.ofSeconds(3)));
    }

    @Test
    public void testUserUnregisterAll() {
        List<String> users = this.deleteUser.all(100).collectList().block(Duration.ofSeconds(3));
        assertEquals(200, users.size());
        for (int i = 0; i < 200; i++) {
            assertEquals("username", users.get(i));
        }
    }

    private JsonNode handleUserUnregisterSingle(JsonNode jsonNode) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "username");

        ArrayNode users = this.objectMapper.createArrayNode();
        users.add(user);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);

        return rsp;
    }

    private JsonNode handleUserUnregisterAll(JsonNode jsonNode, int limit, String cursor) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "username");

        ArrayNode users = this.objectMapper.createArrayNode();
        for (int i = 0; i < limit; i++) {
            users.add(user);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);
        if (cursor != null) {
            rsp.put("cursor", cursor);
        }

        return rsp;
    }
}
