package com.easemob.im.server.api.status.message;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.MockingContext;
import com.easemob.im.server.api.MockingHttpServer;
import com.easemob.im.server.model.EMMessageStatus;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;

public class MessageStatusTest {

    private ObjectMapper objectMapper = new ObjectMapper();

    private MockingHttpServer server = MockingHttpServer.builder()
        .addHandler("GET /easemob/demo/users/alice/offline_msg_status/123456789", this::handleMessageStatusRequest)
        .build();

    private EMProperties properties = EMProperties.builder()
        .baseUri(this.server.uri())
        .appkey("easemob#demo")
        .clientId("clientId")
        .clientSecret("clientSecret")
        .build();

    private MockingContext context = new MockingContext(properties);

    @Test
    public void testMessageStatus() {
        MessageStatus messageStatus = new MessageStatus(this.context, "123456789");
        EMMessageStatus status = messageStatus.toUser("alice").block(Duration.ofSeconds(3));
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