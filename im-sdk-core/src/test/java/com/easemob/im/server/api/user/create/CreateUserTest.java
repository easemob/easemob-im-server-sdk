package com.easemob.im.server.api.user.create;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserTest extends AbstractApiTest {

    CreateUser createUser = new CreateUser(this.context);

    CreateUserTest() {
        this.server.addHandler("POST /easemob/demo/users", this::handleUserRegisterRequest);
    }

    @Test
    public void testUserRegisterSingle() {
        assertDoesNotThrow(
                () -> this.createUser.single("username", "password").block(Utilities.UT_TIMEOUT));
    }

    private JsonNode handleUserRegisterRequest(JsonNode req) {

        String username = req.get("username").asText();
        String password = req.get("password").asText();

        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.put("username", username);

        return jsonRsp;
    }

}
