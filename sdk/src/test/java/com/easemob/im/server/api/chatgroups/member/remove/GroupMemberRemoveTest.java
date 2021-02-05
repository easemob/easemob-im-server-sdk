package com.easemob.im.server.api.chatgroups.member.remove;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.chatgroups.member.add.GroupMemberAdd;
import com.easemob.im.server.exception.EMNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GroupMemberRemoveTest extends AbstractApiTest {

    public GroupMemberRemoveTest() {
        this.server.addHandler("DELETE /easemob/demo/chatgroups/1/users/alice", this::handleGroupMemberRemoveRequest);
    }

    @Test
    void testRemoveGroupMember() {
        assertDoesNotThrow(() -> {
            GroupMemberRemove.single(this.context, "1", "alice").block(Duration.ofSeconds(3));
        });
    }


    @Test
    void testRemoveGroupMemberNotFound() {
        assertDoesNotThrow(() -> {
            GroupMemberRemove.single(this.context, "1", "bob").block(Duration.ofSeconds(3));
        });
    }

    private JsonNode handleGroupMemberRemoveRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }
}