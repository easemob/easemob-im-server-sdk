package com.easemob.im.server.api.notification;

import com.easemob.im.server.api.AbstractIT;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class NotificationIT extends AbstractIT {
    public NotificationIT() {
        super();
    }

    @Test
    public void testGetUserNotification() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.notification().getNoDisturbing(randomUsername).block(Duration.ofSeconds(3)));
    }
}
