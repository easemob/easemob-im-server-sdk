package com.easemob.im.server.api.notification.settings;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.easemob.im.server.model.EMNotificationUserSetting;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationUserSettingTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    private MockingHttpServer server = MockingHttpServer.builder()
        .addHandler("PUT /easemob/demo/users/username", this::handleNotificationUserSettingUpdateRequest)
        .addHandler("GET /easemob/demo/users/username", this::handleNotificationUserSettingGetRequest)
        .build();

    private EMProperties properties = EMProperties.builder()
        .setBaseUri(this.server.uri())
        .setAppkey("easemob#demo")
        .setClientId("clientId")
        .setClientSecret("clientSecret")
        .build();

    private MockingContext context = new MockingContext(properties);

    @Test
    public void testUserSetNickname() {
        assertDoesNotThrow(() -> {
            NotificationUserSetting.update(this.context, "username", settings -> settings.withNickname("nickname")).block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testUserSetStyle() {
        assertDoesNotThrow(() -> {
            NotificationUserSetting.update(this.context,"username", settings -> settings.withShowMessageContent(true)).block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testGetUserSetting() {
        EMNotificationUserSetting setting = NotificationUserSetting.get(this.context,"username").block(Duration.ofSeconds(3));
        assertEquals("username", setting.getUsername());
        assertEquals("nickname", setting.getNickname());
        assertEquals(true, setting.getShowMessageContent());
    }

    private JsonNode handleNotificationUserSettingUpdateRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

    private JsonNode handleNotificationUserSettingGetRequest(JsonNode jsonNode) {
        ObjectNode setting = this.objectMapper.createObjectNode();
        setting.put("username", "username");
        setting.put("nickname", "nickname");
        setting.put("notification_display_style", 1);

        ArrayNode entities = this.objectMapper.createArrayNode();
        entities.add(setting);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("entities", entities);
        return rsp;
    }

}