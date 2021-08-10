package com.easemob.im.server.api.user.password;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.easemob.im.server.exception.EMNotFoundException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import static org.junit.jupiter.api.Assertions.*;

public class UpdateUserPasswordTest extends AbstractApiTest {

    UpdateUserPassword updateUserPassword = new UpdateUserPassword(this.context);

    UpdateUserPasswordTest() {
        this.server.addHandler("PUT /easemob/demo/users/username/password",
                this::handleUserPasswordReset);
    }

    @Test
    public void testUserPasswordReset() {
        assertDoesNotThrow(() -> {
            this.updateUserPassword.update("username", "password").block(Utilities.UT_TIMEOUT);
        });
    }

    @Test
    public void testNonRegisteredUserPasswordReset() {
        assertThrows(EMNotFoundException.class, () -> {
            this.updateUserPassword.update("power", "password").block(Utilities.UT_TIMEOUT);
        });
    }

    private JsonNode handleUserPasswordReset(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }
}
