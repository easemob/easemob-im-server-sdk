package com.easemob.im.server.api.metadata;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.model.EMBatchMetadata;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

        String randomUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(randomUsername, map)
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    public void testMetadataGet() {
        Map<String, String> map = new HashMap<>();
        map.put("nickname", "昵称");
        map.put("avatar", "http://www.easemob.com/avatar.png");
        map.put("phone", "159");

        String randomUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(randomUsername, map)
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().getMetadataFromUser(randomUsername)
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    public void testMetadataBatchGet() {
        Map<String, String> aliceData = new HashMap<>();
        aliceData.put("name", "alice");
        aliceData.put("title", "java");
        aliceData.put("employer", "easemob");

        Map<String, String> bobData = new HashMap<>();
        bobData.put("name", "bob");
        bobData.put("gender", "male");
        bobData.put("phone", "000-000-0000");
        bobData.put("zip", "61801");

        String password = "password";

        String aliceName = String.format("it-%08d-%08d",
                ThreadLocalRandom.current().nextInt(100000000),
                Instant.now().toEpochMilli());

        String bobName = String.format("it-%08d-%08d",
                ThreadLocalRandom.current().nextInt(100000000),
                Instant.now().toEpochMilli());

        // post users and metadata
        assertDoesNotThrow(() -> this.service.user().create(aliceName, password)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(aliceName, aliceData)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.user().create(bobName, password)
                .block(Duration.ofSeconds(30)));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(bobName, bobData)
                .block(Duration.ofSeconds(30)));

        // batch get metadata from users
        EMBatchMetadata batchMetadata =
                assertDoesNotThrow(() -> this.service.metadata().getMetadataFromUsers(
                        Arrays.asList(aliceName, bobName),
                        Arrays.asList("name", "title", "zip")).block(Duration.ofSeconds(30)));
        Map<String, Map<String, String>> data = batchMetadata.getData();
        assertEquals(2, data.size());

        // check results
        Map<String, String> aliceMetadata = data.get(aliceName);
        assertEquals(2, aliceMetadata.size());
        assertEquals("alice", aliceMetadata.get("name"));
        assertEquals("java", aliceMetadata.get("title"));

        Map<String, String> bobMetadata = data.get(bobName);
        assertEquals(2, bobMetadata.size());
        assertEquals("bob", bobMetadata.get("name"));
        assertEquals("61801", bobMetadata.get("zip"));

        // delete users
        assertDoesNotThrow(
                () -> this.service.user().delete(aliceName).block(Duration.ofSeconds(30)));
        assertDoesNotThrow(
                () -> this.service.user().delete(bobName).block(Duration.ofSeconds(30)));
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

        String randomUsername = String.format("im-sdk-it-user-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().setMetadataToUser(randomUsername, map)
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().getMetadataFromUser(randomUsername)
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.metadata().deleteMetadataFromUser(randomUsername)
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

}
