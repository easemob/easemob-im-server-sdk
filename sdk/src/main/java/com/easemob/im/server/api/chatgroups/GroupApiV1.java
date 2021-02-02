package com.easemob.im.server.api.chatgroups;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.chatgroups.detail.GroupDetail;
import com.easemob.im.server.api.chatgroups.list.GroupList;

public class GroupApiV1 {
    private GroupList list;

    private GroupDetail detail;

    public GroupApiV1(Context context) {
        this.list = new GroupList(context);
        this.detail = new GroupDetail(context);
    }

    public GroupList list() {
        return this.list;
    }

    public GroupDetail detail() {
        return this.detail;
    }


}
