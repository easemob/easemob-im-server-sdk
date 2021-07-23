package com.easemob.im.server.utils;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

public class RandomMaker {
    public static String makeRandomUserName() {
        return String.format("it-%08d-%08d",
                ThreadLocalRandom.current().nextInt(100000000),
                Instant.now().toEpochMilli()
        );
    }
}
