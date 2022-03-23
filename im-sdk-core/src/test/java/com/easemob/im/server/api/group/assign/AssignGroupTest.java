package com.easemob.im.server.api.group.assign;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.util.Utilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AssignGroupTest extends AbstractApiTest {

    AssignGroup assignGroup=new AssignGroup(context);

    public AssignGroupTest() {
        this.server.addHandler("PUT /easemob/demo/chatgroups/1", this::handleAssignGroupRequest);
    }

    @Test
    public void testAssignGroup(){
        assertDoesNotThrow(() -> {
            this.assignGroup.execute("1","newOwner1").block(Utilities.UT_TIMEOUT);
        });
    }

    private JsonNode handleAssignGroupRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("newowner", "newOwner1");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }
}
