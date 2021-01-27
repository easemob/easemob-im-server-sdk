package com.easemob.im.server.api.user;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.easemob.im.server.api.user.register.UserRegisterRequestV1;
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

    private MockingContext context = new MockingContext(properties);

    @Test
    public void testUserRegisterSingle() {
        UserRegister userRegister = new UserRegister(this.context);
        EMUser user = userRegister.single(new UserRegisterRequestV1("username", "password")).block(Duration.ofSeconds(3));
        assertEquals("username", user.getUsername());
    }

    @Test
    public void testUserRegisterTwice() {
        UserRegister userRegister = new UserRegister(this.context);
        UserRegisterRequestV1 userRegisterRequest1 = new UserRegisterRequestV1("username1", "password1");
        UserRegisterRequestV1 userRegisterRequest2 = new UserRegisterRequestV1("username2", "password2");
        List<EMUser> users = userRegister.each(Flux.just(userRegisterRequest1, userRegisterRequest2)).collectList().block(Duration.ofSeconds(3));
        assertEquals(2, users.size());
        assertEquals("username1", users.get(0).getUsername());
        assertEquals("username2", users.get(1).getUsername());
    }

    @Test
    public void testUserRegisterEmpty() {
        UserRegister userRegister = new UserRegister(this.context);
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