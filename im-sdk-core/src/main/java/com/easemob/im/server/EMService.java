package com.easemob.im.server;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultContext;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.block.BlockApi;
import com.easemob.im.server.api.attachment.AttachmentApi;
import com.easemob.im.server.api.codec.JsonCodec;
import com.easemob.im.server.api.dnsconfig.DnsConfigApi;
import com.easemob.im.server.api.message.MessageApi;
import com.easemob.im.server.api.metadata.MetadataApi;
import com.easemob.im.server.api.room.RoomApi;
import com.easemob.im.server.api.group.GroupApi;
import com.easemob.im.server.api.contact.ContactApi;
import com.easemob.im.server.api.sms.SmsApi;
import com.easemob.im.server.api.user.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

/**
 * Server SDK API服务类
 * 主要API服务包括：
 * - 封禁API
 * - 通讯录API
 * - 附件API
 * - 群API
 * - 消息API
 * - 聊天室API
 * - 用户API
 * - 发送短信API
 * - 用户属性API
 *
 */
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

    private final SmsApi smsApi;

    private final MetadataApi metadataApi;

    public static Mono<String> getCluster(String appkey) {
        return new DnsConfigApi(HttpClient.create(), new DefaultErrorMapper(), new JsonCodec()).getCluster(appkey);
    }

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
        this.smsApi = new SmsApi(this.context);
        this.metadataApi = new MetadataApi(this.context);
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

    public SmsApi sms() {
        return this.smsApi;
    }

    public MetadataApi metadata() {
        return this.metadataApi;
    }


}
