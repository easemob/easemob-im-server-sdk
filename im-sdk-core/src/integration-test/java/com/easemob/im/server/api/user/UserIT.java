package com.easemob.im.server.api.user;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.model.EMBlock;
import org.junit.jupiter.api.Test;

import java.time.Duration;
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
        assertThrows(EMNotFoundException.class, () -> this.service.user().get(randomUsername).block(Duration.ofSeconds(3)));
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
        String username = assertDoesNotThrow(() -> this.service.user().listAllUsers().blockLast(Duration.ofSeconds(10)));
        if (!username.equals(randomUsername)) {
            throw new RuntimeException(String.format("%s is not found in the user list", randomUsername));
        }
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
        String username = assertDoesNotThrow(() -> this.service.contact().list(randomUsername).blockLast(Duration.ofSeconds(3)));
        if (!username.equals(randomContactUsername)) {
            throw new RuntimeException(String.format("%s did not found %s in his contact list", randomUsername, randomContactUsername));
        }
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
        String username = assertDoesNotThrow(() -> this.service.contact().list(randomUsername).blockLast(Duration.ofSeconds(3)));
        if (username != null) {
            throw new RuntimeException(String.format("%s contact remove fail", username));
        }
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomContactUsername).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserContactList() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword).block(Duration.ofSeconds(3)));

        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomUsernameCodeJack).block(Duration.ofSeconds(3)));
        String username = assertDoesNotThrow(() -> this.service.contact().list(randomUsername)).blockFirst(Duration.ofSeconds(3));
        if (username == null) {
            throw new RuntimeException(String.format("%s contact list is null", randomUsername));
        }

        if (!username.equals(randomUsernameCodeJack)) {
            throw new RuntimeException(String.format("%s did not found %s in his contact list", randomUsername, randomUsernameCodeJack));
        }
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserGetUsersBlockedFromSendMsg() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword).block(Duration.ofSeconds(3)));

        assertDoesNotThrow(() -> this.service.block().blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername).block(Duration.ofSeconds(3)));
        EMBlock block = assertDoesNotThrow(() -> this.service.block().getUsersBlockedFromSendMsgToUser(randomUsername)).blockFirst(Duration.ofSeconds(3));
        if (!block.getUsername().equals(randomUsernameCodeJack)) {
            throw new RuntimeException(String.format("%s did not found %s in his block list", randomUsername, randomUsernameCodeJack));
        }
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack).block(Duration.ofSeconds(3)));
    }

    @Test
    void testUserBlockUserSendMsg() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword).block(Duration.ofSeconds(3)));

        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomUsernameCodeJack).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.block().blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername).block(Duration.ofSeconds(3)));
        EMBlock block = assertDoesNotThrow(() -> this.service.block().getUsersBlockedFromSendMsgToUser(randomUsername).blockLast(Duration.ofSeconds(3)));
        String username = assertDoesNotThrow(() -> this.service.contact().list(randomUsername).blockLast(Duration.ofSeconds(3)));
        if (!block.getUsername().equals(randomUsernameCodeJack)) {
            throw new RuntimeException(String.format("%s did not found %s in his block list", randomUsername, randomUsernameCodeJack));
        }

        if (username == null) {
            throw new RuntimeException(String.format("%s does not exist in %s contact list", randomUsernameCodeJack, randomUsername));
        }
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
        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomUsernameCodeJack).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.block().blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername).block(Duration.ofSeconds(3)));
        assertDoesNotThrow(() -> this.service.block().unblockUserSendMsgToUser(randomUsernameCodeJack, randomUsername).block(Duration.ofSeconds(3)));
        EMBlock block = assertDoesNotThrow(() -> this.service.block().getUsersBlockedFromSendMsgToUser(randomUsername).blockLast(Duration.ofSeconds(3)));
        String username = assertDoesNotThrow(() -> this.service.contact().list(randomUsername).blockLast(Duration.ofSeconds(3)));
        if (block != null) {
            throw new RuntimeException(String.format("%s unblock %s fail", randomUsername, randomUsernameCodeJack));
        }
        if (!username.equals(randomUsernameCodeJack)) {
            throw new RuntimeException(String.format("%s does not exist in %s contact list", randomUsernameCodeJack, randomUsername));
        }
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

    @Test
    void testUserOnlineStatus() {
        String randomUsername = String.format("im-sdk-it-user-%08d", ThreadLocalRandom.current().nextInt(100000000));
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword).block(Duration.ofSeconds(3)));
        boolean isOnline = assertDoesNotThrow(() -> this.service.user().isUserOnline(randomUsername).block(Duration.ofSeconds(3)));
        if (isOnline) {
            throw new RuntimeException(String.format("%s is online status", randomUsername));
        }
        assertDoesNotThrow(() -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(3)));
    }

}
