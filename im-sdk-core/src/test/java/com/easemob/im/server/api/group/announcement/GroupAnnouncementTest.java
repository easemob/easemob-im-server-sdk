package com.easemob.im.server.api.group.announcement;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GroupAnnouncementTest extends AbstractApiTest {

    GroupAnnouncement groupAnnouncement = new GroupAnnouncement(this.context);

    public GroupAnnouncementTest() {
        this.server.addHandler("GET /easemob/demo/chatgroups/1/announcement",
                this::handleGroupAnnouncementGetRequest);
        this.server.addHandler("POST /easemob/demo/chatgroups/1/announcement",
                this::handleGroupAnnouncementUpdateRequest);
    }

    @Test
    public void testGroupAnnouncementGet() {
        String announcement = this.groupAnnouncement.get("1").block(Duration.ofSeconds(3));
        assertEquals("Hello World", announcement);
    }

    @Test
    public void testGroupAnnouncementSet() {
        assertDoesNotThrow(
                () -> this.groupAnnouncement.set("1", "你好,世界").block(Duration.ofSeconds(3)));
    }

    private JsonNode handleGroupAnnouncementGetRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("announcement", "Hello World");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

    private JsonNode handleGroupAnnouncementUpdateRequest(JsonNode req) {
        assertEquals("你好,世界", req.get("announcement").asText());
        return this.objectMapper.createObjectNode();
    }

}
