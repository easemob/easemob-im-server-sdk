package com.easemob.im.server;

import com.easemob.im.server.exception.EMInvalidArgumentException;
import org.apache.logging.log4j.util.Strings;

public class EMProxy {
    private String ip;
    private int port;
    private String username;
    private String password;

    public EMProxy(String ip, int port, String username, String password) {
        this.ip = ip;
        this.port = port;
        this.username = username;
        this.password = password;
    }

    public static Builder builder() {
        return new Builder();
    }

    public String getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    @Override
    public String toString() {
        return "EMProxy{" +
                "ip='" + ip + '\'' +
                ", port=" + port +
                ", username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }

    public static class Builder {
        private String ip;
        private int port;
        private String username;
        private String password;

        public Builder setIP(String ip) {
            if (Strings.isBlank(ip)) {
                throw new EMInvalidArgumentException("ip must not be null or blank");
            }
            this.ip = ip;
            return this;
        }

        public Builder setPort(int port) {
            if (port < 0 || port > 65535) {
                throw new EMInvalidArgumentException(String.format("port %s is illegal", port));
            }
            this.port = port;
            return this;
        }

        public Builder setUsername(String username) {
            this.username = username;
            return this;
        }

        public Builder setPassword(String password) {
            this.password = password;
            return this;
        }

        public EMProxy build() {
            if (Strings.isBlank(this.ip)) {
                throw new EMInvalidArgumentException("the IP of setting proxy cannot be null or blank");
            }
            return new EMProxy(this.ip, this.port, this.username, this.password);
        }

        @Override
        public String toString() {
            return "Builder{" +
                    "ip='" + ip + '\'' +
                    ", port=" + port +
                    ", username='" + username + '\'' +
                    ", password='" + password + '\'' +
                    '}';
        }
    }
}

