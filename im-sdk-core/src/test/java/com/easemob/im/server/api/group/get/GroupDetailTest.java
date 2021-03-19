package com.easemob.im.server.api.group.get;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMGroup;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Set;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

class GroupDetailTest extends AbstractApiTest {

    private GetGroup getGroup = new GetGroup(this.context);

    public GroupDetailTest() {
        this.server.addHandler("GET /easemob/demo/chatgroups/1", this::handleGroupDetailRequest1);
    }

    @Test
    public void testGroupDetails() {
        EMGroup detail = this.getGroup.execute("1").block(Duration.ofSeconds(3));
        assertEquals("1", detail.getGroupId());
        assertEquals(true, detail.getIsPublic());
        assertEquals(false, detail.getNeedApproveToJoin());
        assertEquals(false, detail.getCanMemberInviteOthers());
        assertEquals("alice", detail.getOwner());
        assertEquals(200, detail.getMaxMembers());

        Set<String> members = detail.getMembers().stream().collect(Collectors.toSet());

        assertEquals(3, members.size());
        assertTrue(members.contains("user1"));
        assertTrue(members.contains("user2"));
        assertTrue(members.contains("user3"));
    }

    private JsonNode handleGroupDetailRequest1(JsonNode jsonNode) {
        ObjectNode group = buildGroupJson("1");

        ArrayNode data = this.objectMapper.createArrayNode();
        data.add(group);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    private ObjectNode buildGroupJson(String groupId) {
        ObjectNode member1 = this.objectMapper.createObjectNode();
        member1.put("owner", "user1");
        ObjectNode member2 = this.objectMapper.createObjectNode();
        member2.put("member", "user2");
        ObjectNode member3 = this.objectMapper.createObjectNode();
        member3.put("member", "user3");
        ArrayNode members = this.objectMapper.createArrayNode();
        members.add(member1);
        members.add(member2);
        members.add(member3);

        ObjectNode group1 = this.objectMapper.createObjectNode();
        group1.put("id", groupId);
        group1.put("public", true);
        group1.put("membersonly", false);
        group1.put("allowinvites", false);
        group1.put("owner", "alice");
        group1.put("maxusers", 200);
        group1.set("affiliations", members);
        return group1;
    }

}