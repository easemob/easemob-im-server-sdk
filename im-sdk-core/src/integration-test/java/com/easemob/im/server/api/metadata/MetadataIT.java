package com.easemob.im.server.api.metadata;

import com.easemob.im.server.api.AbstractIT;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MetadataIT extends AbstractIT {
    public MetadataIT() {
        super();
    }

    @Test
    public void testMetadataSet() {

        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(randomUsername, map).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    public void testMetadataGet() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(randomUsername, map).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().getMetadataFromUser(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    public void testMetadataGetUsage() {
        assertDoesNotThrow(() -> this.service.metadata().getUsage().block(Duration.ofSeconds(3)));
    }

    @Test
    public void testMetadataDelete() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(randomUsername, map).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().getMetadataFromUser(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().deleteMetadataFromUser(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

}
