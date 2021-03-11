package com.easemob.im.server.api.user.get;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.easemob.im.server.api.user.list.UserList;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserGetTest extends AbstractApiTest {

    public UserGetTest() {
        this.server.addHandler("GET /easemob/demo/users/username", this::handleUserGetSingle);
    }

    @Test
    public void testUserGetSingle() {
        EMUser user = UserGet.single(this.context, "username").block(Duration.ofSeconds(3));
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