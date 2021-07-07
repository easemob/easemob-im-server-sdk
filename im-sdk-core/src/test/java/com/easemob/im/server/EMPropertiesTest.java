package com.easemob.im.server;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EMPropertiesTest {
    @Test
    public void testBuildEMPropertiesSuccessfully() {
        EMProperties properties = EMProperties.builder()
                .setAppkey("easemob#demo")
                .setClientId("id")
                .setClientSecret("secret")
                .build();
        assertEquals("easemob#demo", properties.getAppkey());
        assertEquals("id", properties.getClientId());
        assertEquals("secret", properties.getClientSecret());
    }

    @Test
    public void testInvalidAppkeyOfEmpty() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().setAppkey(""));
    }

    @Test
    public void testInvalidAppkeyOfNull() {
        assertThrows(EMInvalidArgumentException.class,
                () -> EMProperties.builder().setAppkey(null));
    }

    @Test
    public void testInvalidClientIdOfEmpty() {
        assertThrows(EMInvalidArgumentException.class,
                () -> EMProperties.builder().setClientId(""));
    }

    @Test
    public void testInvalidClientIdOfNull() {
        assertThrows(EMInvalidArgumentException.class,
                () -> EMProperties.builder().setClientId(null));
    }

    @Test
    public void testInvalidClientSecretOfEmpty() {
        assertThrows(EMInvalidArgumentException.class,
                () -> EMProperties.builder().setClientSecret(""));
    }

    @Test
    public void testInvalidClientSecretOfNull() {
        assertThrows(EMInvalidArgumentException.class,
                () -> EMProperties.builder().setClientSecret(null));
    }

    @Test
    public void testNotSettingAppkey() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
                .setClientId("id")
                .setClientSecret("secret")
                .build());
    }

    @Test
    public void testNotSettingClientId() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
                .setAppkey("easemob#demo")
                .setClientSecret("secret")
                .build());
    }

    @Test
    public void testNotSettingClientSecret() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
                .setAppkey("easemob#demo")
                .setClientId("id")
                .build());
    }
}
