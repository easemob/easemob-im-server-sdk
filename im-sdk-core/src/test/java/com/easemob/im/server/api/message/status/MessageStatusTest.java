package com.easemob.im.server.api.message.status;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import com.easemob.im.server.api.util.Utilities;

import static org.junit.jupiter.api.Assertions.*;

public class MessageStatusTest extends AbstractApiTest {

    private MessageStatus messageStatus;

    public MessageStatusTest() {
        this.server.addHandler("GET /easemob/demo/users/alice/offline_msg_status/123456789",
                this::handleMessageStatusRequest);
        this.messageStatus = new MessageStatus(this.context);
    }

    @Test
    public void testMessageStatus() {
        boolean messageIsDelivered =
                this.messageStatus.isMessageDeliveredToUser("123456789", "alice")
                        .block(Utilities.UT_TIMEOUT);
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
