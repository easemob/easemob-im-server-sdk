package com.easemob.im.server.api.group.delete;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.group.crud.GroupDestroy;
import com.fasterxml.jackson.databind.JsonNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

class GroupDestroyTest extends AbstractApiTest {

    GroupDestroy groupDestroy = new GroupDestroy(this.context);

    public GroupDestroyTest() {
        this.server.addHandler("DELETE /easemob/demo/chatgroups/1", this::handleDeleteGroupRequest);
    }

    @Test
    public void testGroupDeleteSuccess() {
        assertDoesNotThrow(() -> {
            this.groupDestroy.execute("1").block(Duration.ofSeconds(3));
        });
    }

    @Test
    public void testGroupDeleteNotFoundAlsoSuccess() {
        assertDoesNotThrow(() -> {
            this.groupDestroy.execute("2").block(Duration.ofSeconds(3));
        });
    }

    private JsonNode handleDeleteGroupRequest(JsonNode jsonNode) {
        return this.objectMapper.createObjectNode();
    }

}