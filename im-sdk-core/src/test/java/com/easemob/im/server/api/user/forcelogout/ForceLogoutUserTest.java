package com.easemob.im.server.api.user.forcelogout;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.exception.EMInternalServerErrorException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import static org.junit.jupiter.api.Assertions.*;

class ForceLogoutUserTest extends AbstractApiTest {

    ForceLogoutUser forceLogoutUser = new ForceLogoutUser(this.context);

    ForceLogoutUserTest() {
        super();
        this.server.addHandler("GET /easemob/demo/users/alice/disconnect",
                this::handlDisconnectByUsernameSucc);
        this.server.addHandler("GET /easemob/demo/users/alice/disconnect/slippers",
                this::handlDisconnectByUsernameAndResourceFail);
    }

    @Test
    public void testForceLogoutByUsername() {
        assertDoesNotThrow(() -> {
            this.forceLogoutUser.byUsername("alice").block(Utilities.UT_TIMEOUT);
        });
    }

    @Test
    public void testForceLogoutByUsernameAndResource() {
        assertThrows(EMInternalServerErrorException.class, () -> {
            this.forceLogoutUser.byUsernameAndResource("alice", "slippers")
                    .block(Utilities.UT_TIMEOUT);
        });
    }

    private JsonNode handlDisconnectByUsernameSucc(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("result", true);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

    private JsonNode handlDisconnectByUsernameAndResourceFail(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("result", false);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }
}
