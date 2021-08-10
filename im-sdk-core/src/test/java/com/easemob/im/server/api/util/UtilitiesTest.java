package com.easemob.im.server.api.util;

import org.junit.jupiter.api.Test;

import static com.easemob.im.server.api.util.Utilities.randomPassword;
import static com.easemob.im.server.api.util.Utilities.randomUserName;

public class UtilitiesTest {

    @Test
    public void randomUserNameTest() {
        for (int i = 0; i < 10; i ++) System.out.println(randomUserName());
    }

    @Test
    public void randomPasswordTest() {
        for (int i = 0; i < 10; i ++) System.out.println(randomPassword());
    }

    @Test
    public void toExpireOnSecondsTest() {
    }

    @Test
    public void maskTest() {
    }
}
