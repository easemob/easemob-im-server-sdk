package com.easemob.im.server.api.loadbalance;

import java.util.Objects;

public class Endpoint {
    private String protocol;
    private String host;
    private int port;

    public String getUri() {
        if (port > 0) {
            return String.format("%s://%s:%d", protocol, host, port);
        } else {
            return String.format("%s://%s", protocol, host);
        }
    }

    public Endpoint(String protocol, String host, int port) {
        this.protocol = protocol;
        this.host = host;
        this.port = port;
    }

    public Endpoint(String protocol, String host) {
        this.protocol = protocol;
        this.host = host;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Endpoint endpoint = (Endpoint) o;
        return port == endpoint.port && Objects.equals(protocol, endpoint.protocol) && Objects.equals(host, endpoint.host);
    }

    @Override
    public int hashCode() {
        return Objects.hash(protocol, host, port);
    }

    @Override
    public String toString() {
        return "Endpoint{" +
                "protocol='" + protocol + '\'' +
                ", host='" + host + '\'' +
                ", port=" + port +
                '}';
    }
}
