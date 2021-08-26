package com.easemob.im.server.api.user;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.EMService;
import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserNameIT {

    EMService serviceWithUserNameValidation;
    EMService serviceNoUserNameValidation;

    public UserNameIT() {
        String realm = System.getenv("IM_REALM");
        String appkey = System.getenv("IM_APPKEY");
        String baseUri = System.getenv("IM_BASE_URI");

        EMProperties propertiesWithUserNameValidation = null;
        EMProperties propertiesNoUserNameValidation = null;

        if (realm != null && realm.equals(EMProperties.Realm.AGORA_REALM.toString())) {
            String appId = System.getenv("IM_APP_ID");
            String appCert = System.getenv("IM_APP_CERT");

            propertiesWithUserNameValidation = EMProperties.builder()
                    .setBaseUri(baseUri)
                    .setRealm(EMProperties.Realm.AGORA_REALM)
                    .setAppkey(appkey)
                    .setAppId(appId)
                    .setAppCert(appCert)
                    .build();

            propertiesNoUserNameValidation = EMProperties.builder()
                    .turnOffUserNameValidation()
                    .setBaseUri(baseUri)
                    .setRealm(EMProperties.Realm.AGORA_REALM)
                    .setAppkey(appkey)
                    .setAppId(appId)
                    .setAppCert(appCert)
                    .build();

        } else {
            String clientId = System.getenv("IM_CLIENT_ID");
            String clientSecret = System.getenv("IM_CLIENT_SECRET");

            propertiesWithUserNameValidation = EMProperties.builder()
                    .setRealm(EMProperties.Realm.EASEMOB_REALM)
                    .setBaseUri(baseUri)
                    .setAppkey(appkey)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setHttpConnectionPoolSize(10)
                    .setServerTimezone("+8")
                    .build();

            propertiesNoUserNameValidation = EMProperties.builder()
                    .turnOffUserNameValidation()
                    .setRealm(EMProperties.Realm.EASEMOB_REALM)
                    .setBaseUri(baseUri)
                    .setAppkey(appkey)
                    .setClientId(clientId)
                    .setClientSecret(clientSecret)
                    .setHttpConnectionPoolSize(10)
                    .setServerTimezone("+8")
                    .build();
        }

        this.serviceWithUserNameValidation = new EMService(propertiesWithUserNameValidation);
        this.serviceNoUserNameValidation = new EMService(propertiesNoUserNameValidation);
    }

    @Test
    public void withUserNameValidation() {
        String goodUserName = Utilities.randomUserName();
        String badUserName = "000" + Utilities.randomUserName();
        String password = Utilities.randomPassword();
        assertThrows(EMInvalidArgumentException.class,
                () -> serviceWithUserNameValidation.user().create(badUserName, password)
                        .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(()-> serviceWithUserNameValidation.user().create(goodUserName, password)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(()-> serviceWithUserNameValidation.user().delete(goodUserName)
                .block(Utilities.IT_TIMEOUT));

    }

    @Test
    public void noUserNameValidation() {
        String goodUserName = Utilities.randomUserName();
        String badUserName = "000" + Utilities.randomUserName();
        String password = Utilities.randomPassword();
        assertDoesNotThrow(()-> serviceNoUserNameValidation.user().create(goodUserName, password)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(()-> serviceNoUserNameValidation.user().create(badUserName, password)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(()-> serviceNoUserNameValidation.user().delete(goodUserName)
                .block(Utilities.IT_TIMEOUT));
        assertDoesNotThrow(()-> serviceNoUserNameValidation.user().delete(badUserName)
                .block(Utilities.IT_TIMEOUT));
    }
}
