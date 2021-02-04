package com.easemob.im.server.api.user.password;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.easemob.im.server.exception.EMNotFoundException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class UserPasswordTest {
    private ObjectMapper objectMapper = new ObjectMapper()
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    private MockingHttpServer server = MockingHttpServer.builder()
        .addHandler("PUT /easemob/demo/users/username/password", this::handleUserPasswordReset)
        .build();

    private EMProperties properties = EMProperties.builder()
        .baseUri(this.server.uri())
        .appkey("easemob#demo")
        .clientId("clientId")
        .clientSecret("clientSecret")
        .build();

    private MockingContext context = new MockingContext(properties);

    @Test
    public void testUserPasswordReset() {
        assertDoesNotThrow(() -> {
            UserPassword.reset(this.context, "username", "password").block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testNonRegisteredUserPasswordReset() {
        assertThrows(EMNotFoundException.class, () -> {
            UserPassword.reset(this.context, "power", "password").block(Duration.ofSeconds(3));
        });
    }

    private JsonNode handleUserPasswordReset(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }
}