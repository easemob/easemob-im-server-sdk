package com.easemob.im.server;

import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import org.apache.logging.log4j.util.Strings;

public class EasemobAppCredentials implements Credentials {
    private final String clientId;
    private final String clientSecret;

    public EasemobAppCredentials(String clientId, String clientSecret) {
        if (Strings.isBlank(clientId) || Strings.isBlank(clientSecret)) {
            throw new EMInvalidArgumentException("clientId/clientSecret cannot be blank");
        }
        this.clientId = clientId;
        this.clientSecret = clientSecret;
    }

    @Override
    public String getId() {
        return this.clientId;
    }

    @Override
    public String getSecret() {
        return this.clientSecret;
    }

    @Override
    public String toString() {
        return "EasemobAppCredentials{" +
                "clientId='" + Utilities.mask(clientId) + '\'' +
                ", clientSecret='" + Utilities.mask(clientSecret) + '\'' +
                '}';
    }

}
