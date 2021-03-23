package com.easemob.im.server.api.user;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.model.EMUser;
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

    @Test
    void testUserListUsers() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().listUsers(1, null).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserListAll() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().listAllUsers().blockFirst(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserContactAdd() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;

        String randomContactUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomContactPassword = randomContactUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomContactUsername, randomContactPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomContactUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomContactUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserContactRemove() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;

        String randomContactUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomContactPassword = randomContactUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomContactUsername, randomContactPassword).block(Duration.ofSeconds(3)));

        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomContactUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.contact().remove(randomUsername, randomContactUsername).block(Duration.ofSeconds(3)));

        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomContactUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserContactList() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomUsernameCodeTom = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeTom, randomPassword).block(Duration.ofSeconds(3)));

        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomUsernameCodeJack).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomUsernameCodeTom).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.contact().list(randomUsername)).blockFirst(Duration.ofSeconds(3));

        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeTom).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserGetUsersBlockedFromSendMsg() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomUsernameCodeTom = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeTom, randomPassword).block(Duration.ofSeconds(3)));

        assertDoesNotThrow(() -> this.service.block().blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.block().blockUserSendMsgToUser(randomUsernameCodeTom, randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.block().getUsersBlockedFromSendMsgToUser(randomUsername)).blockFirst(Duration.ofSeconds(3));

        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeTom).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserBlockUserSendMsg() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.block().blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserUnblockUserSendMsg() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.block().blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.block().unblockUserSendMsgToUser(randomUsernameCodeJack, randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserCountMissedMessages() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword).block(Duration.ofSeconds(3)));

        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomUsernameCodeJack)
                .toUser(randomUsername)
                .text(msg -> msg.text("offlineMessage"))
                .send()
                .block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.message().countMissedMessages(randomUsername).blockFirst(Duration.ofSeconds(3)));

        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserBlockLogin() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.block().blockUserLogin(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserUnblockLogin() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.block().blockUserLogin(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.block().unblockUserLogin(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }
}
