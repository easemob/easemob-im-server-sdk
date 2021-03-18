package com.easemob.im.server.model;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EMUserTest {
    @Test
    public void testUsernameValidate() {
        assertDoesNotThrow(() -> EMUser.validateUsername("usr-0001"));
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validateUsername(null)); // null
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validateUsername("")); // empty
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validateUsername("000000000000000000000000000000000")); // too long, 32 bytes max
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validateUsername("USR00001")); // invalid character
    }

    @Test
    public void testPasswordValidate() {
        assertDoesNotThrow(() -> EMUser.validatePassword("0xBEEF?!?!"));
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword(null)); // null
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("")); // empty
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("000000000000000000000000000000000")); // too long, 32 bytes max
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("\r\n")); // invalid character
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("\"")); // invalid character
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("\\")); // invalid character
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("'")); // invalid character
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("`")); // invalid character
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("(")); // invalid character
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("[")); // invalid character
        assertThrows(EMInvalidArgumentException.class, () -> EMUser.validatePassword("{")); // invalid character
    }
}