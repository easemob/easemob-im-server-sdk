package com.easemob.im.server.api.mute.detail;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.util.Utilities;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MuteDetailTest extends AbstractApiTest {

    MuteDetail muteDetail = new MuteDetail(this.context);

    public MuteDetailTest() {
        this.server.addHandler("GET /easemob/demo/mutes/bob", this::handleGetMuteDetailRequest);
    }

    @Test
    public void testGetMuteDetail() {
        Map<String, String> map = new HashMap<>();
        assertDoesNotThrow(
                () -> this.muteDetail.execute("bob").block(Utilities.UT_TIMEOUT));
    }

    public JsonNode handleGetMuteDetailRequest(JsonNode req) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("userid", "bob");
        data.put("chat", 96);
        data.put("groupchat", 96);
        data.put("chatroom", 96);
        data.put("unixtime", 1631609831);

        ObjectNode jsonRsp = this.objectMapper.createObjectNode();
        jsonRsp.set("data", data);

        return jsonRsp;
    }
}
