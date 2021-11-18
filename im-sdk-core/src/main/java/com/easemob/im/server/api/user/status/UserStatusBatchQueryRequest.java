package com.easemob.im.server.api.user.status;

import java.util.List;

public class UserStatusBatchQueryRequest {

    private List<String> usernames;

    public UserStatusBatchQueryRequest(List<String> usernames) {
        this.usernames = usernames;
    }

    public List<String> getUsernames() {
        return this.usernames;
    }

    public void setUsernames(List<String> usernames) {
        this.usernames = usernames;
    }
}
