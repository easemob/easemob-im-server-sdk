package com.easemob.im.server;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.config.Loggers;

public class EMProperties {
    private final String baseUri;
    private final String appkey;
    private final String clientId;
    private final String clientSecret;
    private final int httpConnectionPoolSize;
    private final boolean hideBanner;

    public static Builder builder() {
        return new Builder();
    }

    public String getBaseUri() {
        return this.baseUri;
    }

    public String getAppkey() {
        return this.appkey;
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public int getHttpConnectionPoolSize() {
        return this.httpConnectionPoolSize;
    }

    public boolean getHideBanner() {
        return this.hideBanner;
    }

    private EMProperties(String baseUri, String appkey, String clientId, String clientSecret, int httpConnectionPoolSize,
                         boolean hideBanner) {
        String[] tokens = appkey.split("#");
        this.baseUri = String.format("%s/%s/%s", baseUri, tokens[0], tokens[1]);
        this.appkey = appkey;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.httpConnectionPoolSize = httpConnectionPoolSize;
        this.hideBanner = hideBanner;
    }

    public String maskSensitiveString(String str) {
        return "<hidden>";
    }

    @Override
    public String toString() {
        return "EMProperties{" +
            "baseUri=" + this.baseUri +
            ", appkey=" + this.appkey +
            ", clientId=" + maskSensitiveString(this.clientId) +
            ", clientSecret=" + maskSensitiveString(this.clientSecret) +
            ", httpConnectionPoolSize=" + this.httpConnectionPoolSize +
            '}';
    }

    public static class Builder {
        private String appkey;
        private String clientId;
        private String clientSecret;
        private String baseUri;
        private int httpConnectionPoolSize = 10;
        private boolean hideBanner = false;

        /**
         * 设置Appkey，可以到环信Console查询该值。
         * @param appkey appkey
         * @return {@code Builder}
         */
        public Builder setAppkey(String appkey) {
            if (appkey == null || appkey.isEmpty()) {
                throw new EMInvalidArgumentException("appkey must not be null or empty");
            }

            String[] tokens = appkey.split("#");
            if (tokens.length != 2) {
                throw new EMInvalidArgumentException("appkey must contains #");
            }

            if (tokens[0].isEmpty()) {
                throw new EMInvalidArgumentException("appkey must contains {org}");
            }

            if (tokens[1].isEmpty()) {
                throw new EMInvalidArgumentException("appkey must contains {app}");
            }

            this.appkey = appkey;
            return this;
        }

        /**
         * 设置App认证id。
         * 该信息应该安全的保存在受信任的环境中。
         *
         * @param clientId     认证id
         * @return {@code Builder}
         */
        public Builder setClientId(String clientId) {
            if (clientId == null || clientId.isEmpty()) {
                throw new EMInvalidArgumentException("clientId must not be null or empty");
            }

            this.clientId = clientId;
            return this;
        }

        /**
         * 设置App认证secret。
         * 该信息应该安全的保存在受信任的环境中。
         *
         * @param clientSecret 认证密码
         * @return {@code Builder}
         */
        public Builder setClientSecret(String clientSecret) {
            if (clientSecret == null || clientSecret.isEmpty()) {
                throw new EMInvalidArgumentException("clientSecret must not be null or empty");
            }

            this.clientSecret = clientSecret;
            return this;
        }

        /**
         * 设置环信API baseUri，可以到环信Console查询该值。
         *
         * @param baseUri baseUri
         * @return the {@code Builder}
         */
        public Builder setBaseUri(String baseUri) {
            if (baseUri == null || baseUri.isEmpty()) {
                throw new EMInvalidArgumentException("baseUri must not be null or empty");
            }
            // trim trailing slash
            if (baseUri.charAt(baseUri.length()-1) == '/') {
                this.baseUri = baseUri.substring(0, baseUri.length()-1);
            } else {
                this.baseUri = baseUri;
            }

            return this;
        }

        public Builder setHttpConnectionPoolSize(int httpConnectionPoolSize) {
            if (httpConnectionPoolSize < 0) {
                throw new EMInvalidArgumentException("httpConnectionPoolSize must not be negative");
            }

            this.httpConnectionPoolSize = httpConnectionPoolSize;
            return this;
        }

        public Builder setHideBanner(boolean hideBanner) {
            this.hideBanner = hideBanner;
            return this;
        }

        /** 构造EMProperties
         *
         * @return {@code EMProperties}
         */
        public EMProperties build() {
            if (this.baseUri == null) {
                throw new EMInvalidStateException("baseUri not set");
            }
            if (this.appkey == null) {
                throw new EMInvalidStateException("appkey not set");
            }
            if (this.clientId == null) {
                throw new EMInvalidStateException("clientId not set");
            }
            if (this.clientSecret == null) {
                throw new EMInvalidStateException("clientSecret not set");
            }

            return new EMProperties(this.baseUri, this.appkey, this.clientId, this.clientSecret, this.httpConnectionPoolSize,
                    this.hideBanner);
        }

    }
}