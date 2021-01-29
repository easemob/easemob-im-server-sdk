package com.easemob.im.server.api.notification.user;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class NotificationUserTest {
    private ObjectMapper objectMapper = new ObjectMapper();

    private MockingHttpServer server = MockingHttpServer.builder()
        .addHandler("PUT /easemob/demo/users/username/nickname", this::handleNotificationUserSetNickname)
        .addHandler("PUT /easemob/demo/users/username", this::handleNotificationUserSetStyle)
        .build();

    private EMProperties properties = EMProperties.builder()
        .baseUri(this.server.uri())
        .appkey("easemob#demo")
        .clientId("clientId")
        .clientSecret("clientSecret")
        .build();

    private MockingContext context = new MockingContext(properties);

    @Test
    public void testUserSetNickname() {
        NotificationUser notificationUser = new NotificationUser(this.context);
        assertDoesNotThrow(() -> {
            notificationUser.setNickName("username", "nickname").block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testUserSetStyle() {
        NotificationUser notificationUser = new NotificationUser(this.context);
        assertDoesNotThrow(() -> {
            notificationUser.setStyle("username", NotificationUser.Style.MESSAGE_CONTENT).block(Duration.ofSeconds(3));
        });
    }

    private JsonNode handleNotificationUserSetNickname(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

    private JsonNode handleNotificationUserSetStyle(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

}