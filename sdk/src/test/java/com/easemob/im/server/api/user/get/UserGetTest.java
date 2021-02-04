package com.easemob.im.server.api.user.get;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.easemob.im.server.exception.EMInvalidArgumentException;
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

public class UserGetTest {

    private ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private MockingHttpServer server = MockingHttpServer.builder()
        .addHandler("GET /easemob/demo/users/username", this::handleUserGetSingle)
        .addHandler("GET /easemob/demo/users?limit=100", this::handleUserGet100)
        .addHandler("GET /easemob/demo/users?limit=100&cursor=cursor-0-99", this::handleUserGet100Continued)
        .addHandler("GET /easemob/demo/users?limit=100&cursor=cursor-100-199", this::handleUserGet100Last)
        .addHandler("GET /easemob/demo/users?limit=200", this::handleUserGet200)
        .addHandler("GET /easemob/demo/users?limit=200&cursor=cursor-0-199", this::handleUserGet200Continued)
        .build();

    private EMProperties properties = EMProperties.builder()
        .baseUri(this.server.uri())
        .appkey("easemob#demo")
        .clientId("clientId")
        .clientSecret("clientSecret")
        .build();

    private MockingContext context = new MockingContext(properties);

    @Test
    public void testUserGetSingle() {
        EMUser user = UserGet.single(this.context, "username").block(Duration.ofSeconds(3));
        assertEquals("username", user.getUsername());
    }

    @Test
    public void testUserGetAll100EachTime() {
        List<EMUser> users = UserGet.all(this.context, 100)
            .collectList().block(Duration.ofSeconds(3));
        assertEquals(300, users.size());
        for (int i = 0; i < 300; i++) {
            assertEquals("username", users.get(i).getUsername());
        }
    }

    @Test
    public void testUserGetAll200EachTime() {
        List<EMUser> users = UserGet.all(this.context, 200)
            .collectList().block(Duration.ofSeconds(3));
        assertEquals(300, users.size());
        for (int i = 0; i < 300; i++) {
            assertEquals("username", users.get(i).getUsername());
        }
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

    private JsonNode handleUserGet100(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "username");

        ArrayNode users = this.objectMapper.createArrayNode();
        for (int i = 0; i < 100; i++) {
            users.add(user);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);
        rsp.put("cursor", "cursor-0-99");

        return rsp;
    }

    private JsonNode handleUserGet100Continued(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "username");

        ArrayNode users = this.objectMapper.createArrayNode();
        for (int i = 0; i < 100; i++) {
            users.add(user);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);
        rsp.put("cursor", "cursor-100-199");

        return rsp;
    }

    private JsonNode handleUserGet100Last(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "username");

        ArrayNode users = this.objectMapper.createArrayNode();
        for (int i = 0; i < 100; i++) {
            users.add(user);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);

        return rsp;
    }

    private JsonNode handleUserGet200(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "username");

        ArrayNode users = this.objectMapper.createArrayNode();
        for (int i = 0; i < 200; i++) {
            users.add(user);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);
        rsp.put("cursor", "cursor-0-199");

        return rsp;
    }

    private JsonNode handleUserGet200Continued(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "username");

        ArrayNode users = this.objectMapper.createArrayNode();
        for (int i = 0; i < 100; i++) {
            users.add(user);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);

        return rsp;
    }
}