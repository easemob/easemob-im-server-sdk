package com.easemob.im.server.api.group.member.list;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GroupMemberListTest extends AbstractApiTest {

    GroupMemberList groupMemberList = new GroupMemberList(this.context);

    public GroupMemberListTest() {
        this.server.addHandler("GET /easemob/demo/chatgroups/1/users?version=v3&limit=10",
                this::handleGroupMemberListRequest1);
        this.server.addHandler("GET /easemob/demo/chatgroups/1/users?version=v3&limit=10&cursor=1",
                this::handleGroupMemberListRequest2);
    }

    @Test
    public void testListGroupMemberAll() {
        List<String> usernames = this.groupMemberList.all("1", 10).collect(Collectors.toList())
                .block(Duration.ofSeconds(3));
        assertEquals(15, usernames.size());
    }

    private JsonNode handleGroupMemberListRequest1(JsonNode jsonNode) {
        ArrayNode data = this.objectMapper.createArrayNode();
        for (int i = 0; i < 10; i++) {
            data.add(String.format("user-%d", i));
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        rsp.put("cursor", "1");

        return rsp;
    }

    private JsonNode handleGroupMemberListRequest2(JsonNode jsonNode) {
        ArrayNode data = this.objectMapper.createArrayNode();
        for (int i = 10; i < 15; i++) {
            data.add(String.format("user-%d", i));
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }
}
