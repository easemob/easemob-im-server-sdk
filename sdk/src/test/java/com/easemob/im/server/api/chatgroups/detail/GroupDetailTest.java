package com.easemob.im.server.api.chatgroups.detail;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMGroupDetail;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class GroupDetailTest extends AbstractApiTest {

    public GroupDetailTest() {
        this.server.addHandler("GET /easemob/demo/chatgroups/1,2", this::handleGroupDetailRequest1);
    }

    @Test
    public void testGroupDetails() {
        GroupDetail groupDetail = new GroupDetail(this.context);
        List<EMGroupDetail> result = groupDetail.byId(Flux.just("1", "2")).collectList().block(Duration.ofSeconds(3));
        assertEquals(2, result.size());
    }

    private JsonNode handleGroupDetailRequest1(JsonNode jsonNode) {
        ObjectNode group1 = buildGroupJson("1");
        ObjectNode group2 = buildGroupJson("2");

        ArrayNode data = this.objectMapper.createArrayNode();
        data.add(group1);
        data.add(group2);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

    private ObjectNode buildGroupJson(String groupId) {
        ObjectNode group1Member1 = this.objectMapper.createObjectNode();
        group1Member1.put("owner", "user1");
        ObjectNode group1Member2 = this.objectMapper.createObjectNode();
        group1Member2.put("member", "user2");
        ObjectNode group1Member3 = this.objectMapper.createObjectNode();
        group1Member3.put("member", "user3");
        ArrayNode group1Members = this.objectMapper.createArrayNode();
        group1Members.add(group1Member1);
        group1Members.add(group1Member2);
        group1Members.add(group1Member3);

        ObjectNode group1 = this.objectMapper.createObjectNode();
        group1.put("id", groupId);
        group1.put("membersonly", false);
        group1.put("allowinvites", false);
        group1.put("maxusers", 200);
        group1.put("public", true);
        group1.set("affiliations", group1Members);
        return group1;
    }

}