package com.easemob.im.server.api.metadata.chatroom.set;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.metadata.chatroom.AutoDelete;
import com.easemob.im.server.api.util.Utilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ChatRoomMetadataSetTest extends AbstractApiTest {
    ChatRoomMetadataSet chatRoomMetadataSet = new ChatRoomMetadataSet(this.context);

    public ChatRoomMetadataSetTest() {
        this.server.addHandler("PUT /easemob/demo/metadata/chatroom/r1/user/bob",
                this::handleChatRoomMetadataSet);

        this.server.addHandler("PUT /easemob/demo/metadata/chatroom/r1/user/bob/forced",
                this::handleChatRoomMetadataSet);
    }

    @Test
    public void testMetadataSet() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");
        assertDoesNotThrow(
                () -> this.chatRoomMetadataSet.toChatRoom("bob", "r1", map, AutoDelete.DELETE)
                        .block(Utilities.UT_TIMEOUT));
    }

    @Test
    public void testMetadataSetForced() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");
        assertDoesNotThrow(
                () -> this.chatRoomMetadataSet.toChatRoomForced("bob", "r1", map, AutoDelete.DELETE)
                        .block(Utilities.UT_TIMEOUT));
    }

    public JsonNode handleChatRoomMetadataSet(JsonNode req) {
        ObjectNode data = this.objectMapper.createObjectNode();
        List<String> successKeys = new ArrayList<>();
        successKeys.add("phone");
        successKeys.add("avatar");
        successKeys.add("nickname");
        data.putPOJO("successKeys", successKeys);

        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.set("data", data);

        return jsonRsp;
    }
}
