package com.easemob.im.server.api.chatgroups.delete;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GroupDeleteTest extends AbstractApiTest {

    public GroupDeleteTest() {
        this.server.addHandler("DELETE /easemob/demo/chatgroups/1", this::handleDeleteGroupRequest);
    }

    @Test
    public void testGroupDeleteSuccess() {
        assertDoesNotThrow(() -> {
            GroupDelete.execute(this.context, "1").block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testGroupDeleteNotFoundAlsoSuccess() {
        assertDoesNotThrow(() -> {
            GroupDelete.execute(this.context, "2").block(Duration.ofSeconds(3));
        });
    }

    private JsonNode handleDeleteGroupRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

}