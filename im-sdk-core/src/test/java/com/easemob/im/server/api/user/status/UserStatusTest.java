package com.easemob.im.server.api.user.status;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.model.EMUserStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserStatusTest extends AbstractApiTest {

    private UserStatus userStatus;

    public UserStatusTest() {
        this.server
                .addHandler("GET /easemob/demo/users/alice/status", this::handleUserStatusRequest);

        this.server
                .addHandler("POST /easemob/demo/users/batch/status", this::handleUserStatusBatchRequest);
        this.userStatus = new UserStatus(this.context);
    }

    @Test
    public void testUserStatusSingle() {
        boolean aliceIsOnline = this.userStatus.isUserOnline("alice").block(Utilities.UT_TIMEOUT);
        assertEquals(true, aliceIsOnline);
    }

    @Test
    public void testUserStatusBatch() {
        List<EMUserStatus> userStatusList = this.userStatus.isUsersOnline(Arrays.asList("alice")).block(Utilities.UT_TIMEOUT);
        assertEquals(true, userStatusList.get(0).isOnline());
    }

    private JsonNode handleUserStatusRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("alice", "online");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

    private JsonNode handleUserStatusBatchRequest(JsonNode jsonNode) {
        ArrayNode data = this.objectMapper.createArrayNode();
        ObjectNode obj = this.objectMapper.createObjectNode();
        obj.put("alice", "online");
        data.add(obj);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

}
