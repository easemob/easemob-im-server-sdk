package com.easemob.im.server;

import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import org.apache.logging.log4j.util.Strings;

public class EasemobAppCredentials implements Credentials {
    private final String appKey;
    private final String clientId;
    private final String clientSecret;

    public EasemobAppCredentials(String appKey, String clientId, String clientSecret) {
        if (Strings.isBlank(appKey) || Strings.isBlank(clientId) || Strings.isBlank(clientSecret)) {
            throw new EMInvalidArgumentException("appKey/clientId/clientSecret cannot be blank");
        }
        this.appKey = appKey;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String getAppKey() {
        return appKey;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override public String toString() {
        return "EasemobAppCredentials{" +
                "appKey='" + appKey + '\'' +
                ", clientId='" + Utilities.mask(clientId) + '\'' +
                ", clientSecret='" +Utilities.mask(clientSecret) + '\'' +
                '}';
    }
}
