package com.easemob.im.server.api.message.missed;

import com.easemob.im.server.api.AbstractApiTest;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;
import reactor.test.StepVerifier;

import java.time.Duration;

public class MessageMissedTest extends AbstractApiTest {

    public MessageMissedTest() {
        this.server.addHandler("GET /easemob/demo/users/alice/offline_msg_count",
                this::handleMessageMisssedCountRequest);
    }

    @Test
    public void testMsgMissedCount() {
        MessageMissed missed = new MessageMissed(this.context);
        MissedMessageCount mmc1 = new MissedMessageCount("queen", 99);
        MissedMessageCount mmc2 = new MissedMessageCount("madhat", 1);

        missed.count("alice")
                .log()
                .as(StepVerifier::create)
                .expectNextMatches(mmc -> mmc1.equals(mmc) || mmc2.equals(mmc))
                .expectNextMatches(mmc -> mmc1.equals(mmc) || mmc2.equals(mmc))
                .expectComplete()
                .verify(Duration.ofSeconds(3));
    }

    private JsonNode handleMessageMisssedCountRequest(JsonNode jsonNode) {
        ObjectNode data = this.objectMapper.createObjectNode();
        data.put("queen", 99);
        data.put("madhat", 1);
        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", data);
        return rsp;
    }

}
