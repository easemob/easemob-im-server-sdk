package com.easemob.im.server.api.user.status;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserStatusTest extends AbstractApiTest {

    private UserStatus userStatus;

    public UserStatusTest() {
        this.server.addHandler("GET /easemob/demo/users/alice/status", this::handleUserStatusRequest);
        this.userStatus = new UserStatus(this.context);
    }

    @Test
    public void testUserStatusSingle() {
        boolean aliceIsOnline = this.userStatus.isUserOnline("alice").block(Duration.ofSeconds(3));
        assertEquals(true, aliceIsOnline);
    }

    private JsonNode handleUserStatusRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("alice", "online");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

}