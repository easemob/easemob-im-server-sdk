package com.easemob.im.server.api.message.modify;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ModifyTextOrCustomRequest {
    @JsonProperty("user")
    private String user;

    @JsonProperty("new_msg")
    private NewMessage newMessage;

    @JsonProperty("new_ext")
    private Map<String, Object> newExt;

    @JsonProperty("is_combine_ext")
    private Boolean isCombineExt;

    @JsonCreator
    public ModifyTextOrCustomRequest(@JsonProperty("user") String user, @JsonProperty("new_msg") NewMessage newMessage,
            @JsonProperty("new_ext") Map<String, Object> newExt,
            @JsonProperty("is_combine_ext") Boolean isCombineExt) {
        this.user = user;
        this.newMessage = newMessage;
        this.newExt = newExt;
        this.isCombineExt = isCombineExt;
    }
}
