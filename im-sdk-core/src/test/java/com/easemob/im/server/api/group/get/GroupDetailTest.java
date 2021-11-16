package com.easemob.im.server.api.group.get;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMGroup;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GroupDetailTest extends AbstractApiTest {

    private GetGroup getGroup = new GetGroup(this.context);

    public GroupDetailTest() {
        this.server.addHandler("GET /easemob/demo/chatgroups/1", this::handleGroupDetailRequest1);
    }

    @Test
    public void testGroupDetails() {
        EMGroup detail = this.getGroup.execute("1").block(Utilities.UT_TIMEOUT);
        assertEquals("1", detail.getGroupId());
        assertEquals("test-group", detail.getName());
        assertEquals("test-description", detail.getDescription());
        assertEquals(true, detail.getIsPublic());
        assertEquals(false, detail.getNeedApproveToJoin());
        assertEquals(false, detail.getCanMemberInviteOthers());
        assertEquals("alice", detail.getOwner());
        assertEquals(200, detail.getMaxMembers());
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
        ObjectNode group1 = this.objectMapper.createObjectNode();
        group1.put("id", groupId);
        group1.put("name", "test-group");
        group1.put("description", "test-description");
        group1.put("public", true);
        group1.put("membersonly", false);
        group1.put("allowinvites", false);
        group1.put("owner", "alice");
        group1.put("maxusers", 200);
        group1.put("affiliations_count", 1);
        return group1;
    }

}