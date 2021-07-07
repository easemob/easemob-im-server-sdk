package com.easemob.im.server.api.loadbalance;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;
import java.util.stream.Collectors;

public class GetDnsConfigResponse {
    @JsonProperty("deploy_name")
    private String name;
    @JsonProperty("file_version")
    private String version;
    @JsonProperty("rest")
    private Service service;

    @JsonCreator
    public GetDnsConfigResponse(@JsonProperty("deploy_name") String name,
            @JsonProperty("file_version") String version,
            @JsonProperty("rest") Service service) {
        this.name = name;
        this.version = version;
        this.service = service;
    }

    public List<Endpoint> toEndpoints() {
        return this.service.toEndpoints();
    }

    public static class Service {
        @JsonProperty("hosts")
        private List<ServiceRecord> records;

        public Service(@JsonProperty("hosts") List<ServiceRecord> records) {
            this.records = records;
        }

        public List<Endpoint> toEndpoints() {
            return this.records.stream()
                    .map(ServiceRecord::toEndpoint)
                    .collect(Collectors.toList());
        }
    }


    public static class ServiceRecord {
        @JsonProperty("protocol")
        private String protocol;
        @JsonProperty("domain")
        private String domainName;
        @JsonProperty("ip")
        private String ip;
        @JsonProperty("port")
        private int port;

        public ServiceRecord(@JsonProperty("protocol") String protocol,
                @JsonProperty("domain") String domainName,
                @JsonProperty("ip") String ip,
                @JsonProperty("port") int port) {
            this.protocol = protocol;
            this.domainName = domainName;
            this.ip = ip;
            this.port = port;
        }

        public Endpoint toEndpoint() {
            if (this.domainName != null) {
                return new Endpoint(this.protocol, this.domainName, this.port);
            }
            return new Endpoint(this.protocol, this.ip, this.port);
        }
    }
}
