package com.easemob.im.server.api.chatgroups.member.add;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.exception.EMNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GroupMemberAddTest extends AbstractApiTest {

    public GroupMemberAddTest() {
        this.server.addHandler("POST /easemob/demo/chatgroups/1/users/alice", this::handleGroupMemberAddRequest);
    }

    @Test
    void testAddGroupMember() {
        assertDoesNotThrow(() -> {
            GroupMemberAdd.single(this.context, "1", "alice").block(Duration.ofSeconds(3));
        });
    }

    @Test
    void testAddGroupMemberNotFound() {
        assertThrows(EMNotFoundException.class, () -> {
            GroupMemberAdd.single(this.context, "1", "bob").block(Duration.ofSeconds(3));
        });
    }

    private JsonNode handleGroupMemberAddRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

}