package com.easemob.im.server.api.user.status;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.easemob.im.server.model.EMUserStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class UserStatusTest extends AbstractApiTest {

    public UserStatusTest() {
        this.server.addHandler("GET /easemob/demo/users/alice/status", this::handleUserStatusRequest);
        this.server.addHandler("POST /easemob/demo/users/batch/status", this::handleUserStatusBatch);
    }

    @Test
    public void testUserStatusSingle() {
        EMUserStatus aliceStatus = UserStatus.single(this.context, "alice").block(Duration.ofSeconds(3));
        assertEquals("alice", aliceStatus.getUsername());
        assertEquals(true, aliceStatus.isOnline());
    }

    @Test
    public void testUserStatusEach() {
        List<EMUserStatus> statusList = UserStatus.batch(this.context, Flux.just("alice", "queen")).collectList().block(Duration.ofSeconds(3));
        assertEquals(2, statusList.size());
        Map<String, Boolean> statusMap = statusList.stream().collect(Collectors.toMap(s -> s.getUsername(), s -> s.isOnline()));
        assertEquals(true, statusMap.get("alice"));
        assertEquals(false, statusMap.get("queen"));
    }

    private JsonNode handleUserStatusRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("alice", "online");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

    private JsonNode handleUserStatusBatch(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("alice", "online");
        data.put("queen", "offline");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }
}