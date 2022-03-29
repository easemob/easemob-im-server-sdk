package com.easemob.im.server.api.mute;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.mute.mute.MuteUserRequest;
import com.easemob.im.server.api.util.Utilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MuteIT extends AbstractIT {

    MuteIT() {
        super();
    }

    @Test
    void testMuteUser() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.mute()
                .muteUser(MuteUserRequest.builder().username(randomUsername).chat(100).build())
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.user().delete(randomUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMuteDetail() {
        String randomUsername = Utilities.randomUserName();
        String randomPassword = Utilities.randomPassword();

        assertDoesNotThrow(() -> this.service.user().create(randomUsername, randomPassword)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.mute().detail(randomUsername)
                .block(Utilities.IT_TIMEOUT));

        assertDoesNotThrow(() -> this.service.user().delete(randomUsername)
                .block(Utilities.IT_TIMEOUT));
    }

    @Test
    void testMuteList() {
        assertDoesNotThrow(() -> this.service.mute().muteList().block(Utilities.IT_TIMEOUT));
    }
}
