package com.easemob.im.server.api.user;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.model.EMBlock;
import com.easemob.im.server.model.EMCreateUser;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserIT extends AbstractIT {

    UserIT() {
        super();
    }

    @Test
    void testUserLifeCycles() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().get(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
        assertThrows(EMNotFoundException.class,
                () -> this.service.user().get(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testBatchCreateUser() {
        String randomUsername = Utilities.randomUserName();
        String randomUsername1 = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        List<EMCreateUser> createUserList = new ArrayList<>();
        EMCreateUser createUser = new EMCreateUser(randomUsername, randomPassword);
        EMCreateUser createUser1 = new EMCreateUser(randomUsername1, randomPassword);
        createUserList.add(createUser);
        createUserList.add(createUser1);

        assertDoesNotThrow(() -> this.service.user().create(createUserList)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().get(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().get(randomUsername1).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername1).block(Utilities.IT_TIMEOUT));
        assertThrows(EMNotFoundException.class,
                () -> this.service.user().get(randomUsername).block(Utilities.IT_TIMEOUT));
        assertThrows(EMNotFoundException.class,
                () -> this.service.user().get(randomUsername1).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserForceLogout() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().forceLogoutAllDevices(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserUpdatePassword() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().updateUserPassword(randomUsername, "password")
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserListUsers() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().listUsers(1, null).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    // TODO: enable this once we can use a clean appkey for tests
    // currently we use easemob-demo#easechatui for the gateway token007 tests and this appkey has too many users
    @Disabled
    void testUserListAll() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        String username = assertDoesNotThrow(
                () -> this.service.user().listAllUsers().blockLast(Utilities.IT_TIMEOUT));
        if (!username.equals(randomUsername)) {
            throw new RuntimeException(
                    String.format("%s is not found in the user list", randomUsername));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserContactAdd() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomContactUsername = Utilities.randomUserName();
        String randomContactPassword = randomContactUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().create(randomContactUsername, randomContactPassword)
                        .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomContactUsername)
                .block(Utilities.IT_TIMEOUT));
        String username = assertDoesNotThrow(
                () -> this.service.contact().list(randomUsername).blockLast(Utilities.IT_TIMEOUT));
        if (!username.equals(randomContactUsername)) {
            throw new RuntimeException(
                    String.format("%s did not found %s in his contact list", randomUsername,
                            randomContactUsername));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomContactUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserContactRemove() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomContactUsername = Utilities.randomUserName();
        String randomContactPassword = randomContactUsername;
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().create(randomContactUsername, randomContactPassword)
                        .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomContactUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.contact().remove(randomUsername, randomContactUsername)
                        .block(Utilities.IT_TIMEOUT));
        String username = assertDoesNotThrow(
                () -> this.service.contact().list(randomUsername).blockLast(Utilities.IT_TIMEOUT));
        if (username != null) {
            throw new RuntimeException(String.format("%s contact remove fail", username));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomContactUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserContactList() {
        // add bob to alice's contact list
        String aliceUserName = Utilities.randomUserName();
        String bobUserName = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        assertDoesNotThrow(() -> this.service.user().create(aliceUserName, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(bobUserName, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.contact().add(aliceUserName, bobUserName)
                .block(Utilities.IT_TIMEOUT));
        String aliceFirstFriend =
                assertDoesNotThrow(() -> this.service.contact().list(aliceUserName))
                        .blockFirst(Utilities.IT_TIMEOUT);
        if (aliceFirstFriend == null) {
            throw new RuntimeException(String.format("%s contact list is null", aliceUserName));
        }

        if (!aliceFirstFriend.equals(bobUserName)) {
            throw new RuntimeException(String.format("%s did not found %s in his contact list",
                    aliceUserName, bobUserName));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(aliceUserName).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(bobUserName)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserGetUsersBlockedFromSendMsg() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomUsernameCodeJack = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername)
                .block(Utilities.IT_TIMEOUT));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedFromSendMsgToUser(randomUsername))
                .blockFirst(Utilities.IT_TIMEOUT);
        if (!block.getUsername().equals(randomUsernameCodeJack)) {
            throw new RuntimeException(
                    String.format("%s did not found %s in his block list", randomUsername,
                            randomUsernameCodeJack));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserBlockUserSendMsg() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomUsernameCodeJack = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomUsernameCodeJack)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername)
                .block(Utilities.IT_TIMEOUT));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedFromSendMsgToUser(randomUsername)
                        .blockLast(Utilities.IT_TIMEOUT));
        String username = assertDoesNotThrow(
                () -> this.service.contact().list(randomUsername).blockLast(Utilities.IT_TIMEOUT));
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
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserUnblockUserSendMsg() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomUsernameCodeJack = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.contact().add(randomUsername, randomUsernameCodeJack)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block()
                .blockUserSendMsgToUser(randomUsernameCodeJack, randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block()
                .unblockUserSendMsgToUser(randomUsernameCodeJack, randomUsername)
                .block(Utilities.IT_TIMEOUT));
        EMBlock block = assertDoesNotThrow(
                () -> this.service.block().getUsersBlockedFromSendMsgToUser(randomUsername)
                        .blockLast(Utilities.IT_TIMEOUT));
        String username = assertDoesNotThrow(
                () -> this.service.contact().list(randomUsername).blockLast(Utilities.IT_TIMEOUT));
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
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserCountMissedMessages() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        String randomUsernameCodeJack = Utilities.randomUserName();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomUsernameCodeJack, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.message().send()
                .fromUser(randomUsernameCodeJack)
                .toUser(randomUsername)
                .text(msg -> msg.text("offlineMessage"))
                .send()
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.message().countMissedMessages(randomUsername)
                .blockFirst(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().delete(randomUsernameCodeJack)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserBlockLogin() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block().blockUserLogin(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserUnblockLogin() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block().blockUserLogin(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.block().unblockUserLogin(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUserOnlineStatus() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        boolean isOnline = assertDoesNotThrow(() -> this.service.user().isUserOnline(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        if (isOnline) {
            throw new RuntimeException(String.format("%s is online status", randomUsername));
        }
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testIsUsersOnline() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().isUsersOnline(Arrays.asList(randomUsername))
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testGetUserToken() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(
                () -> this.service.user().create(randomUsername, randomPassword)
                        .block(Utilities.IT_TIMEOUT));
        // notice the deprecated stuff
        assertDoesNotThrow(() -> this.service.user().getToken(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
    }


    @Test
    public void testGetUserTokenWithInherit() throws Exception {
        String randomUsername = Utilities.randomUserName();
        // notice the deprecated stuff
        assertDoesNotThrow(() -> {
            service.token().getUserTokenWithInherit(randomUsername);
        });
        assertDoesNotThrow(() -> {
            service.user().get(randomUsername).block(Utilities.IT_TIMEOUT);
        });
        assertDoesNotThrow(() -> {
            service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT);
        });
    }

    @Test
    public void testGetUserTokenTtlWithInherit() throws Exception {
        String randomUsername = Utilities.randomUserName();
        // notice the deprecated stuff
        assertDoesNotThrow(() -> {
            service.token().getUserTokenWithInherit(randomUsername, 1000);
        });
        assertDoesNotThrow(() -> {
            service.user().get(randomUsername).block(Utilities.IT_TIMEOUT);
        });
        assertDoesNotThrow(() -> {
            service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT);
        });
    }

}
