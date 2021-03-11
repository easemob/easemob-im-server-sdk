package com.easemob.im.server.api.group.admin.list;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.group.admin.GroupAdminList;
import com.easemob.im.server.model.EMGroupAdmin;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Map;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GroupAdminListTest extends AbstractApiTest {

    public GroupAdminListTest() {
        this.server.addHandler("GET /easemob/demo/chatgroups/1/admin", this::handleGroupAdminListRequest);
    }

    @Test
    void testListGroupAdmin() {
        Map<String, EMGroupAdmin> admins = GroupAdminList.all(this.context, "1").collect(Collectors.toMap(a -> a.getUsername(), a -> a)).block(Duration.ofSeconds(3));
        assertTrue(admins.containsKey("madhat"));
        assertTrue(admins.containsKey("rabbit"));
    }

    private JsonNode handleGroupAdminListRequest(JsonNode jsonNode) {
        ArrayNode data = this.objectMapper.createArrayNode();
        data.add("madhat");
        data.add("rabbit");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

}