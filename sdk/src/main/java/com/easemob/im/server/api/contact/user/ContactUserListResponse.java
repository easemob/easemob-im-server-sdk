package com.easemob.im.server.api.contact.user;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class ContactUserListResponse {
    @JsonProperty("data")
    private List<String> usernames;

    @JsonCreator
    public ContactUserListResponse(@JsonProperty("data") List<String> usernames) {
        this.usernames = usernames;
    }

    public List<String> getUsernames() {
        return usernames;
    }

}
