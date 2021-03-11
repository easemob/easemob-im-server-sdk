package com.easemob.im.server;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EMPropertiesTest {
    @Test
    public void testBuildEMPropertiesSuccessfully() {
        EMProperties properties = EMProperties.builder()
            .setBaseUri("https://a1.easemob.com")
            .setAppkey("easemob#demo")
            .setClientId("id")
            .setClientSecret("secret")
            .build();
        assertEquals("https://a1.easemob.com/easemob/demo", properties.getBaseUri());
        assertEquals("easemob#demo", properties.getAppkey());
        assertEquals( "id", properties.getClientId());
        assertEquals("secret", properties.getClientSecret());
    }

    @Test
    public void testTrimmingBaseUri() {
        EMProperties properties = EMProperties.builder()
            .setBaseUri("https://a1.easemob.com/")
            .setAppkey("easemob#demo")
            .setClientId("id")
            .setClientSecret("secret")
            .build();
        assertEquals("https://a1.easemob.com/easemob/demo", properties.getBaseUri());
        assertEquals("easemob#demo", properties.getAppkey());
        assertEquals( "id", properties.getClientId());
        assertEquals("secret", properties.getClientSecret());
    }

    @Test
    public void testInvalidBaseUriOfEmpty() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().setBaseUri(""));
    }

    @Test
    public void testInvalidBaseUriOfNull() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().setBaseUri(null));
    }


    @Test
    public void testInvalidAppkeyOfEmpty() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().setAppkey(""));
    }

    @Test
    public void testInvalidAppkeyOfNull() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().setAppkey(null));
    }


    @Test
    public void testInvalidClientIdOfEmpty() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().setClientId(""));
    }

    @Test
    public void testInvalidClientIdOfNull() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().setClientId(null));
    }


    @Test
    public void testInvalidClientSecretOfEmpty() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().setClientSecret(""));
    }

    @Test
    public void testInvalidClientSecretOfNull() {
        assertThrows(EMInvalidArgumentException.class, () -> EMProperties.builder().setClientSecret(null));
    }

    @Test
    public void testNotSettingBaseUri() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
            .setAppkey("easemob#demo")
            .setClientId("id")
            .setClientSecret("secret")
            .build());
    }

    @Test
    public void testNotSettingAppkey() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
            .setBaseUri("https://a1.easemob.com")
            .setClientId("id")
            .setClientSecret("secret")
            .build());
    }

    @Test
    public void testNotSettingClientId() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
            .setBaseUri("https://a1.easemob.com")
            .setAppkey("easemob#demo")
            .setClientSecret("secret")
            .build());
    }

    @Test
    public void testNotSettingClientSecret() {
        assertThrows(EMInvalidStateException.class, () -> EMProperties.builder()
            .setBaseUri("https://a1.easemob.com")
            .setAppkey("easemob#demo")
            .setClientId("id")
            .build());
    }
}