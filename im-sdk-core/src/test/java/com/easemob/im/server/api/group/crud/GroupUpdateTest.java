package com.easemob.im.server.api.group.crud;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GroupUpdateTest extends AbstractApiTest {

    GroupUpdate groupUpdate = new GroupUpdate(this.context);

    public GroupUpdateTest() {
        this.server.addHandler("PUT /easemob/demo/chatgroups/1", this::handleGroupUpdateOwnerRequest);
    }

    @Test
    void testUpdateGroupOwner() {
        assertDoesNotThrow(() -> {
            this.groupUpdate.owner("1", "rabbit").block(Duration.ofSeconds(3));
        });
    }

    private JsonNode handleGroupUpdateOwnerRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }
}