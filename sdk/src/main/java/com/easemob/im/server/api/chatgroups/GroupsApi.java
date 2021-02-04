package com.easemob.im.server.api.chatgroups;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.chatgroups.create.GroupCreate;
import com.easemob.im.server.api.chatgroups.list.GroupList;

public class GroupsApi {
    private Context context;

    public GroupsApi(Context context) {
        this.context = context;
    }

    public GroupList list() {
        return new GroupList(this.context);
    }

    public GroupCreate create() {
        return new GroupCreate(this.context);
    }
}
