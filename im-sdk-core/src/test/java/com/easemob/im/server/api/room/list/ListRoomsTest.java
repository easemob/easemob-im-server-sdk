package com.easemob.im.server.api.room.list;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.model.EMPage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ListRoomsTest extends AbstractApiTest {

    public ListRoomsTest() {
        this.server.addHandler("GET /easemob/demo/chatrooms?limit=2", req -> handleListRoomsRequest(req, 0, 2, "1"));
        this.server.addHandler("GET /easemob/demo/chatrooms?limit=2&cursor=1", req -> handleListRoomsRequest(req, 2,1, null));
        this.server.addHandler("GET /easemob/demo/users/alice/joined_chatrooms", this::handleListRoomsUserJoined);
    }

    private JsonNode handleListRoomsUserJoined(JsonNode jsonNode) {
        ObjectNode room1 = this.objectMapper.createObjectNode();
        room1.put("id", "r1");
        room1.put("name", "room-1");

        ObjectNode room2 = this.objectMapper.createObjectNode();
        room2.put("id", "r2");
        room2.put("name", "room-2");

        ArrayNode rooms = this.objectMapper.createArrayNode();
        rooms.add(room1);
        rooms.add(room2);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", rooms);

        return rsp;
    }

    private JsonNode handleListRoomsRequest(JsonNode jsonNode, int offset, int count, String cursor) {
        ArrayNode rooms = this.objectMapper.createArrayNode();

        for (int i = 1; i <= count; i++) {
            ObjectNode room = this.objectMapper.createObjectNode();
            room.put("id", String.format("r%d", i+offset));
            room.put("name", String.format("room-%d", i+offset));
            room.put("owner", String.format("owner-%d", i+offset));
            room.put("affiliations_count", i+offset);
            rooms.add(room);
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", rooms);
        if (cursor != null) {
            rsp.put("cursor", cursor);
        }

        return rsp;
    }

    @Test
    void testListAll() {
        List<String> rooms = ListRooms.all(this.context, 2).collectList().block(Duration.ofSeconds(3));
        assertEquals(3, rooms.size());
        assertEquals("r1", rooms.get(0));
        assertEquals("r2", rooms.get(1));
        assertEquals("r3", rooms.get(2));
    }

    @Test
    void testListFirstPage() {
        EMPage<String> page = ListRooms.next(this.context,  2, null).block(Duration.ofSeconds(3));
        assertEquals(2, page.getValues().size());
    }

    @Test
    void testListSecondPage() {
        EMPage<String> page = ListRooms.next(this.context,  2, "1").block(Duration.ofSeconds(3));
        assertEquals(1, page.getValues().size());
    }

    @Test
    void testListUserJoined() {
        List<String> rooms = ListRooms.userJoined(this.context, "alice").collectList().block(Duration.ofSeconds(3));
        assertEquals(2, rooms.size());
        assertEquals("r1", rooms.get(0));
        assertEquals("r2", rooms.get(1));
    }

}