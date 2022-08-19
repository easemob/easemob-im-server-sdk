package com.easemob.im.server.api.metadata.chatroom.delete;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.util.Utilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ChatRoomMetadataDeleteTest extends AbstractApiTest {
    ChatRoomMetadataDelete chatRoomMetadataDelete = new ChatRoomMetadataDelete(this.context);

    public ChatRoomMetadataDeleteTest() {
        this.server.addHandler("DELETE /easemob/demo/metadata/chatroom/r1/user/bob",
                this::handleChatRoomMetadataDelete);

        this.server.addHandler("DELETE /easemob/demo/metadata/chatroom/r1/user/admin/forced",
                this::handleChatRoomMetadataDelete);
    }

    @Test
    public void testMetadataSet() {
        List<String> list = new ArrayList<>();
        list.add("nickname");
        assertDoesNotThrow(
                () -> this.chatRoomMetadataDelete.fromChatRoom("bob", "r1", list)
                        .block(Utilities.UT_TIMEOUT));
    }

    @Test
    public void testMetadataSetForced() {
        List<String> list = new ArrayList<>();
        list.add("nickname");
        assertDoesNotThrow(
                () -> this.chatRoomMetadataDelete.fromChatRoomForced("r1", list)
                        .block(Utilities.UT_TIMEOUT));
    }

    public JsonNode handleChatRoomMetadataDelete(JsonNode req) {
        ObjectNode data = this.objectMapper.createObjectNode();
        List<String> successKeys = new ArrayList<>();
        successKeys.add("nickname");
        data.putPOJO("successKeys", successKeys);

        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.set("data", data);

        return jsonRsp;
    }
}
