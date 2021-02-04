package com.easemob.im.server.api.chatgroups.detail;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMGroupDetails;
import com.easemob.im.server.model.EMGroupMember;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupDetailTest extends AbstractApiTest {

    public GroupDetailTest() {
        this.server.addHandler("GET /easemob/demo/chatgroups/1", this::handleGroupDetailRequest1);
    }

    @Test
    public void testGroupDetails() {
        EMGroupDetails detail = GroupDetails.execute(this.context, "1").block(Duration.ofSeconds(3));
        assertEquals("1", detail.getGroupId());
        assertEquals(true, detail.getIsPublic());
        assertEquals(false, detail.getNeedApproveToJoin());
        assertEquals(false, detail.getMemberCanInviteOthers());
        assertEquals(200, detail.getMaxMembers());

        List<EMGroupMember> members = detail.getMembers();
        Collections.sort(members, Comparator.comparing(EMGroupMember::getUsername));
        assertEquals(3, members.size());
        assertEquals(EMGroupMember.asOwner("user1"), members.get(0));
        assertEquals(EMGroupMember.asMember("user2"), members.get(1));
        assertEquals(EMGroupMember.asMember("user3"), members.get(2));
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
        group1.put("maxusers", 200);
        group1.set("affiliations", members);
        return group1;
    }

}