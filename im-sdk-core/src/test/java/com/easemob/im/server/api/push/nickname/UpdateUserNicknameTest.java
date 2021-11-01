package com.easemob.im.server.api.push.nickname;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.model.EMUser;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UpdateUserNicknameTest extends AbstractApiTest {

    private static final String DUMMY_USER_NAME = "dummy-user-name";
    private static final String DUMMY_USER_UUID = "b2832810-e91f-11eb-901b-1d2efade27a3";
    private static final String DUMMY_USER_NICKNAME = "dummy-user-nickname";
    private static final boolean DUMMY_USER_ACTIVATED = true;

    UpdateUserNickname updateUserNickname = new UpdateUserNickname(this.context);

    UpdateUserNicknameTest() {
        this.server.addHandler(String.format("PUT /easemob/demo/users/%s", DUMMY_USER_NAME),
                this::handleUpdateUserNickname);
    }

    @Test
    public void testUpdateUserNickname() {
        assertDoesNotThrow(() -> {
            this.updateUserNickname.update(DUMMY_USER_NAME, DUMMY_USER_NICKNAME)
                    .map(EMUser::getUsername)
                    .doOnNext(username -> assertEquals(username, DUMMY_USER_NAME))
                    .block(Utilities.UT_TIMEOUT);
        });
    }

    private JsonNode handleUpdateUserNickname(JsonNode req) {
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
