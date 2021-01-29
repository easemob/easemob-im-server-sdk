package com.easemob.im.server;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class EMServiceTest {

    @Test
    public void testPrintBanner() {
        EMProperties properties = EMProperties.builder()
            .baseUri("https://a1.easemob.com")
            .appkey("easemob#demo")
            .clientId("id")
            .clientSecret("secret")
            .build();
        new EMService(properties);
    }
}