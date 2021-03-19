package com.easemob.im.server.api.user;

import com.easemob.im.server.api.AbstractIT;
import org.junit.jupiter.api.Test;

import java.time.Duration;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.*;

class UserIT extends AbstractIT {

    UserIT() {
        super();
    }

    @Test
    void testUserLifeCycles() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().get(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserForceLogout() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().forceLogoutAllDevices(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserUpdatePassword() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().updateUserPassword(randomUsername, "password").block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }


}
