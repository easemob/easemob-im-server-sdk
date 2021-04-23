package com.easemob.im.server.api.sms.send;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;
import java.util.Set;

public class SendSmsRequest {
    @JsonProperty("mobiles")
    private Set<String> mobiles;

    @JsonProperty("tid")
    private String tid;

    @JsonProperty("tmap")
    private Map<String, String> tmap;

    @JsonProperty("extendCode")
    private String extendCode;

    @JsonProperty("custom")
    private String custom;

    @JsonCreator
    public SendSmsRequest(@JsonProperty("mobiles") Set<String> mobiles,
                          @JsonProperty("tid") String tid,
                          @JsonProperty("tmap") Map<String, String> tmap,
                          @JsonProperty("extendCode") String extendCode,
                          @JsonProperty("custom") String custom) {
        this.mobiles = mobiles;
        this.tid = tid;
        this.tmap = tmap;
        this.extendCode = extendCode;
        this.custom = custom;
    }

    public Set<String> getMobiles() {
        return mobiles;
    }

    public String getTid() {
        return tid;
    }

    public Map<String, String> getTmap() {
        return tmap;
    }

    public String getExtendCode() {
        return extendCode;
    }

    public String getCustom() {
        return custom;
    }
}
