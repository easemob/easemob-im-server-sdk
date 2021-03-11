package com.easemob.im.server.api.message.status;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.easemob.im.server.model.EMMessageStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
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
        EMMessageStatus status = MessageStatus.isMessageDeliveredToUser(this.context, "123456789", "alice").block(Duration.ofSeconds(3));
        assertEquals("123456789", status.getMessageId());
        assertEquals(true, status.isDelivered());
        assertEquals("alice", status.getToUsername());
    }

    private JsonNode handleMessageStatusRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("123456789", "delivered");
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }
}