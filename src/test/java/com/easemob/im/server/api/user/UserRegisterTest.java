package com.easemob.im.server.api.user;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.easemob.im.server.api.user.register.UserRegisterRequest;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegisterTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockingHttpServer server = MockingHttpServer.builder()
        .addHandler("POST /easemob/demo/users", this::handleUserRegisterRequest)
        .build();

    private EMProperties properties = EMProperties.builder()
        .baseUri(this.server.uri())
        .withAppkey("easemob#demo")
        .withClientId("clientId")
        .withClientSecret("clientSecret")
        .build();

    @Test
    public void testUserRegisterSingle() {
        MockingContext context = new MockingContext(properties);
        UserRegister userRegister = new UserRegister(context);
        EMUser user = userRegister.single(new UserRegisterRequest("username", "password")).block(Duration.ofSeconds(3));
        assertEquals("username", user.getUsername());
    }

    @Test
    public void testUserRegisterTwice() {
        MockingContext context = new MockingContext(properties);
        UserRegister userRegister = new UserRegister(context);
        UserRegisterRequest userRegisterRequest1 = new UserRegisterRequest("username1", "password1");
        UserRegisterRequest userRegisterRequest2 = new UserRegisterRequest("username2", "password2");
        List<EMUser> users = userRegister.each(Flux.just(userRegisterRequest1, userRegisterRequest2)).collectList().block(Duration.ofSeconds(3));
        assertEquals(2, users.size());
        assertEquals("username1", users.get(0).getUsername());
        assertEquals("username2", users.get(1).getUsername());
    }

    @Test
    public void testUserRegisterEmpty() {
        MockingContext context = new MockingContext(properties);
        UserRegister userRegister = new UserRegister(context);
        List<EMUser> users = userRegister.each(Flux.empty()).collectList().block(Duration.ofSeconds(3));
        assertEquals(0, users.size());
    }

    private JsonNode handleUserRegisterRequest(JsonNode req) {

        String username = req.get("username").asText();
        String password = req.get("password").asText();

        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.put("username", username);

        return jsonRsp;
    }

}