package com.easemob.im.server.api.chatgroups;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.chatgroups.list.GroupList;

public class GroupApiV1 {
    private GroupList list;

    public GroupApiV1(Context context) {
        this.list = new GroupList(context);
    }

    public GroupList list() {
        return this.list;
    }


}
