package com.easemob.im.server;

import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import com.easemob.im.server.exception.EMUnsupportedEncodingException;
import org.apache.logging.log4j.util.Strings;
import reactor.netty.transport.logging.AdvancedByteBufFormat;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class EMProperties {

    private final Realm realm;
    private final String appKey;
    private final Credentials credentials;
    private final String baseUri;
    private final EMProxy proxy;
    private final int httpConnectionPoolSize;
    private final int httpConnectionMaxIdleTime;
    private final String serverTimezone;
    private final int agoraTokenExpireInSeconds;
    private final AdvancedByteBufFormat httpLogFormat;
    private final boolean validateUserName;


    private EMProperties(Realm realm, String appKey, Credentials credentials, String baseUri,
                         EMProxy proxy, int httpConnectionPoolSize, String serverTimezone,
                         int agoraTokenExpireInSeconds, AdvancedByteBufFormat httpLogFormat, boolean validateUserName, int httpConnectionMaxIdleTime) {
        this.realm = realm;
        this.appKey = appKey;
        this.credentials = credentials;
        this.baseUri = baseUri;
        this.proxy = proxy;
        this.httpConnectionPoolSize = httpConnectionPoolSize;
        this.serverTimezone = serverTimezone;
        this.agoraTokenExpireInSeconds = agoraTokenExpireInSeconds;
        this.httpLogFormat = httpLogFormat;
        this.validateUserName = validateUserName;
        this.httpConnectionMaxIdleTime = httpConnectionMaxIdleTime;
    }

    /**
     * @param baseUri baseUri
     * @param appKey appKey
     * @param proxy proxy
     * @param clientId clientId
     * @param clientSecret clientSecret
     * @param httpConnectionPoolSize httpConnectionPoolSize
     * @param serverTimezone serverTimezone
     * @param httpConnectionMaxIdleTime httpConnectionMaxIdleTime
     * @deprecated use {@link #builder()} instead.
     */
    @Deprecated
    public EMProperties(String baseUri, String appKey, EMProxy proxy, String clientId,
                        String clientSecret, int httpConnectionPoolSize, String serverTimezone, int httpConnectionMaxIdleTime) {
        this.realm = Realm.EASEMOB_REALM;
        this.appKey = appKey;
        this.credentials = new EasemobAppCredentials(clientId, clientSecret);
        this.baseUri = baseUri;
        this.proxy = proxy;
        this.httpConnectionPoolSize = httpConnectionPoolSize;
        this.serverTimezone = serverTimezone;
        this.agoraTokenExpireInSeconds = Utilities.DEFAULT_AGORA_TOKEN_EXPIRE_IN_SECONDS;
        this.httpLogFormat = AdvancedByteBufFormat.SIMPLE;
        this.validateUserName = true;
        this.httpConnectionMaxIdleTime = httpConnectionMaxIdleTime;
    }

    // easemob realm by default
    public static Builder builder() {
        return new Builder().setRealm(Realm.EASEMOB_REALM);
    }

    public String getAppkey() {
        return this.appKey;
    }

    public String getClientId() {
        if (this.realm.equals(Realm.EASEMOB_REALM)) {
            return this.credentials.getId();

        } else if (this.realm.equals(Realm.AGORA_REALM)) {
            return null;
        } else {
            throw new EMInvalidStateException(
                    String.format("invalid realm type %s", this.realm.toString()));
        }
    }

    public String getClientSecret() {
        if (this.realm.equals(Realm.EASEMOB_REALM)) {
            return this.credentials.getSecret();

        } else if (this.realm.equals(Realm.AGORA_REALM)) {
            return null;
        } else {
            throw new EMInvalidStateException(
                    String.format("invalid realm type %s", this.realm.toString()));
        }
    }

    public String getAppId() {
        if (this.realm.equals(Realm.AGORA_REALM)) {
            return this.credentials.getId();

        } else if (this.realm.equals(Realm.EASEMOB_REALM)) {
            return null;
        } else {
            throw new EMInvalidStateException(
                    String.format("invalid realm type %s", this.realm.toString()));
        }
    }

    public String getAppCert() {
        if (this.realm.equals(Realm.AGORA_REALM)) {
            return this.credentials.getSecret();

        } else if (this.realm.equals(Realm.EASEMOB_REALM)) {
            return null;
        } else {
            throw new EMInvalidStateException(
                    String.format("invalid realm type %s", this.realm.toString()));
        }
    }

    public Realm getRealm() {
        return realm;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    public String getBaseUri() {
        return baseUri;
    }

    public EMProxy getProxy() {
        return this.proxy;
    }

    public String getAppkeyUrlEncoded() {
        try {
            return URLEncoder.encode(this.appKey, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new EMUnsupportedEncodingException(e.getMessage());
        }
    }

    public String getAppkeySlashDelimited() {
        return this.appKey.replace('#', '/');
    }

    public int getHttpConnectionPoolSize() {
        return this.httpConnectionPoolSize;
    }

    public int getHttpConnectionMaxIdleTime() {
        return this.httpConnectionMaxIdleTime;
    }

    public String getServerTimezone() {
        return this.serverTimezone;
    }

    public String getAppKey() {
        return appKey;
    }

    public int getAgoraTokenExpireInSeconds() {
        return agoraTokenExpireInSeconds;
    }

    public AdvancedByteBufFormat getHttpLogFormat() {
        return httpLogFormat;
    }

    public boolean getValidateUserName() {
        return validateUserName;
    }

    @Override
    public String toString() {
        return "EMProperties{" +
                "realm=" + realm +
                ", appKey='" + appKey + '\'' +
                ", credentials=" + credentials +
                ", baseUri='" + baseUri + '\'' +
                ", proxy=" + proxy +
                ", httpConnectionPoolSize=" + httpConnectionPoolSize +
                ", httpConnectionMaxIdleTime=" + httpConnectionMaxIdleTime +
                ", serverTimezone='" + serverTimezone + '\'' +
                ", agoraTokenExpireInSeconds=" + agoraTokenExpireInSeconds +
                ", httpLogFormat=" + httpLogFormat +
                ", validateUserName=" + validateUserName +
                '}';
    }

    public enum Realm {
        AGORA_REALM(1),
        EASEMOB_REALM(2),
        ;
        public short intValue;

        Realm(int value) {
            intValue = (short) value;
        }
    }


    public static class Builder {
        private Realm realm;
        private String appKey;
        private String clientId;
        private String clientSecret;
        private String appId;
        private String appCert;

        private String baseUri;
        private EMProxy proxy;
        private int httpConnectionPoolSize = 10;
        private int httpConnectionMaxIdleTime = 10 * 1000;
        private String serverTimezone = "+8";
        private int agoraTokenExpireInSeconds = Utilities.DEFAULT_AGORA_TOKEN_EXPIRE_IN_SECONDS;
        private AdvancedByteBufFormat httpLogFormat = AdvancedByteBufFormat.SIMPLE;
        private boolean validateUserName = true;

        public Builder setRealm(Realm realm) {
            this.realm = realm;
            return this;
        }

        public Builder setAppkey(String appKey) {
            if (Strings.isBlank(appKey)) {
                throw new EMInvalidArgumentException("appKey must not be null or blank");
            }
            String[] tokens = appKey.split("#");
            if (tokens.length != 2) {
                throw new EMInvalidArgumentException("appKey must contains #");
            }
            if (tokens[0].isEmpty()) {
                throw new EMInvalidArgumentException("appKey must contains {org}");
            }
            if (tokens[1].isEmpty()) {
                throw new EMInvalidArgumentException("appKey must contains {app}");
            }
            this.appKey = appKey;
            return this;
        }

        public Builder setClientId(String clientId) {
            if (Strings.isBlank(clientId)) {
                throw new EMInvalidArgumentException("clientId must not be null or blank");
            }
            this.clientId = clientId;
            return this;
        }

        public Builder setClientSecret(String clientSecret) {
            if (Strings.isBlank(clientSecret)) {
                throw new EMInvalidArgumentException("clientSecret must not be null or blank");
            }
            this.clientSecret = clientSecret;
            return this;
        }

        public Builder setAppId(String appId) {
            if (Strings.isBlank(appId)) {
                throw new EMInvalidArgumentException("appId must not be null or blank");
            }
            this.appId = appId;
            return this;
        }

        public Builder setAppCert(String appCert) {
            if (Strings.isBlank(appCert)) {
                throw new EMInvalidArgumentException("appCert must not be null or blank");
            }
            this.appCert = appCert;
            return this;
        }

        public Builder setBaseUri(String baseUri) {
            this.baseUri = baseUri;
            return this;
        }

        public Builder setProxy(EMProxy proxy) {
            this.proxy = proxy;
            return this;
        }

        public Builder setHttpConnectionPoolSize(int httpConnectionPoolSize) {
            if (httpConnectionPoolSize < 0) {
                throw new EMInvalidArgumentException("httpConnectionPoolSize must not be negative");
            }
            this.httpConnectionPoolSize = httpConnectionPoolSize;
            return this;
        }

        public Builder setServerTimezone(String timezone) {
            this.serverTimezone = timezone;
            return this;
        }

        public Builder setAgoraTokenExpireInSeconds(int agoraTokenExpireInSeconds) {
            this.agoraTokenExpireInSeconds = agoraTokenExpireInSeconds;
            return this;
        }

        public Builder setHttpLogFormat(AdvancedByteBufFormat httpLogFormat) {
            this.httpLogFormat = httpLogFormat;
            return this;
        }

        public Builder turnOffUserNameValidation() {
            this.validateUserName = false;
            return this;
        }

        /**
         * @param httpConnectionMaxIdleTime httpConnection最大空闲时间 单位：毫秒
         * @return Builder
         */
        public Builder httpConnectionMaxIdleTime(int httpConnectionMaxIdleTime) {
            if (httpConnectionMaxIdleTime <= 0) {
                throw new EMInvalidArgumentException("httpConnectionMaxIdleTime must not be negative");
            }
            this.httpConnectionMaxIdleTime = httpConnectionMaxIdleTime;
            return this;
        }


        public EMProperties build() {
            if (this.realm == null) {
                throw new EMInvalidStateException("realm not set");
            }
            if (this.appKey == null) {
                throw new EMInvalidStateException("appKey not set");
            }
            if (this.realm.equals(Realm.EASEMOB_REALM)) {
                if (this.clientId == null) {
                    throw new EMInvalidStateException("clientId not set");
                }
                if (this.clientSecret == null) {
                    throw new EMInvalidStateException("clientSecret not set");
                }
                if (this.appId != null || this.appCert != null) {
                    throw new EMInvalidStateException("appId and appCert must be blank");
                }
                Credentials credentials =
                        new EasemobAppCredentials(this.clientId, this.clientSecret);
                return new EMProperties(this.realm, this.appKey, credentials, this.baseUri,
                        this.proxy, this.httpConnectionPoolSize, this.serverTimezone,
                        this.agoraTokenExpireInSeconds, this.httpLogFormat, this.validateUserName, this.httpConnectionMaxIdleTime);
            } else if (this.realm.equals(Realm.AGORA_REALM)) {
                if (this.appId == null) {
                    throw new EMInvalidStateException("appId not set");
                }
                if (this.appCert == null) {
                    throw new EMInvalidStateException("appCert not set");
                }
                if (this.clientId != null || this.clientSecret != null) {
                    throw new EMInvalidStateException("clientId and clientSecret must be blank");
                }
                Credentials credentials = new AgoraAppCredentials(this.appId, this.appCert);
                return new EMProperties(this.realm, this.appKey, credentials, this.baseUri,
                        this.proxy, this.httpConnectionPoolSize, this.serverTimezone,
                        this.agoraTokenExpireInSeconds, this.httpLogFormat, this.validateUserName, this.httpConnectionMaxIdleTime);
            } else {
                throw new EMInvalidStateException(
                        String.format("invalid realm type %s", this.realm.toString()));
            }
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "realm=" + realm +
                    ", appKey='" + appKey + '\'' +
                    ", clientId='" + Utilities.mask(clientId) + '\'' +
                    ", clientSecret='" + Utilities.mask(clientSecret) + '\'' +
                    ", appId='" + Utilities.mask(appId) + '\'' +
                    ", appCert='" + Utilities.mask(appCert) + '\'' +
                    ", baseUri='" + baseUri + '\'' +
                    ", proxy=" + proxy +
                    ", httpConnectionPoolSize=" + httpConnectionPoolSize +
                    ", serverTimezone='" + serverTimezone + '\'' +
                    ", agoraTokenExpireInSeconds=" + agoraTokenExpireInSeconds +
                    ", httpLogFormat=" + httpLogFormat +
                    ", validateUserName=" + validateUserName +
                    '}';
        }
    }
}
