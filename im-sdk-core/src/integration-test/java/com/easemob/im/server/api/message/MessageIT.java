package com.easemob.im.server.api.message;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.model.EMKeyValue;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MessageIT extends AbstractIT {

    MessageIT() {
        super();
    }

    @Test
    void testMessageSendText() {
        String randomFromUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomFromUsername;

        String randomToUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .text(msg -> msg.text("hello"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testMessageSendImage() {
        String randomFromUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomFromUsername;

        String randomToUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .image(msg -> msg.uri(URI.create("http://example/image.jpg")).height(100.000).width(200.000))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testMessageSendVoice() {
        String randomFromUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomFromUsername;

        String randomToUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .voice(msg -> msg.uri(URI.create("http://example/voice.amr")).duration(18))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testMessageSendVideo() {
        String randomFromUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomFromUsername;

        String randomToUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .video(msg -> msg.uri(URI.create("http://example/video.mp4")).duration(18))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testMessageSendLocation() {
        String randomFromUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomFromUsername;

        String randomToUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .location(msg -> msg.latitude(1.234567).longitude(1.234567).address("some where"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testMessageSendCommand() {
        String randomFromUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomFromUsername;

        String randomToUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .command(msg -> msg.action("run").param("name", "rabbit"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testMessageSendCustom() {
        String randomFromUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomFromUsername;

        String randomToUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .custom(msg -> msg.customEvent("liked").customExtension("name", "forest"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(3)));
    }

}
