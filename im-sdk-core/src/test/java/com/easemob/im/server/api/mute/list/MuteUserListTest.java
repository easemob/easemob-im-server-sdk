package com.easemob.im.server.api.mute.list;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.util.Utilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MuteUserListTest extends AbstractApiTest {

    MuteList muteUserList = new MuteList(this.context);

    public MuteUserListTest() {
        this.server.addHandler("GET /easemob/demo/mutes", this::handleGetMuteDetailRequest);
    }

    @Test
    public void testGetMuteDetail() {
        Map<String, String> map = new HashMap<>();
        assertDoesNotThrow(
                () -> this.muteUserList.execute().block(Utilities.UT_TIMEOUT));
    }

    public JsonNode handleGetMuteDetailRequest(JsonNode req) {
        ArrayNode data = this.objectMapper.createArrayNode();
        ObjectNode mute = this.objectMapper.createObjectNode();
        mute.put("userid", "bob");
        mute.put("chat", 96);
        mute.put("groupchat", 96);
        mute.put("chatroom", 96);
        data.add(mute);

        ObjectNode jsonData = this.objectMapper.createObjectNode();
        jsonData.set("data", data);
        jsonData.put("unixtime", 1631609831);

        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.set("data", jsonData);

        return jsonRsp;
    }
}
