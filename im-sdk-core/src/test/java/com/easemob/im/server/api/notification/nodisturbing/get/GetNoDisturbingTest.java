package com.easemob.im.server.api.notification.nodisturbing.get;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMNotificationNoDisturbing;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GetNoDisturbingTest extends AbstractApiTest {
    GetNoDisturbing noDisturbing = new GetNoDisturbing(this.context);

    GetNoDisturbingTest() {
        this.server.addHandler("GET /easemob/demo/users/bob", this::handleGetNoDisturbing);
    }

    @Test
    void testGetNoDisturbing() {
        EMNotificationNoDisturbing noDisturbing = assertDoesNotThrow(() -> this.noDisturbing.getNoDisturbing("bob").block(Duration.ofSeconds(3)));
        assertEquals(true, noDisturbing.getNoDisturbing());
        assertEquals(10, noDisturbing.getNoDisturbingStart());
        assertEquals(13, noDisturbing.getNoDisturbingEnd());
    }

    private JsonNode handleGetNoDisturbing(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "bob");
        user.put("notification_no_disturbing", true);
        user.put("notification_no_disturbing_start", 10);
        user.put("notification_no_disturbing_end", 13);

        ArrayNode users = this.objectMapper.createArrayNode();
        users.add(user);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);

        return rsp;
    }
}