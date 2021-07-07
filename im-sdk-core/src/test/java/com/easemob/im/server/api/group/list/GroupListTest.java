package com.easemob.im.server.api.group.list;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.time.Duration;

public class GroupListTest extends AbstractApiTest {
    private static int seq = 0;

    private GroupList groupList = new GroupList(this.context);

    public GroupListTest() {
        super();
        this.server
                .addHandler("GET /easemob/demo/chatgroups?limit=10", this::handleGroupListRequest1);
        this.server.addHandler("GET /easemob/demo/chatgroups?limit=10&cursor=1",
                this::handleGroupListRequest2);
        this.server.addHandler("GET /easemob/demo/chatgroups?limit=10&cursor=2",
                this::handleGroupListRequest3);
        this.server.addHandler("GET /easemob/demo/users/alice/joined_chatgroups",
                this::handleGroupListUserJoined);
    }

    @Test
    public void testGroupListHighLevelApi() {
        this.groupList.all(10)
                .as(StepVerifier::create)
                .expectNextCount(25)
                .expectComplete()
                .verify(Duration.ofSeconds(3));
    }

    @Test
    public void testGroupListLowLevelApi() {
        this.groupList.next(10, "1")
                .as(StepVerifier::create)
                .expectNextMatches(
                        page -> page.getValues().size() == 10 && page.getCursor().equals("2"))
                .expectComplete()
                .verify(Duration.ofSeconds(3));
    }

    @Test
    public void testGroupListUserJoined() {
        this.groupList.userJoined("alice")
                .as(StepVerifier::create)
                .expectNext("aliceGroup")
                .expectNext("rabbitGroup")
                .expectComplete()
                .verify(Duration.ofSeconds(3));
    }

    private JsonNode handleGroupListRequest1(JsonNode jsonNode) {
        return buildResponse(10, "1");
    }

    private JsonNode handleGroupListRequest2(JsonNode jsonNode) {
        return buildResponse(10, "2");
    }

    private JsonNode handleGroupListRequest3(JsonNode jsonNode) {
        return buildResponse(5, null);
    }

    private JsonNode handleGroupListUserJoined(JsonNode jsonNode) {
        ArrayNode data = this.objectMapper.createArrayNode();

        ObjectNode group1 = this.objectMapper.createObjectNode();
        group1.put("groupid", "aliceGroup");
        data.add(group1);

        ObjectNode group2 = this.objectMapper.createObjectNode();
        group2.put("groupid", "rabbitGroup");
        data.add(group2);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

    private ObjectNode buildResponse(int count, String cursor) {
        ArrayNode data = this.objectMapper.createArrayNode();
        for (int i = 0; i < count; i++) {
            ObjectNode group = this.objectMapper.createObjectNode();
            group.put("groupid", String.format("%d", seq++));
            data.add(group);
        }
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        if (cursor != null) {
            rsp.put("cursor", cursor);
        }
        return rsp;
    }

}
