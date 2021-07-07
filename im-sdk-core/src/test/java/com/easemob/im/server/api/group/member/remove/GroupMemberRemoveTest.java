package com.easemob.im.server.api.group.member.remove;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GroupMemberRemoveTest extends AbstractApiTest {

    private GroupMemberRemove groupMemberRemove = new GroupMemberRemove(this.context);

    public GroupMemberRemoveTest() {
        this.server.addHandler("DELETE /easemob/demo/chatgroups/1/users/alice",
                this::handleGroupMemberRemoveRequest);
    }

    @Test
    void testRemoveGroupMember() {
        assertDoesNotThrow(() -> {
            this.groupMemberRemove.single("1", "alice").block(Duration.ofSeconds(3));
        });
    }

    @Test
    void testRemoveGroupMemberNotFound() {
        assertDoesNotThrow(() -> {
            this.groupMemberRemove.single("1", "bob").block(Duration.ofSeconds(3));
        });
    }

    private JsonNode handleGroupMemberRemoveRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }
}
