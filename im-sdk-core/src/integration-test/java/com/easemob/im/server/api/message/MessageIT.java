package com.easemob.im.server.api.message;

import com.easemob.im.server.EMException;
import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.message.recall.RecallMessageSource;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.model.EMKeyValue;
import com.easemob.im.server.model.EMSentMessageIds;
import com.easemob.im.server.model.EMSentMessageResults;
import com.easemob.im.server.model.EMTextMessage;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class MessageIT extends AbstractIT {

    MessageIT() {
        super();
    }

    @Test
    void testMessageSendText() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> {
            EMSentMessageIds messageIds = this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .text(msg -> msg.text("hello"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.IT_TIMEOUT);
            assertNotNull(messageIds.getMessageIdsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(() -> {
            Set<String> tos = new HashSet<>();
            tos.add(randomToUsername);

            Set<EMKeyValue> exts = new HashSet<>();
            exts.add(EMKeyValue.of("key", "value"));

            EMSentMessageIds messageIds = this.service.message().send(randomFromUsername, "users", tos, new EMTextMessage().text("你好"), exts).block(Utilities.IT_TIMEOUT);
            assertNotNull(messageIds.getMessageIdsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendTextWithoutMsgId() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> {
            EMSentMessageResults messageResults = this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .text(msg -> msg.text("hello"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .sendWithoutMsgId()
                .block(Utilities.IT_TIMEOUT);
            assertEquals("success", messageResults.getMessageResultsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(() -> {
            Set<String> tos = new HashSet<>();
            tos.add(randomToUsername);

            Set<EMKeyValue> exts = new HashSet<>();
            exts.add(EMKeyValue.of("key", "value"));

            EMSentMessageResults messageResults = this.service.message().sendWithoutMsgId(randomFromUsername, "users", tos, new EMTextMessage().text("你好"), exts).block(Utilities.IT_TIMEOUT);
            assertNotNull(messageResults.getMessageResultsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendTextDeliveryOnline() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> {
            EMSentMessageIds messageIds = this.service.message().send()
                    .fromUser(randomFromUsername)
                    .toUser(randomToUsername)
                    .text(msg -> msg.text("hello"))
                    .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                    .routeType("ROUTE_ONLINE")
                    .send()
                    .block(Utilities.IT_TIMEOUT);
            assertNotNull(messageIds.getMessageIdsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(() -> {
            Set<String> tos = new HashSet<>();
            tos.add(randomToUsername);

            Set<EMKeyValue> exts = new HashSet<>();
            exts.add(EMKeyValue.of("key", "value"));

            EMSentMessageIds messageIds = this.service.message().send(randomFromUsername, "users", tos, new EMTextMessage().text("你好"), exts, "ROUTE_ONLINE").block(Utilities.IT_TIMEOUT);
            assertNotNull(messageIds.getMessageIdsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendTextDeliveryOnlineWithoutMsgId() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> {
            EMSentMessageResults messageResults = this.service.message().send()
                    .fromUser(randomFromUsername)
                    .toUser(randomToUsername)
                    .text(msg -> msg.text("hello"))
                    .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                    .routeType("ROUTE_ONLINE")
                    .sendWithoutMsgId()
                    .block(Utilities.IT_TIMEOUT);
            assertEquals("success", messageResults.getMessageResultsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(() -> {
            Set<String> tos = new HashSet<>();
            tos.add(randomToUsername);

            Set<EMKeyValue> exts = new HashSet<>();
            exts.add(EMKeyValue.of("key", "value"));

            EMSentMessageResults messageResults = this.service.message().sendWithoutMsgId(randomFromUsername, "users", tos, new EMTextMessage().text("你好"), exts, "ROUTE_ONLINE").block(Utilities.IT_TIMEOUT);
            assertNotNull(messageResults.getMessageResultsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendTextSyncDevice() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> {
            EMSentMessageIds messageIds = this.service.message().send()
                    .fromUser(randomFromUsername)
                    .toUser(randomToUsername)
                    .text(msg -> msg.text("hello"))
                    .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                    .syncDevice(true)
                    .send()
                    .block(Utilities.IT_TIMEOUT);
            assertNotNull(messageIds.getMessageIdsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(() -> {
            Set<String> tos = new HashSet<>();
            tos.add(randomToUsername);

            Set<EMKeyValue> exts = new HashSet<>();
            exts.add(EMKeyValue.of("key", "value"));

            EMSentMessageIds messageIds = this.service.message().send(randomFromUsername, "users", tos, new EMTextMessage().text("你好"), exts, true).block(Utilities.IT_TIMEOUT);
            assertNotNull(messageIds.getMessageIdsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendTextSyncDeviceWithoutMsgId() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> {
            EMSentMessageResults messageResults = this.service.message().send()
                    .fromUser(randomFromUsername)
                    .toUser(randomToUsername)
                    .text(msg -> msg.text("hello"))
                    .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                    .syncDevice(true)
                    .sendWithoutMsgId()
                    .block(Utilities.IT_TIMEOUT);
            assertEquals("success", messageResults.getMessageResultsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(() -> {
            Set<String> tos = new HashSet<>();
            tos.add(randomToUsername);

            Set<EMKeyValue> exts = new HashSet<>();
            exts.add(EMKeyValue.of("key", "value"));

            EMSentMessageResults messageResults = this.service.message().sendWithoutMsgId(randomFromUsername, "users", tos, new EMTextMessage().text("你好"), exts, false).block(Utilities.IT_TIMEOUT);
            assertNotNull(messageResults.getMessageResultsByEntityId().get(randomToUsername));
        });
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendImage() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .image(msg -> msg.uri(URI.create("http://example/image.png"))
                        .height(1709.000)
                        .width(2573.000)
                        .secret("secret")
                        .displayName("image.png")
                )
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendVoice() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .voice(msg -> msg.uri(URI.create("http://example/voice.amr"))
                        .duration(3)
                        .secret("secret")
                        .displayName("voice.amr")
                        .bytes(5158)
                )
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendVideo() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .video(msg -> msg.uri(URI.create("http://example/video.mp4"))
                        .duration(3)
                        .secret("secret")
                        .displayName("video.mp4")
                        .thumb("http://example/videoThumbnail")
                        .thumbSecret("thumbSecret")
                )
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendFile() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .file(msg -> msg.uri(URI.create("http://example/file.txt"))
                        .secret("secret")
                        .displayName("file.txt")
                )
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendLocation() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .location(msg -> msg.latitude(1.234567).longitude(1.234567).address("some where"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendCommand() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .command(msg -> msg.action("run").param("name", "rabbit"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMessageSendCustom() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .custom(msg -> msg.customEvent("liked").customExtension("name", "forest"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    /**
     * 测试扩展字段ext支持Object类型
     */
    @Test
    void testExtensionSupportObject() throws InterruptedException {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        Map<String, Object> hashMap = new HashMap<>();
        hashMap.put("name", "forest");
        Set<EMKeyValue> kvSet = EMKeyValue.of(hashMap);
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .custom(msg -> msg.customEvent("liked").customExtensions(kvSet))
                .extension(exts -> exts.add(EMKeyValue.of("extension-object", new HashMap<String, String>() {
                    {
                        put("em_push_content", "custom-content");
                        put("extension_key", "extension-value");
                    }
                })))
                .send()
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testRecallMessage() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        List<RecallMessageSource> messageSourceList = new ArrayList<>();
        messageSourceList.add(new RecallMessageSource("13132131", "chat", randomFromUsername, randomToUsername, true));

        assertDoesNotThrow(() -> this.service.message().recallMessage(messageSourceList).block());
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testDeleteMessageChannel() {
        String randomFromUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomToUsername = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.message().deleteChannel(randomFromUsername, randomToUsername, "chat", true).block());
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Utilities.IT_TIMEOUT));
    }

}
