package com.easemob.im.server.api.group.admin.remove;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.group.admin.GroupAdminAdd;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupAdminRemoveTest extends AbstractApiTest {

    public GroupAdminRemoveTest() {
        this.server.addHandler("DELETE /easemob/demo/chatgroups/1/admin/rabbit", this::handleGroupAdminRemoveRequest);
    }

    @Test
    void testRemoveGroupAdmin() {
        assertDoesNotThrow(() -> {
            GroupAdminAdd.single(this.context, "1", "rabbit");
        });
    }

    @Test
    void testRemoveGroupAdminNotFound() {
        assertDoesNotThrow(() -> {
            GroupAdminAdd.single(this.context, "1", "alice");
        });
    }

    private JsonNode handleGroupAdminRemoveRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }
}