package com.easemob.im.server.api.util;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.model.EMUser;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UtilitiesTest {

    @Test
    public void illegalUserName() {
        String uuidUserName = "eaf4b5b4-7445-40f9-bc4d-78a1a6049269";
        assertThrows(EMInvalidArgumentException.class,()->EMUser.validateUsername(uuidUserName));
        String startWithDigits = "000" + Utilities.randomUserName();
        assertThrows(EMInvalidArgumentException.class,()->EMUser.validateUsername(startWithDigits));
    }

    @Test
    public void legalUserName() {
        String userName = "it-username-1234567890";
        assertDoesNotThrow(()->EMUser.validateUsername(userName));
    }
}
