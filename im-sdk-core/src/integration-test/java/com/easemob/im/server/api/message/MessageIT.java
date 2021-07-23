package com.easemob.im.server.api.message;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.model.EMKeyValue;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static com.easemob.im.server.utils.RandomMaker.makeRandomUserName;

public class MessageIT extends AbstractIT {

    MessageIT() {
        super();
    }

    @Test
    void testMessageSendText() {
        String randomFromUsername = makeRandomUserName();
        String randomPassword = randomFromUsername;

        String randomToUsername = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .text(msg -> msg.text("hello"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(30)));
    }

    @Test
    void testMessageSendImage() {
        String randomFromUsername = makeRandomUserName();
        String randomPassword = randomFromUsername;

        String randomToUsername = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
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
                .block(Duration.ofSeconds(6)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(30)));
    }

    @Test
    void testMessageSendVoice() {
        String randomFromUsername = makeRandomUserName();
        String randomPassword = randomFromUsername;

        String randomToUsername = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
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
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(30)));
    }

    @Test
    void testMessageSendVideo() {
        String randomFromUsername = makeRandomUserName();
        String randomPassword = randomFromUsername;

        String randomToUsername = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
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
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(30)));
    }

    @Test
    void testMessageSendFile() {
        String randomFromUsername = makeRandomUserName();
        String randomPassword = randomFromUsername;

        String randomToUsername = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .file(msg -> msg.uri(URI.create("http://example/file.txt"))
                        .secret("secret")
                        .displayName("file.txt")
                )
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(30)));
    }

    @Test
    void testMessageSendLocation() {
        String randomFromUsername = makeRandomUserName();
        String randomPassword = randomFromUsername;

        String randomToUsername = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .location(msg -> msg.latitude(1.234567).longitude(1.234567).address("some where"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(30)));
    }

    @Test
    void testMessageSendCommand() {
        String randomFromUsername = makeRandomUserName();
        String randomPassword = randomFromUsername;

        String randomToUsername = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .command(msg -> msg.action("run").param("name", "rabbit"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(30)));
    }

    @Test
    void testMessageSendCustom() {
        String randomFromUsername = makeRandomUserName();
        String randomPassword = randomFromUsername;

        String randomToUsername = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomFromUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(randomToUsername, randomPassword)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomFromUsername)
                .toUser(randomToUsername)
                .custom(msg -> msg.customEvent("liked").customExtension("name", "forest"))
                .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
                .send()
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomFromUsername).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomToUsername).block(Duration.ofSeconds(30)));
    }

}
