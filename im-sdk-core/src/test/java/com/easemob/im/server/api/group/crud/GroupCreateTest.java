package com.easemob.im.server.api.group.crud;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupCreateTest extends AbstractApiTest {

    GroupCreate groupCreate = new GroupCreate(this.context);

    public GroupCreateTest() {
        this.server.addHandler("POST /easemob/demo/chatgroups", this::handleGroupCreateRequest);
    }

    @Test
    public void testCreatePublicGroup() {
        List<String> members = new ArrayList<>();
        members.add("madhat");
        members.add("rabbit");
        assertEquals("group-create-test", this.groupCreate.publicGroup("alice", members, 10, true).block(Duration.ofSeconds(3)));
    }

    @Test
    public void testCreatePrivateGroup() {
        List<String> members = new ArrayList<>();
        members.add("madhat");
        members.add("rabbit");
        assertEquals("group-create-test", this.groupCreate.privateGroup("alice", members, 10, true).block(Duration.ofSeconds(3)));
    }

    private JsonNode handleGroupCreateRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("groupid", "group-create-test");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }
}