package com.easemob.im.server.api.user.get;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserGetTest extends AbstractApiTest {

    private UserGet userGet;

    public UserGetTest() {
        this.server.addHandler("GET /easemob/demo/users/username", this::handleUserGetSingle);
        this.userGet = new UserGet(this.context);
    }

    @Test
    @SuppressWarnings("unchecked")
    public void testUserGetSingle() {
        UserGetResponse user = this.userGet.single("username").block(Duration.ofSeconds(3));
        assertEquals("username", user.getEntities().get("username"));
        assertEquals(true, user.getEntities().get("activated"));
        assertEquals(0, user.getEntities().get("notification_display_style"));
        assertEquals(true, user.getEntities().get("notification_no_disturbing"));
        assertEquals(8, user.getEntities().get("notification_no_disturbing_start"));
        assertEquals(10, user.getEntities().get("notification_no_disturbing_end"));
        assertEquals(true, user.getEntities().get("notification_ignore_63112447328257"));
        assertNotNull(user.getEntities().get("pushInfo"));
        List<Map<String, String>> pushInfos = (List<Map<String, String>>) user.getEntities().get("pushInfo");
        assertEquals("device_id", pushInfos.get(0).get("device_id"));
        assertEquals("device_token", pushInfos.get(0).get("device_token"));
        assertEquals("notifier_name", pushInfos.get(0).get("notifier_name"));
    }

    private JsonNode handleUserGetSingle(JsonNode req) {
        ArrayNode pushInfos = this.objectMapper.createArrayNode();
        ObjectNode pushInfo = this.objectMapper.createObjectNode();
        pushInfo.put("device_id", "device_id");
        pushInfo.put("device_token", "device_token");
        pushInfo.put("notifier_name", "notifier_name");
        pushInfos.add(pushInfo);

        ObjectNode user = this.objectMapper.createObjectNode();
        user.put("username", "username");
        user.put("activated", true);
        user.put("notification_display_style", 0);
        user.put("notification_no_disturbing", true);
        user.put("notification_no_disturbing_start", 8);
        user.put("notification_no_disturbing_end", 10);
        user.put("notification_ignore_63112447328257", true);
        user.set("pushInfo", pushInfos);

        ArrayNode users = this.objectMapper.createArrayNode();
        users.add(user);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", users);

        return rsp;
    }
}