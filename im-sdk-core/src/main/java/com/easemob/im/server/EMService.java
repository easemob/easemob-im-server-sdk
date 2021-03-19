package com.easemob.im.server;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultContext;
import com.easemob.im.server.api.block.BlockApi;
import com.easemob.im.server.api.attachment.AttachmentApi;
import com.easemob.im.server.api.message.MessageApi;
import com.easemob.im.server.api.room.RoomApi;
import com.easemob.im.server.api.group.GroupApi;
import com.easemob.im.server.api.contact.ContactApi;
import com.easemob.im.server.api.user.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class EMService {

    private static final Logger log = LoggerFactory.getLogger(EMService.class);

    private final Context context;

    private final BlockApi blockV1;

    private final ContactApi contactApi;

    private final AttachmentApi attachmentApi;

    private final GroupApi groupApi;

    private final MessageApi messageApi;

    private final RoomApi roomApi;

    private final UserApi userApi;


    public EMService(EMProperties properties) {
        log.debug("EMService properties: {}", properties);
        this.context = new DefaultContext(properties);

        this.blockV1 = new BlockApi(this.context);
        this.contactApi = new ContactApi(this.context);
        this.attachmentApi = new AttachmentApi(this.context);
        this.messageApi = new MessageApi(this.context);
        this.groupApi = new GroupApi(this.context);
        this.roomApi = new RoomApi(this.context);
        this.userApi = new UserApi(this.context);
    }

    public BlockApi block() {
        return this.blockV1;
    }

    public ContactApi contact() {
        return this.contactApi;
    }

    public AttachmentApi attachment() {
        return this.attachmentApi;
    }

    public GroupApi group() {
        return this.groupApi;
    }

    public MessageApi message() {
        return this.messageApi;
    }

    public UserApi user() {
        return this.userApi;
    }

    public RoomApi room() {
        return this.roomApi;
    }



}
