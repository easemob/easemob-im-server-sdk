package com.easemob.im.server.api.group.member.add;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.exception.EMNotFoundException;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import static org.junit.jupiter.api.Assertions.*;

class GroupMemberAddTest extends AbstractApiTest {

    GroupMemberAdd groupMemberAdd = new GroupMemberAdd(this.context);

    GroupMemberAddTest() {
        this.server.addHandler("POST /easemob/demo/chatgroups/1/users/alice",
                this::handleGroupMemberAddRequest);
    }

    @Test
    void testAddGroupMember() {
        assertDoesNotThrow(() -> {
            this.groupMemberAdd.single("1", "alice").block(Utilities.UT_TIMEOUT);
        });
    }

    @Test
    void testAddGroupMemberNotFound() {
        assertThrows(EMNotFoundException.class, () -> {
            this.groupMemberAdd.single("1", "bob").block(Utilities.UT_TIMEOUT);
        });
    }

    private JsonNode handleGroupMemberAddRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

}
