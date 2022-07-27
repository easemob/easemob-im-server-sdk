package com.easemob.im.server.api.user;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PushResource {
    @JsonProperty("device_Id")
    private String deviceId;

    @JsonProperty("device_token")
    private String deviceToken;

    @JsonProperty("notifier_name")
    private String notifierName;

    public PushResource(@JsonProperty("device_Id") String deviceId,
            @JsonProperty("device_token") String deviceToken,
            @JsonProperty("notifier_name") String notifierName) {
        this.deviceId = deviceId;
        this.deviceToken = deviceToken;
        this.notifierName = notifierName;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public String getNotifierName() {
        return notifierName;
    }

    @Override public String toString() {
        return "PushResource{" +
                "deviceId='" + deviceId + '\'' +
                ", deviceToken='" + deviceToken + '\'' +
                ", notifierName='" + notifierName + '\'' +
                '}';
    }
}
