package com.easemob.im.server.api.contact.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class ContactUserRemarkRequest {
    @JsonProperty("remark")
    private String remark;


    @JsonCreator
    public ContactUserRemarkRequest(@JsonProperty("remark") String remark) {
        this.remark = remark;
    }

    public String getRemark() {
        return remark;
    }
}
