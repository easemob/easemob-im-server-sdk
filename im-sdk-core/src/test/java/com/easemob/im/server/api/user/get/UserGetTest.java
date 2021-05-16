package com.easemob.im.server.api.user.get;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserGetTest extends AbstractApiTest {

    private UserGet userGet;

    public UserGetTest() {
        this.server.addHandler("GET /easemob/demo/users/username", this::handleUserGetSingle);
        this.userGet = new UserGet(this.context);
    }

    @Test
    public void testUserGetSingle() {
        EMUser user = this.userGet.single("username").block(Duration.ofSeconds(3));
        assertEquals("username", user.getUsername());
    }

    private JsonNode handleUserGetSingle(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "username");

        ArrayNode users = this.objectMapper.createArrayNode();
        users.add(user);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);

        return rsp;
    }
}