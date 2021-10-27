package com.easemob.im.server.api.user.create;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.EMNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.junit.jupiter.api.Test;

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

    @Test
    public void testReturnExceptionErrorCode() {
        //移除钩子函数 抛出EMNotFoundException异常
        this.server.removeHandler("POST /easemob/demo/users");
        EMException emException = assertThrows(EMNotFoundException.class, () -> this.createUser.single("username", "password").block(Utilities.UT_TIMEOUT));
        assertNotNull(emException.getErrorCode());
        assertEquals(emException.getErrorCode(), HttpResponseStatus.NOT_FOUND.code());
    }

    private JsonNode handleUserRegisterRequest(JsonNode req) {

        String username = req.get("username").asText();
        String password = req.get("password").asText();

        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.put("username", username);

        return jsonRsp;
    }

}
