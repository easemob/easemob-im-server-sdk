package com.easemob.im.server.api.message.deletechannel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class DeleteMessageChannelRequest {

    @JsonProperty("channel")
    private String channelName;

    @JsonProperty("type")
    private String channelType;

    @JsonProperty("delete_roam")
    private Boolean deleteRoam;

    @JsonCreator
    public DeleteMessageChannelRequest(@JsonProperty("channel") String channelName,
            @JsonProperty("type") String channelType,
            @JsonProperty("delete_roam") Boolean deleteRoam) {
        this.channelName = channelName;
        this.channelType = channelType;
        this.deleteRoam = deleteRoam;
    }
}
