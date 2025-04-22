package com.easemob.im.api;

import com.easemob.im.ApiClient;
import com.easemob.im.ApiException;
import com.easemob.im.Configuration;

import java.time.Instant;
import java.util.concurrent.ThreadLocalRandom;

public abstract class AbstractTest {
    static {
        try {
            Configuration.setDefaultApiClient(ApiClient.builder()
                    .setBasePath(System.getenv("IM_BASE_URI"))
                    .setAppKey(System.getenv("IM_APPKEY"))
                    .setClientId(System.getenv("IM_CLIENT_ID"))
                    .setClientSecret(System.getenv("IM_CLIENT_SECRET"))
                    .build());
        } catch (ApiException e) {
            throw new RuntimeException(e);
        }
    }

    protected static String randomUserName() {
        return String.format("user-%d-%d",
                ThreadLocalRandom.current().nextInt(100000000),
                Instant.now().toEpochMilli()
        );
    }
}
