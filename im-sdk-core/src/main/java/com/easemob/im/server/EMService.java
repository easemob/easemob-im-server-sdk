package com.easemob.im.server;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultContext;
import com.easemob.im.server.api.block.BlockApi;
import com.easemob.im.server.api.attachment.AttachmentApi;
import com.easemob.im.server.api.message.MessageApi;
import com.easemob.im.server.api.metadata.MetadataApi;
import com.easemob.im.server.api.moderation.ModerationApi;
import com.easemob.im.server.api.mute.MuteApi;
import com.easemob.im.server.api.push.PushApi;
import com.easemob.im.server.api.token.TokenApi;
import com.easemob.im.server.api.room.RoomApi;
import com.easemob.im.server.api.group.GroupApi;
import com.easemob.im.server.api.contact.ContactApi;
import com.easemob.im.server.api.user.UserApi;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Server SDK API服务类
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

    private final MetadataApi metadataApi;

    private final TokenApi tokenApi;

    private final PushApi pushApi;

    private final ModerationApi moderationApi;

    private final MuteApi  muteApi;

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
        this.metadataApi = new MetadataApi(this.context);
        this.tokenApi = new TokenApi(this.context);
        this.pushApi = new PushApi(this.context);
        this.moderationApi = new ModerationApi(this.context);
        this.muteApi = new MuteApi(this.context);
    }

    public Context getContext() {
        return context;
    }

    /**
     * 封禁API.<br>
     * 支持：<br>
     * - 用户禁言<br>
     * - 群禁言（可以定时解除）<br>
     * - 聊天室禁言（可以定时解除）<br>
     * - 禁止加入群<br>
     * - 禁止加入聊天室<br>
     * - 禁止登录<br>
     *
     * @return {@code BlockApi}
     */
    public BlockApi block() {
        return this.blockV1;
    }

    /**
     * 通讯录API.<br>
     * 支持：<br>
     * - 添加联系人<br>
     * - 移除联系人<br>
     * - 获取联系人列表<br>
     * <p>
     * 目前联系人只作为通讯录之用.
     *
     * @return {@code ContactApi}
     */
    public ContactApi contact() {
        return this.contactApi;
    }

    /**
     * 附件API.<br>
     * 支持：<br>
     * - 附件上传<br>
     * - 附件下载<br>
     * <p>
     * 目前，只支持本地文件的上传和下载.
     *
     * @return {@code AttachmentApi}
     */
    public AttachmentApi attachment() {
        return this.attachmentApi;
    }

    /**
     * 群API.<br>
     * <p>支持群管理：<br>
     * - 创建群<br>
     * - 删除群<br>
     * - 获取群列表<br>
     * - 获取群详情<br>
     * - 获取用户加入的群<br>
     * - 修改群详情<br>
     * - 修改群主<br>
     * <br>
     * <p>支持群成员管理：<br>
     * - 获取群成员列表<br>
     * - 添加群成员<br>
     * - 删除群成员<br>
     * <br>
     * <p>支持群管理员管理：<br>
     * - 获取群管理员列表<br>
     * - 添加群管理员<br>
     * - 删除群管理员<br>
     * <p>
     * 群与聊天室都是多人聊天，与聊天室主要差别在于群支持离线消息，即群成员上线时可以收到离线时错过的消息。
     * 如果配置了推送，则离线消息也会产生推送。
     * 群分为公开群和私有群，区别在于：在设备SDK中（指iOS、Android、Web、小程序等），私有群不会出现在群列表API的返回结果。
     *
     * @return {@code GroupApi}
     * @see com.easemob.im.server.api.block.BlockApi
     */
    public GroupApi group() {
        return this.groupApi;
    }

    /**
     * 消息API.<br>
     * 支持：<br>
     * - 发送消息<br>
     * - 查询离线消息数<br>
     * - 获取/下载聊天历史<br>
     *
     * @return {@code MessageApi}
     */
    public MessageApi message() {
        return this.messageApi;
    }

    /**
     * 用户API.<br>
     * 支持：<br>
     * - 创建用户<br>
     * - 删除用户<br>
     * - 获取用户<br>
     * - 修改用户密码<br>
     * - 强制用户下线<br>
     * - 获取用户在线状态<br>
     * - 获取用户token<br>
     *
     * @return {@code UserApi}
     */
    public UserApi user() {
        return this.userApi;
    }

    /**
     * 聊天室API.<br>
     * 支持聊天室管理：<br>
     * - 创建聊天室<br>
     * - 获取聊天室详情<br>
     * - 修改聊天室<br>
     * - 获取聊天室列表<br>
     * - 获取用户加入的聊天室列表<br>
     * <br>
     * <p>支持聊天室成员管理：<br>
     * - 获取聊天室成员列表<br>
     * - 添加聊天室成员<br>
     * - 移除聊天室成员<br>
     * <br>
     * <p>支持聊天室管理员管理：<br>
     * - 获取聊天室管理员<br>
     * - 添加聊天室管理员<br>
     *
     * @return {@code RoomApi}
     * @see com.easemob.im.server.api.block.BlockApi
     */
    public RoomApi room() {
        return this.roomApi;
    }

    /**
     * 用户属性API.<br>
     * 支持：<br>
     * - 设置用户属性<br>
     * - 获取用户属性<br>
     * - 获取app用户属性容量<br>
     * - 删除用户属性<br>
     *
     * @return {@code MetadataApi}
     */
    public MetadataApi metadata() {
        return this.metadataApi;
    }

    /**
     * token API.<br>
     * 支持：<br>
     * - TODO: generate user token <br>
     *
     * @return {@code TokenApi}
     */
    public TokenApi token() {
        return this.tokenApi;
    }

    /**
     * 推送API.<br>
     * 支持：<br>
     * - 设置推送昵称<br>
     *
     * @return {@code PushApi}
     */
    public PushApi push() {
        return this.pushApi;
    }

    /**
     * 内容审核记录API.
     * 支持：<br>
     * - 按查询条件导出文件<br>
     * - 获取导出详情列表<br>
     * - 下载内容审核记录文件<br>
     *
     * @return
     */
    public ModerationApi moderation() {
        return this.moderationApi;
    }

    /**
     * 用户全局禁言API.
     * 支持：<br>
     * - 设置用户全局禁言<br>
     * - 查询单个用户全局禁言剩余时间<br>
     * - 查询所有用户全局禁言剩余时间<br>
     *
     * @return
     */
    public MuteApi mute() {
        return this.muteApi;
    }
}
