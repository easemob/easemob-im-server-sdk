package com.easemob.im.server.api.push;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.push.displaystyle.set.DisplayStyle;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.model.EMConversationType;
import com.easemob.im.server.model.EMNotificationType;
import com.easemob.im.server.model.EMPushNickname;
import com.easemob.im.server.model.EMUser;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        assertDoesNotThrow(() -> this.service.push()
                .updateUserNickname(randomUsername, String.format("nickname-%s", randomUsername))
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testUpdateUserNicknames() {
        String randomUsername = Utilities.randomUserName();
        String randomUsername1 = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername1, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        List<EMPushNickname> pushNicknames = new ArrayList<>();
        pushNicknames.add(new EMPushNickname(randomUsername, "推送昵称-1"));
        pushNicknames.add(new EMPushNickname(randomUsername1, "推送昵称-2"));

        assertDoesNotThrow(() -> this.service.push().updateUserNicknames(pushNicknames)
                .block(Utilities.IT_TIMEOUT));

        EMUser user = this.service.user().get(randomUsername).block(Utilities.IT_TIMEOUT);
        assertEquals("推送昵称-1", user.getPushNickname());

        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername1).block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testNotificationDisplayStyle() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.push()
                .setNotificationDisplayStyle(randomUsername, DisplayStyle.DETAILS)
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
        assertDoesNotThrow(
                () -> this.service.push().openNotificationNoDisturbing(randomUsername, 10, 15)
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

    @Test
    void testOfflinePushSetting() {
        String randomUsername = Utilities.randomUserName();
        String randomUsername1 = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername1, randomPassword)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(() -> this.service.push()
                .offlineSetting(randomUsername, EMConversationType.USER, randomUsername1,
                        EMNotificationType.NONE, "08:30-10:00", 120000)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername1).block(Utilities.IT_TIMEOUT));
    }
}
