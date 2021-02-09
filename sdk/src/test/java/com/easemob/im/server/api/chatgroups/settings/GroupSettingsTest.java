package com.easemob.im.server.api.chatgroups.settings;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.exception.EMUnknownException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GroupSettingsTest extends AbstractApiTest {

    public GroupSettingsTest() {
        this.server.addHandler("PUT /easemob/demo/chatgroups/1", this::handleGroupUpdateRequestSuccess);
        this.server.addHandler("PUT /easemob/demo/chatgroups/2", this::handleGroupUpdateRequestFailure);
    }

    @Test
    public void testGroupUpdateSuccess() {
        assertDoesNotThrow(() -> GroupSettings.update(this.context, "1", settings -> settings.setMaxMembers(10)).block(Duration.ofSeconds(3)));
    }

    @Test
    public void testGroupUpdateFailure() {
        assertThrows(EMUnknownException.class, () -> GroupSettings.update(this.context, "2", settings -> settings.setMaxMembers(1000000).setCanMemberInviteOthers(false)).block(Duration.ofSeconds(3)));
    }

    private JsonNode handleGroupUpdateRequestSuccess(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        if (jsonNode.has("maxusers")) {
            data.put("maxusers", true);
        }
        if (jsonNode.has("membersonly")) {
            data.put("membersonly", true);
        }
        if (jsonNode.has("allowinvites")) {
            data.put("allowinvites", true);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    private JsonNode handleGroupUpdateRequestFailure(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        if (jsonNode.has("maxusers")) {
            data.put("maxusers", false);
        }
        if (jsonNode.has("membersonly")) {
            data.put("membersonly", false);
        }
        if (jsonNode.has("allowinvites")) {
            data.put("allowinvites", false);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

}