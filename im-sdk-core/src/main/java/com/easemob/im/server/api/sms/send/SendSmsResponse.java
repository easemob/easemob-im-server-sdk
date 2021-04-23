package com.easemob.im.server.api.sms.send;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SendSmsResponse {
    @JsonProperty("count")
    private Integer count;

    @JsonProperty("msg")
    private String msg;

    @JsonCreator
    public SendSmsResponse(@JsonProperty("count") Integer count,
                           @JsonProperty("msg") String msg) {
        this.count = count;
        this.msg = msg;
    }

    public Integer getCount() {
        return count;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public String toString() {
        return "SendSmsResponse{" +
                "count=" + count +
                ", msg='" + msg + '\'' +
                '}';
    }
}
