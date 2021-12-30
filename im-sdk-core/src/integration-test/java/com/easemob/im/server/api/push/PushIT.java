package com.easemob.im.server.api.push;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.push.displaystyle.set.DisplayStyle;
import com.easemob.im.server.api.util.Utilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class PushIT extends AbstractIT {

    PushIT() {
        super();
    }

    @Test
    void testUpdateUserNickname() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.push().updateUserNickname(randomUsername, String.format("nickname-%s",randomUsername))
                    .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testNotificationDisplayStyle() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.push().setNotificationDisplayStyle(randomUsername, DisplayStyle.DETAILS)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testOpenNotificationNoDisturbing() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.push().openNotificationNoDisturbing(randomUsername, 10, 15)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testCloseNotificationNoDisturbing() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.push().closeNotificationNoDisturbing(randomUsername)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }
}
