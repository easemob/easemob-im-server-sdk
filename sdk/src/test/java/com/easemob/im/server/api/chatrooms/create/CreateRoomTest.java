package com.easemob.im.server.api.chatrooms.create;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CreateRoomTest extends AbstractApiTest {

    CreateRoomTest() {
        this.server.addHandler("POST /easemob/demo/chatrooms", this::handleCreateRoomRequest);
    }

    private JsonNode handleCreateRoomRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("id", "r1");

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    @Test
    void testCreateRoom() {
        List<String> members = new ArrayList<>();
        members.add("rabbit");
        members.add("madhat");
        String id = CreateRoom.createRoom(this.context, "room one", "have a nice day", "alice", members, 200)
            .block(Duration.ofSeconds(3));
        assertEquals("r1", id);
    }

}