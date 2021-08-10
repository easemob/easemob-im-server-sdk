package com.easemob.im.server.api.message.history;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.nio.file.Paths;
import com.easemob.im.server.api.util.Utilities;
import java.time.Instant;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.*;

class MessageHistoryTest extends AbstractApiTest {

    MessageHistory messageHistory = new MessageHistory(this.context, "+8");

    MessageHistoryTest() {
        this.server.addHandler("GET /easemob/demo/chatmessages/2020020200",
                this::handleGetMessageHistoryTest);
    }

    private JsonNode handleGetMessageHistoryTest(JsonNode jsonNode) {
        ObjectNode history = this.objectMapper.createObjectNode();
        history.put("url", "https://example.com/history");

        ArrayNode data = this.objectMapper.createArrayNode();
        data.add(history);

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);

        return rsp;
    }

    @Test
    void testGetUrl() {
        String url = this.messageHistory.toUri(Instant.ofEpochSecond(1580574630))
                .block(Utilities.UT_TIMEOUT);
        assertEquals("https://example.com/history", url);
    }
}
