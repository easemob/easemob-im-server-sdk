package com.easemob.im.server.model;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class EMRemoveMember {

    @JsonProperty("result")
    private boolean isSuccess;

    @JsonProperty("user")
    private String memberName;

    @JsonProperty("reason")
    private String failReason;

    @JsonCreator
    public EMRemoveMember(@JsonProperty("result") boolean isSuccess,
            @JsonProperty("user") String memberName,
            @JsonProperty("reason") String failReason) {
        this.isSuccess = isSuccess;
        this.memberName = memberName;
        this.failReason = failReason;
    }

//    @JsonCreator
//    public EMRemoveMember(@JsonProperty("result") boolean isSuccess,
//            @JsonProperty("user") String memberName
//    ) {
//        this.isSuccess = isSuccess;
//        this.memberName = memberName;
//    }
//
//    public EMRemoveMember of() {
//        if (this.isSuccess) {
//            return new EMRemoveMember(true, this.memberName);
//        } else {
//            return new EMRemoveMember(false, this.memberName, this.failReason);
//        }
//    }

    public String getMemberName() {
        return memberName;
    }

    public boolean getIsSuccess() {
        return isSuccess;
    }

    public String getFailReason() {
        return failReason;
    }
}
