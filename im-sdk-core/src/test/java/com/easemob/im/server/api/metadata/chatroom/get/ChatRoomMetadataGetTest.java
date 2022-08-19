package com.easemob.im.server.api.metadata.chatroom.get;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.util.Utilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

class ChatRoomMetadataGetTest extends AbstractApiTest {
    ChatRoomMetadataGet chatRoomMetadataDeleteGet = new ChatRoomMetadataGet(this.context);

    public ChatRoomMetadataGetTest() {
        this.server.addHandler("POST /easemob/demo/metadata/chatroom/r1",
                this::handleChatRoomMetadataGet);
    }

    @Test
    public void testMetadataGet() {
        List<String> list = new ArrayList<>();
        list.add("nickname");
        assertDoesNotThrow(
                () -> this.chatRoomMetadataDeleteGet.listChatRoomMetadata("r1", list)
                        .block(Utilities.UT_TIMEOUT));
    }

    public JsonNode handleChatRoomMetadataGet(JsonNode req) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("nickname", "昵称");
        data.put("avatar", "http://www.easemob.com/avatar.png");
        data.put("phone", "159");

        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.set("data", data);

        return jsonRsp;
    }
}
