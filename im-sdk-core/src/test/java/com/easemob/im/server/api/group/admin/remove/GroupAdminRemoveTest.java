package com.easemob.im.server.api.group.admin.remove;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.group.admin.GroupAdminAdd;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class GroupAdminRemoveTest extends AbstractApiTest {

    GroupAdminAdd groupAdminAdd = new GroupAdminAdd(this.context);

    public GroupAdminRemoveTest() {
        this.server.addHandler("DELETE /easemob/demo/chatgroups/1/admin/rabbit", this::handleGroupAdminRemoveRequest);
    }

    @Test
    void testRemoveGroupAdmin() {
        assertDoesNotThrow(() -> {
            this.groupAdminAdd.single("1", "rabbit");
        });
    }

    @Test
    void testRemoveGroupAdminNotFound() {
        assertDoesNotThrow(() -> {
            this.groupAdminAdd.single("1", "alice");
        });
    }

    private JsonNode handleGroupAdminRemoveRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }
}