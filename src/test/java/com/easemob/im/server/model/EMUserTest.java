package com.easemob.im.server.model;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EMUserTest {
    @Test
    public void testUsernameValidate() {
        assertDoesNotThrow(() -> EMUser.validateUsername("usr-0001"));
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validateUsername("0000001"));
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validateUsername("00000001"));
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validateUsername("usr1"));
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validateUsername("USR00001"));
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validateUsername("usr000000000000000000000000000001"));
    }

    @Test
    public void testPasswordValidate() {
        assertDoesNotThrow(() -> EMUser.validatePassword("0xBEEF?!?!"));
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("0000"));
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("000000000000000000000000000000000"));
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("000,AAAA"));
    }
}