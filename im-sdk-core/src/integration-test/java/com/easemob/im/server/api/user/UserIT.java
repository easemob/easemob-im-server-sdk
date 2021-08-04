package com.easemob.im.server.api.user;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.model.EMBlock;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.time.Duration;

import static org.junit.jupiter.api.Assertions.*;
import static com.easemob.im.server.utils.RandomMaker.makeRandomUserName;

class UserIT extends AbstractIT {

    UserIT() {
        super();
    }

    @Test
    void testUserLifeCycles() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().get(randomUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
        assertThrows(EMNotFoundException.class,
                () -> this.service.user().get(randomUsername).block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserForceLogout() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().forceLogoutAllDevices(randomUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserUpdatePassword() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().updateUserPassword(randomUsername, "password")
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserListUsers() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().listUsers(1, null).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
    }

    // TODO: enable this once we can use a clean appkey for tests
    // currently we use easemob-demo#easechatui for the gateway token007 tests and this appkey has too many users
    @Disabled
    void testUserListAll() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        String username = assertDoesNotThrow(
                () -> this.service.user().listAllUsers().blockLast(Duration.ofSeconds(30)));
        if (!username.equals(randomUsername)) {
            throw new RuntimeException(
                    String.format("%s is not found in the user list", randomUsername));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserContactAdd() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;

        String randomContactUsername = makeRandomUserName();
        String randomContactPassword = randomContactUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().create(randomContactUsername, randomContactPassword)
                        .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomContactUsername)
                .block(Duration.ofSeconds(10)));
        String username = assertDoesNotThrow(
                () -> this.service.contact().list(randomUsername).blockLast(Duration.ofSeconds(30)));
        if (!username.equals(randomContactUsername)) {
            throw new RuntimeException(
                    String.format("%s did not found %s in his contact list", randomUsername,
                            randomContactUsername));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomContactUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserContactRemove() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;

        String randomContactUsername = makeRandomUserName();
        String randomContactPassword = randomContactUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().create(randomContactUsername, randomContactPassword)
                        .block(Duration.ofSeconds(10)));

        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomContactUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.contact().remove(randomUsername, randomContactUsername)
                        .block(Duration.ofSeconds(10)));
        String username = assertDoesNotThrow(
                () -> this.service.contact().list(randomUsername).blockLast(Duration.ofSeconds(30)));
        if (username != null) {
            throw new RuntimeException(String.format("%s contact remove fail", username));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomContactUsername)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserContactList() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword)
                .block(Duration.ofSeconds(10)));

        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomUsernameCodeJack)
                .block(Duration.ofSeconds(10)));
        String username = assertDoesNotThrow(() -> this.service.contact().list(randomUsername))
                .blockFirst(Duration.ofSeconds(30));
        if (username == null) {
            throw new RuntimeException(String.format("%s contact list is null", randomUsername));
        }

        if (!username.equals(randomUsernameCodeJack)) {
            throw new RuntimeException(
                    String.format("%s did not found %s in his contact list", randomUsername,
                            randomUsernameCodeJack));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserGetUsersBlockedFromSendMsg() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword)
                .block(Duration.ofSeconds(10)));

        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername)
                .block(Duration.ofSeconds(10)));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedFromSendMsgToUser(randomUsername))
                .blockFirst(Duration.ofSeconds(30));
        if (!block.getUsername().equals(randomUsernameCodeJack)) {
            throw new RuntimeException(
                    String.format("%s did not found %s in his block list", randomUsername,
                            randomUsernameCodeJack));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserBlockUserSendMsg() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword)
                .block(Duration.ofSeconds(10)));

        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomUsernameCodeJack)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername)
                .block(Duration.ofSeconds(10)));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedFromSendMsgToUser(randomUsername)
                        .blockLast(Duration.ofSeconds(30)));
        String username = assertDoesNotThrow(
                () -> this.service.contact().list(randomUsername).blockLast(Duration.ofSeconds(30)));
        if (!block.getUsername().equals(randomUsernameCodeJack)) {
            throw new RuntimeException(
                    String.format("%s did not found %s in his block list", randomUsername,
                            randomUsernameCodeJack));
        }

        if (username == null) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s contact list", randomUsernameCodeJack,
                            randomUsername));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserUnblockUserSendMsg() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomUsernameCodeJack)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block()
                .unblockUserSendMsgToUser(randomUsernameCodeJack, randomUsername)
                .block(Duration.ofSeconds(10)));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedFromSendMsgToUser(randomUsername)
                        .blockLast(Duration.ofSeconds(30)));
        String username = assertDoesNotThrow(
                () -> this.service.contact().list(randomUsername).blockLast(Duration.ofSeconds(30)));
        if (block != null) {
            throw new RuntimeException(
                    String.format("%s unblock %s fail", randomUsername, randomUsernameCodeJack));
        }
        if (!username.equals(randomUsernameCodeJack)) {
            throw new RuntimeException(
                    String.format("%s does not exist in %s contact list", randomUsernameCodeJack,
                            randomUsername));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserCountMissedMessages() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;

        String randomUsernameCodeJack = makeRandomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword)
                .block(Duration.ofSeconds(10)));

        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomUsernameCodeJack)
                .toUser(randomUsername)
                .text(msg -> msg.text("offlineMessage"))
                .send()
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.message().countMissedMessages(randomUsername)
                .blockFirst(Duration.ofSeconds(30)));

        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack)
                .block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserBlockLogin() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block().blockUserLogin(randomUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserUnblockLogin() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block().blockUserLogin(randomUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(() -> this.service.block().unblockUserLogin(randomUsername)
                .block(Duration.ofSeconds(10)));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
    }

    @Test
    void testUserOnlineStatus() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Duration.ofSeconds(10)));
        boolean isOnline = assertDoesNotThrow(() -> this.service.user().isUserOnline(randomUsername)
                .block(Duration.ofSeconds(10)));
        if (isOnline) {
            throw new RuntimeException(String.format("%s is online status", randomUsername));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Duration.ofSeconds(10)));
    }

    @Test
    void testGetUserToken() {
        String randomUsername = makeRandomUserName();
        String randomPassword = randomUsername;
        assertDoesNotThrow(
                () -> this.service.user().create(randomUsername, randomPassword).block());
        String userId = this.service.user().getUUID(randomUsername).block();
        EMProperties.Realm realm = this.service.getContext().getProperties().getRealm();
        if (realm == EMProperties.Realm.AGORA_REALM) {
            assertDoesNotThrow(
                    () -> this.service.user()
                            .getToken(userId, 10, accessToken2 -> {}));
        } else {
            assertDoesNotThrow(
                    () -> this.service.user().getToken(randomUsername, randomPassword).block());
        }
    }

}
