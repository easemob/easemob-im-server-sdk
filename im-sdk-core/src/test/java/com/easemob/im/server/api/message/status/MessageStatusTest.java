package com.easemob.im.server.api.message.status;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class MessageStatusTest extends AbstractApiTest {

    public MessageStatusTest() {
        this.server.addHandler("GET /easemob/demo/users/alice/offline_msg_status/123456789", this::handleMessageStatusRequest);
    }

    @Test
    public void testMessageStatus() {
        boolean messageIsDelivered = MessageStatus.isMessageDeliveredToUser(this.context, "123456789", "alice").block(Duration.ofSeconds(3));
        assertEquals(true, messageIsDelivered);
    }

    private JsonNode handleMessageStatusRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("123456789", "delivered");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }
}