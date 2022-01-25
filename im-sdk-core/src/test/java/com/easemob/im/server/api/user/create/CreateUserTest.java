package com.easemob.im.server.api.user.create;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.EMNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.handler.codec.http.HttpResponseStatus;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CreateUserTest extends AbstractApiTest {

    private static final String DUMMY_USER_NAME = "dummy-user-name";
    private static final String DUMMY_USER_PASSWORD = "dummy-user-password";
    private static final String DUMMY_USER_UUID = "b2832810-e91f-11eb-901b-1d2efade27a3";
    private static final boolean DUMMY_USER_ACTIVATED = true;

    CreateUser createUser = new CreateUser(this.context);

    CreateUserTest() {
        this.server.addHandler("POST /easemob/demo/users", this::handleUserRegisterRequest);
    }

    @Test
    public void testUserRegisterSingle() {
        assertDoesNotThrow(
                () -> this.createUser.single(DUMMY_USER_NAME, DUMMY_USER_PASSWORD).block(Utilities.UT_TIMEOUT));
    }

    @Test
    public void testReturnExceptionErrorCode() {
        //移除钩子函数 抛出EMNotFoundException异常
        this.server.removeHandler("POST /easemob/demo/users");
        EMException emException = assertThrows(EMNotFoundException.class, () -> this.createUser.single(DUMMY_USER_NAME, DUMMY_USER_PASSWORD).block(Utilities.UT_TIMEOUT));
        assertNotNull(emException.getErrorCode());
        assertEquals(emException.getErrorCode(), HttpResponseStatus.NOT_FOUND.code());
    }

    private JsonNode handleUserRegisterRequest(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", DUMMY_USER_NAME);
        user.put("uuid", DUMMY_USER_UUID);
        user.put("activated", DUMMY_USER_ACTIVATED);

        ArrayNode users = this.objectMapper.createArrayNode();
        users.add(user);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);

        return rsp;
    }

}
