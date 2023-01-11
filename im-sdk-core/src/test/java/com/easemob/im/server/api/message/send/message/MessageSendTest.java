package com.easemob.im.server.api.message.send.message;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.message.send.message.MessageSend;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.model.EMKeyValue;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MessageSendTest extends AbstractApiTest {

    MessageSend messageSend = new MessageSend(context);

    MessageSendTest(){
        this.server
                .addHandler("POST /easemob/demo/messages/users", this::handleSendMessage);
    }

    private JsonNode handleSendMessage(JsonNode jsonNode) {
        System.out.println("send msg request:");
        System.out.println(jsonNode.toPrettyString());

        JsonNode targets = jsonNode.get("target");

        ObjectNode messageIdsByReceiverId = this.objectMapper.createObjectNode();
        for (int i = 0; i < targets.size(); i++) {
            String target = targets.get(i).asText();
            messageIdsByReceiverId.put(target, ThreadLocalRandom.current().nextLong(2 ^ 63 - 1));
        }

        ObjectNode rsp = this.objectMapper.createObjectNode();
        rsp.set("data", messageIdsByReceiverId);
        return rsp;
    }

    @Test
    void testSendUser2UserTextMessage() {
        assertDoesNotThrow(() -> this.messageSend.fromUser("alice").toUser("rabbit")
                .text(msg -> msg.text("hello"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.UT_TIMEOUT));
    }

    @Test
    void testSendUser2UserImageMessage() {
        assertDoesNotThrow(() -> this.messageSend.fromUser("alice").toUser("rabbit")
                .image(msg -> msg.uri(URI.create("http://example/image.jpg")).height(100.000)
                        .width(200.000))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.UT_TIMEOUT));
    }

    @Test
    void testSendUser2UserVoiceMessage() {
        assertDoesNotThrow(() -> this.messageSend.fromUser("alice").toUser("rabbit")
                .voice(msg -> msg.uri(URI.create("http://example/voice.amr")).duration(18))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.UT_TIMEOUT));
    }

    @Test
    void testSendUser2UserVideoMessage() {
        assertDoesNotThrow(() -> this.messageSend.fromUser("alice").toUser("rabbit")
                .video(msg -> msg.uri(URI.create("http://example/video.mp4")).duration(18))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.UT_TIMEOUT));
    }

    @Test
    void testSendUser2UserLocationMessage() {
        assertDoesNotThrow(() -> this.messageSend.fromUser("alice").toUser("rabbit")
                .location(msg -> msg.latitude(1.234567).longitude(1.234567).address("some where"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.UT_TIMEOUT));
    }

    @Test
    void testSendUser2UserCommandMessage() {
        assertDoesNotThrow(() -> this.messageSend.fromUser("alice").toUser("rabbit")
                .command(msg -> msg.action("run").param("name", "rabbit"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.UT_TIMEOUT));
    }

    @Test
    void testSendUser2UserCustomMessage() {
        assertDoesNotThrow(() -> this.messageSend.fromUser("alice").toUser("rabbit")
                .custom(msg -> msg.customEvent("liked").customExtension("name", "forest"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.UT_TIMEOUT));
    }
}
