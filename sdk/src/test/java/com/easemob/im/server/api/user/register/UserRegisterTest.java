package com.easemob.im.server.api.user.register;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class UserRegisterTest extends AbstractApiTest {

    public UserRegisterTest() {
        this.server.addHandler("POST /easemob/demo/users", this::handleUserRegisterRequest);
    }

    @Test
    public void testUserRegisterSingle() {
        EMUser user = UserRegister.single(this.context, "username", "password").block(Duration.ofSeconds(3));
        assertEquals("username", user.getUsername());
    }

    private JsonNode handleUserRegisterRequest(JsonNode req) {

        String username = req.get("username").asText();
        String password = req.get("password").asText();

        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.put("username", username);

        return jsonRsp;
    }

}