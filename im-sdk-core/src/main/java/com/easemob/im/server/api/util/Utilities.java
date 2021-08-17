package com.easemob.im.server.api.util;

import org.apache.logging.log4j.util.Strings;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

public class Utilities {

    public static final Duration IT_TIMEOUT = Duration.ofSeconds(60);
    public static final Duration UT_TIMEOUT = Duration.ofSeconds(3);

    public static String randomUserName() {
        return String.format("it-%d-%d",
                ThreadLocalRandom.current().nextInt(100000000),
                Instant.now().toEpochMilli()
        );
    }

    public static String randomPassword() {
        return String.format("it-password-%d-%d",
                ThreadLocalRandom.current().nextInt(10000),
                Instant.now().getEpochSecond()
        );
    }

    public static int toExpireOnSeconds(int expireInSeconds) {
        return (int) Instant.now().plusSeconds(expireInSeconds).getEpochSecond();
    }

    public static String mask(String text) {
        if (Strings.isBlank(text)) {
            return text;
        } else {
            return text.replaceAll(".", "*");
        }
    }
}
