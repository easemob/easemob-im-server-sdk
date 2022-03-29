package com.easemob.im.server.api.mute.mute;

import com.easemob.im.server.api.AbstractApiTest;
import com.easemob.im.server.api.util.Utilities;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class MuteUserTest extends AbstractApiTest {

    MuteUser muteUser = new MuteUser(this.context);

    @Test
    public void testGetMuteDetail() {
        assertDoesNotThrow(
                () -> this.muteUser.execute(MuteUserRequest.builder().username("bob").chatroom(100).build()).block(Utilities.UT_TIMEOUT));
    }
}
