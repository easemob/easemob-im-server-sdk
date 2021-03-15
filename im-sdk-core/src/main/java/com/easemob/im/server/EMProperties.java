package com.easemob.im.server;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.exception.EMInvalidStateException;
import com.easemob.im.server.exception.EMUnsupportedEncodingException;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.file.Path;

public class EMProperties {
    private final String appkey;
    private final String clientId;
    private final String clientSecret;
    private final Path downloadDir;
    private final int httpConnectionPoolSize;

    public static Builder builder() {
        return new Builder();
    }

    public String getAppkey() {
        return this.appkey;
    }

    public String getAppkeyUrlEncoded() {
        try {
            return URLEncoder.encode(this.appkey, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            throw new EMUnsupportedEncodingException(e.getMessage());
        }
    }

    public String getAppkeySlashDelimited() {
        return this.appkey.replace('#', '/');
    }

    public String getClientId() {
        return this.clientId;
    }

    public String getClientSecret() {
        return this.clientSecret;
    }

    public Path getDownloadDir() {
        return this.downloadDir;
    }

    public int getHttpConnectionPoolSize() {
        return this.httpConnectionPoolSize;
    }

    private EMProperties(String appkey, String clientId, String clientSecret, Path downloadDir,
                         int httpConnectionPoolSize, boolean hideBanner) {
        this.appkey = appkey;
        this.clientId = clientId;
        this.clientSecret = clientSecret;
        this.downloadDir = downloadDir;
        this.httpConnectionPoolSize = httpConnectionPoolSize;
    }

    public String hide(String str) {
        return str.replaceAll(".", "*");
    }

    @Override
    public String toString() {
        return "EMProperties{" +
                "appkey='" + appkey + '\'' +
                ", clientId='" + hide(clientId) + '\'' +
                ", clientSecret='" + hide(clientSecret) + '\'' +
                ", downloadDir=" + downloadDir +
                ", httpConnectionPoolSize=" + httpConnectionPoolSize +
                '}';
    }

    public static class Builder {
        private String appkey;
        private String baseUri;
        private String clientId;
        private String clientSecret;
        private Path downloadDir;
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

        public Builder setDownloadDir(Path downloadDir) {
            this.downloadDir = downloadDir;
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
            if (this.appkey == null) {
                throw new EMInvalidStateException("appkey not set");
            }
            if (this.clientId == null) {
                throw new EMInvalidStateException("clientId not set");
            }
            if (this.clientSecret == null) {
                throw new EMInvalidStateException("clientSecret not set");
            }

            return new EMProperties(this.appkey, this.clientId, this.clientSecret, this.downloadDir,
                    this.httpConnectionPoolSize, this.hideBanner);
        }

    }
}