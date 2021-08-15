package com.easemob.im.server.api.group.settings;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.exception.EMUnknownException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import static org.junit.jupiter.api.Assertions.*;

class UpdateGroupTest extends AbstractApiTest {

    UpdateGroup updateGroup = new UpdateGroup(this.context);

    public UpdateGroupTest() {
        this.server.addHandler("PUT /easemob/demo/chatgroups/1",
                this::handleGroupUpdateRequestSuccess);
        this.server.addHandler("PUT /easemob/demo/chatgroups/2",
                this::handleGroupUpdateRequestFailure);
        this.server
                .addHandler("PUT /easemob/demo/chatgroups/3", this::handleGroupUpdateOwnerRequest);
    }

    @Test
    public void testGroupUpdateSuccess() {
        assertDoesNotThrow(
                () -> this.updateGroup.update("1", settings -> settings.setMaxMembers(10))
                        .block(Utilities.UT_TIMEOUT));
    }

    @Test
    public void testGroupUpdateFailure() {
        assertThrows(EMUnknownException.class, () -> this.updateGroup.update("2",
                settings -> settings.setMaxMembers(1000000).setCanMemberInviteOthers(false))
                .block(Utilities.UT_TIMEOUT));
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

    @Test
    void testUpdateGroupOwner() {
        assertDoesNotThrow(() -> {
            this.updateGroup.updateOwner("3", "rabbit").block(Utilities.UT_TIMEOUT);
        });
    }

    private JsonNode handleGroupUpdateOwnerRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
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
