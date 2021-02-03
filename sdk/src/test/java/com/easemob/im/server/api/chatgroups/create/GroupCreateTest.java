package com.easemob.im.server.api.chatgroups.create;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMGroup;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class GroupCreateTest extends AbstractApiTest {

    public GroupCreateTest() {
        this.server.addHandler("POST /easemob/demo/chatgroups", this::handleGroupCreateRequest);
    }

    @Test
    public void testCreatePublicGroup() {
        GroupCreate groupCreate = new GroupCreate(this.context);
        EMGroup emGroup = new EMGroup("group-create-test");
        List<String> members = new ArrayList<>();
        members.add("madhat");
        members.add("rabbit");
        assertEquals(emGroup, groupCreate.publicGroup("alice").block(Duration.ofSeconds(3)));
        assertEquals(emGroup, groupCreate.publicGroup("alice", members).block(Duration.ofSeconds(3)));
        assertEquals(emGroup, groupCreate.publicGroup("alice", members, 10).block(Duration.ofSeconds(3)));
        assertEquals(emGroup, groupCreate.publicGroup("alice", members, 10, true).block(Duration.ofSeconds(3)));
    }

    @Test
    public void testCreatePrivateGroup() {
        GroupCreate groupCreate = new GroupCreate(this.context);
        EMGroup emGroup = new EMGroup("group-create-test");
        List<String> members = new ArrayList<>();
        members.add("madhat");
        members.add("rabbit");
        assertEquals(emGroup, groupCreate.privateGroup("alice").block(Duration.ofSeconds(3)));
        assertEquals(emGroup, groupCreate.privateGroup("alice", members).block(Duration.ofSeconds(3)));
        assertEquals(emGroup, groupCreate.privateGroup("alice", members, 10).block(Duration.ofSeconds(3)));
        assertEquals(emGroup, groupCreate.privateGroup("alice", members, 10, true).block(Duration.ofSeconds(3)));
    }

    private JsonNode handleGroupCreateRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("groupid", "group-create-test");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }
}