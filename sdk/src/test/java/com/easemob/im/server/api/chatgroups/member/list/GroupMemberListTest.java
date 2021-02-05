package com.easemob.im.server.api.chatgroups.member.list;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMGroupMember;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GroupMemberListTest extends AbstractApiTest {

    public GroupMemberListTest() {
        this.server.addHandler("GET /easemob/demo/chatgroups/1/users?limit=10", this::handleGroupMemberListRequest1);
        this.server.addHandler("GET /easemob/demo/chatgroups/1/users?limit=10&cursor=1", this::handleGroupMemberListRequest2);
    }

    @Test
    public void testListGroupMemberAll() {
        List<EMGroupMember> members = GroupMemberList.all(this.context, "1", 10).collect(Collectors.toList()).block(Duration.ofSeconds(3));
        assertEquals(15, members.size());
    }

    private JsonNode handleGroupMemberListRequest1(JsonNode jsonNode) {
        ArrayNode data = this.objectMapper.createArrayNode();

        ObjectNode owner = this.objectMapper.createObjectNode();
        owner.put("owner", "user-0");
        data.add(owner);

        for (int i = 1; i < 10; i++) {
            ObjectNode member = this.objectMapper.createObjectNode();
            member.put("member", String.format("user-%d", i));
            data.add(member);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        rsp.put("cursor", "1");

        return rsp;
    }


    private JsonNode handleGroupMemberListRequest2(JsonNode jsonNode) {
        ArrayNode data = this.objectMapper.createArrayNode();

        for (int i = 10; i < 15; i++) {
            ObjectNode member = this.objectMapper.createObjectNode();
            member.put("member", String.format("user-%d", i));
            data.add(member);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }
}