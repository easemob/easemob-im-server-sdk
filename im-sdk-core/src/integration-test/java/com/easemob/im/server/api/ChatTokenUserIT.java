package com.easemob.im.server.api;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;
import com.easemob.im.server.exception.EMNotFoundException;
import org.junit.jupiter.api.Test;

import java.util.concurrent.ThreadLocalRandom;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class ChatTokenUserIT {

    private static final String PASSWORD = "password";
    private static final String BASE_URI = "http://hsb-didi-guangzhou-mesos-slave4:31032";
    private static final String APP_KEY = "easemob-demo#chatdemoui";
    private static final String APP_ID = "878d505d62284294aadfead9a898e1df";
    private static final String APP_CERTIFICATE = "8782beade2354c76b6e07669622f5712";

    private EMService service;

    ChatTokenUserIT() {
        EMProperties properties = EMProperties.builder(EMProperties.Realm.AGORA_REALM)
                .setBaseUri(BASE_URI)
                .setAppkey(APP_KEY)
                .setAppId(APP_ID)
                .setAppCertificate(APP_CERTIFICATE)
                .setHttpConnectionPoolSize(10)
                .setServerTimezone("+8")
                .build();

        this.service = new EMService(properties);
    }

    @Test
    public void userCrud() {
        String randomUsername = String.format("im-sdk-it-ken-%08d",
                ThreadLocalRandom.current().nextInt(100000000));
        assertDoesNotThrow(() -> this.service.user().create(randomUsername, PASSWORD)
                .block());
        assertDoesNotThrow(
                () -> this.service.user().get(randomUsername).block());
        assertDoesNotThrow(
                () -> this.service.user().delete(randomUsername).block());
        assertThrows(EMNotFoundException.class,
                () -> this.service.user().get(randomUsername).block());
    }
}

