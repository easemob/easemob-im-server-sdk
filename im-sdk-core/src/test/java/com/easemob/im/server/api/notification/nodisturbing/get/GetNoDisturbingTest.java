package com.easemob.im.server.api.notification.nodisturbing.get;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.exception.EMJsonException;
import com.easemob.im.server.model.EMNotificationUserSetting;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import com.google.common.base.Throwables;

import static org.junit.jupiter.api.Assertions.*;

class GetNoDisturbingTest extends AbstractApiTest {
    GetNoDisturbing noDisturbing = new GetNoDisturbing(this.context);

    GetNoDisturbingTest() {
        this.server.addHandler("GET /easemob/demo/users/bob", this::handleGetNoDisturbingBob);
        this.server.addHandler("GET /easemob/demo/users/cat", this::handleGetNoDisturbingCat);
        this.server.addHandler("GET /easemob/demo/users/dog", this::handleGetNoDisturbingDog);
        this.server.addHandler("GET /easemob/demo/users/elephant", this::handleGetNoDisturbingElephant);
        this.server.addHandler("GET /easemob/demo/users/fly", this::handleGetNoDisturbingFly);
        this.server.addHandler("GET /easemob/demo/users/gorilla", this::handleGetNoDisturbingGorilla);
    }

    @Test
    void testBob() {
        EMNotificationUserSetting
                noDisturbing = assertDoesNotThrow(() -> this.noDisturbing.getNoDisturbing("bob").block(Duration.ofSeconds(3)));
        assertEquals(true, noDisturbing.getNoDisturb());
        assertEquals(10, noDisturbing.getStartHour());
        assertEquals(13, noDisturbing.getEndHour());
    }

    @Test
    void testCat() {
        EMNotificationUserSetting
                noDisturbing = assertDoesNotThrow(() -> this.noDisturbing.getNoDisturbing("cat").block(Duration.ofSeconds(3)));
        assertEquals(false, noDisturbing.getNoDisturb());
        assertEquals(18, noDisturbing.getStartHour());
        assertEquals(9, noDisturbing.getEndHour());
    }

    @Test
    void testDog() {
        EMJsonException emJsonException = assertThrows(EMJsonException.class, () -> this.noDisturbing.getNoDisturbing("dog").block(Duration.ofSeconds(3)));
        Throwable rootCause = Throwables.getRootCause(emJsonException);
        assertEquals(rootCause.getClass(), IllegalArgumentException.class);
    }

    @Test
    void testElephant() {
        EMNotificationUserSetting noDisturbing = assertDoesNotThrow(() -> this.noDisturbing.getNoDisturbing("elephant").block(Duration.ofSeconds(3)));
        assertEquals(true, noDisturbing.getNoDisturb());
        assertEquals(0, noDisturbing.getStartHour());
        assertEquals(9, noDisturbing.getEndHour());
    }

    @Test
    void testFly() {
        EMNotificationUserSetting
                noDisturbing = assertDoesNotThrow(() -> this.noDisturbing.getNoDisturbing("fly").block(Duration.ofSeconds(3)));
        assertEquals(true, noDisturbing.getNoDisturb());
        assertEquals(9, noDisturbing.getStartHour());
        assertEquals(23, noDisturbing.getEndHour());
    }

    @Test
    void testGorilla() {
        EMNotificationUserSetting noDisturbing = assertDoesNotThrow(() -> this.noDisturbing.getNoDisturbing("gorilla").block(Duration.ofSeconds(3)));
        assertEquals(true, noDisturbing.getNoDisturb());
        assertEquals(0, noDisturbing.getStartHour());
        assertEquals(23, noDisturbing.getEndHour());
    }

    private JsonNode handleGetNoDisturbingBob(JsonNode req) {
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

    private JsonNode handleGetNoDisturbingCat(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "cat");
        user.put("notification_no_disturbing_start", 18);
        user.put("notification_no_disturbing_end", 9);
        ArrayNode users = this.objectMapper.createArrayNode();
        users.add(user);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);
        return rsp;
    }

    private JsonNode handleGetNoDisturbingDog(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "dog");
        user.put("notification_no_disturbing_start", 24);
        user.put("notification_no_disturbing_end", 9);
        ArrayNode users = this.objectMapper.createArrayNode();
        users.add(user);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);
        return rsp;
    }

    private JsonNode handleGetNoDisturbingElephant(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("notification_no_disturbing", true);
        user.put("username", "elephant");
        user.put("notification_no_disturbing_end", 9);
        ArrayNode users = this.objectMapper.createArrayNode();
        users.add(user);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);
        return rsp;
    }

    private JsonNode handleGetNoDisturbingFly(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("notification_no_disturbing", true);
        user.put("username", "fly");
        user.put("notification_no_disturbing_start", 9);
        ArrayNode users = this.objectMapper.createArrayNode();
        users.add(user);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);
        return rsp;
    }

    private JsonNode handleGetNoDisturbingGorilla(JsonNode req) {
        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("notification_no_disturbing", true);
        user.put("username", "gorilla");
        ArrayNode users = this.objectMapper.createArrayNode();
        users.add(user);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);
        return rsp;
    }
}
