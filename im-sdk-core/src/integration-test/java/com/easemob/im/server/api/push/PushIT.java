package com.easemob.im.server.api.push;

import com.easemob.im.server.api.AbstractIT;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.model.EMUser;
import org.junit.jupiter.api.Test;

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
        assertDoesNotThrow(() -> {
            this.service.push().updateUserNickname(randomUsername, String.format("nickname-%s",randomUsername))
                    .map(EMUser::getUsername)
                    .doOnNext(username -> assertEquals(username, randomUsername))
                    .block(Utilities.IT_TIMEOUT);
        });
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block(Utilities.IT_TIMEOUT));
    }
}
