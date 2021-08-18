package com.easemob.im.server;

import com.easemob.im.server.api.util.Utilities;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import com.easemob.im.server.exception.EMUnsupportedEncodingException;
import com.easemob.im.server.model.EMUser;
import org.apache.logging.log4j.util.Strings;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.function.Consumer;

public class EMProperties {

    private final Realm realm;
    private final String appKey;
    private final Credentials credentials;
    private final String baseUri;
    private final EMProxy proxy;
    private final int httpConnectionPoolSize;
    private final String serverTimezone;

    public enum Realm {
        AGORA_REALM(1),
        EASEMOB_REALM(2),
        ;
        public short intValue;
        Realm(int value) {
            intValue = (short) value;
        }
    }

    private EMProperties(Realm realm, String appKey, Credentials credentials, String baseUri,
            EMProxy proxy, int httpConnectionPoolSize, String serverTimezone) {
        this.realm = realm;
        this.appKey = appKey;
        this.credentials = credentials;
        this.baseUri = baseUri;
        this.proxy = proxy;
        this.httpConnectionPoolSize = httpConnectionPoolSize;
        this.serverTimezone = serverTimezone;
    }

    /**
     * @param baseUri
     * @param appKey
     * @param proxy
     * @param clientId
     * @param clientSecret
     * @param httpConnectionPoolSize
     * @param serverTimezone
     * @deprecated use {@link #builder()} instead.
     */
    @Deprecated
    public EMProperties(String baseUri, String appKey, EMProxy proxy, String clientId,
            String clientSecret, int httpConnectionPoolSize, String serverTimezone) {
        this.realm = Realm.EASEMOB_REALM;
        this.appKey = appKey;
        this.credentials = new EasemobAppCredentials(clientId, clientSecret);
        this.baseUri = baseUri;
        this.proxy = proxy;
        this.httpConnectionPoolSize = httpConnectionPoolSize;
        this.serverTimezone = serverTimezone;
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
            throw new EMInvalidStateException(String.format("invalid realm type %s", this.realm.toString()));
        }
    }

    public String getClientSecret() {
        if (this.realm.equals(Realm.EASEMOB_REALM)) {
            return this.credentials.getSecret();

        } else if (this.realm.equals(Realm.AGORA_REALM)) {
            return null;
        } else {
            throw new EMInvalidStateException(String.format("invalid realm type %s", this.realm.toString()));
        }
    }

    public String getAppId() {
        if (this.realm.equals(Realm.AGORA_REALM)) {
            return this.credentials.getId();

        } else if (this.realm.equals(Realm.EASEMOB_REALM)) {
            return null;
        } else {
            throw new EMInvalidStateException(String.format("invalid realm type %s", this.realm.toString()));
        }
    }
    public String getAppCert() {
        if (this.realm.equals(Realm.AGORA_REALM)) {
            return this.credentials.getSecret();

        } else if (this.realm.equals(Realm.EASEMOB_REALM)) {
            return null;
        } else {
            throw new EMInvalidStateException(String.format("invalid realm type %s", this.realm.toString()));
        }
    }

    public Realm getRealm() {
        return realm;
    }

    public Credentials getCredentials() {
        return credentials;
    }

    // easemob realm by default
    public static Builder builder() {
        return new Builder().setRealm(Realm.EASEMOB_REALM);
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

    public String getServerTimezone() {
        return this.serverTimezone;
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
        private String serverTimezone = "+8";

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
                Credentials credentials = new EasemobAppCredentials(this.clientId, this.clientSecret);
                return new EMProperties(this.realm, this.appKey, credentials, this.baseUri, this.proxy,
                        this.httpConnectionPoolSize, this.serverTimezone);
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
                return new EMProperties(this.realm, this.appKey, credentials, this.baseUri, this.proxy,
                        this.httpConnectionPoolSize, this.serverTimezone);
            } else {
                throw new EMInvalidStateException(String.format("invalid realm type %s", this.realm.toString()));
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
                    '}';
        }
    }

    @Override
    public String toString() {
        return "EMProperties{" +
                "realm=" + realm +
                ", appKey=" + appKey +
                ", credentials=" + credentials +
                ", baseUri='" + baseUri + '\'' +
                ", proxy=" + proxy +
                ", httpConnectionPoolSize=" + httpConnectionPoolSize +
                ", serverTimezone='" + serverTimezone + '\'' +
                '}';
    }
}
