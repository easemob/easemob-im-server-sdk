package com.easemob.im.server.api.push;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.push.displaystyle.set.DisplayStyle;
import com.easemob.im.server.api.push.displaystyle.set.NotificationDisplayStyle;
import com.easemob.im.server.api.push.nickname.UpdateUserNickname;
import com.easemob.im.server.api.push.nodisturbing.NotificationNoDisturbing;
import com.easemob.im.server.api.push.offline.Setting;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.model.EMConversationType;
import com.easemob.im.server.model.EMNotificationType;
import com.easemob.im.server.model.EMPushNickname;
import com.easemob.im.server.model.EMUser;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 推送API。
 */
public class PushApi {

    private Context context;

    private UpdateUserNickname updateUserNickname;

    private NotificationDisplayStyle notificationDisplayStyle;

    private NotificationNoDisturbing notificationNoDisturbing;

    private Setting setting;

    public PushApi(Context context) {
        this.updateUserNickname = new UpdateUserNickname(context);
        this.notificationDisplayStyle = new NotificationDisplayStyle(context);
        this.notificationNoDisturbing = new NotificationNoDisturbing(context);
        this.setting = new Setting(context);
        this.context = context;
    }

    /**
     * 设置推送昵称。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     service.push().updateUserNickname("username", "nickname").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 用户名
     * @param nickname 推送昵称
     * @return {@code Mono}
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%AE%BE%E7%BD%AE%E6%8E%A8%E9%80%81%E6%98%B5%E7%A7%B0">设置推送昵称</a>
     */
    public Mono<Void> updateUserNickname(String username, String nickname) {
        try {
            if (context.getProperties().getValidateUserName()) {
                EMUser.validateUsername(username);
            }
        } catch (EMInvalidArgumentException e) {
            return Mono.error(e);
        }
        return this.updateUserNickname.update(username, nickname);
    }

    /**
     * 批量设置离线推送时显示的昵称。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     List<EMPushNickname> pushNicknames = new ArrayList<>();
     *     pushNicknames.add(new EMPushNickname("user1", "推送昵称-1"));
     *     pushNicknames.add(new EMPushNickname("user2", "推送昵称-2"));
     *
     *     service.push().updateUserNicknames(pushNicknames).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param pushNicknames 需要设置离线推送时显示的昵称列表，EMPushNickname 中包含用户名以及推送昵称，推送昵称长度不能超过 100 个字符。 支持以下字符集： \- 26 个小写英文字母 a-z； \- 26 个大写英文字母 A-Z； \- 10 个数字 0-9； \- 中文； \- 特殊字符。
     * @return {@code Mono}
     */
    public Mono<Void> updateUserNicknames(List<EMPushNickname> pushNicknames) {
        return this.updateUserNickname.update(pushNicknames);
    }

    /**
     * 设置推送消息展示方式，指客户端的推送通知栏展示消息的样式。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     service.push().setNotificationDisplayStyle("username", DisplayStyle.DETAILS).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 用户名
     * @param style    展示方式，DEFAULT 仅通知，DETAILS 通知以及消息详情
     * @return {@code Mono}
     * @see <a href="https://docs-im.easemob.com/im/server/ready/user#%E8%AE%BE%E7%BD%AE%E6%8E%A8%E9%80%81%E6%B6%88%E6%81%AF%E5%B1%95%E7%A4%BA%E6%96%B9%E5%BC%8F">设置推送消息展示方式</a>
     */
    public Mono<Void> setNotificationDisplayStyle(String username, DisplayStyle style) {
        try {
            if (context.getProperties().getValidateUserName()) {
                EMUser.validateUsername(username);
            }
        } catch (EMInvalidArgumentException e) {
            return Mono.error(e);
        }

        if (style == DisplayStyle.DEFAULT) {
            return this.notificationDisplayStyle.set(username, "0");
        } else {
            return this.notificationDisplayStyle.set(username, "1");
        }
    }

    /**
     * 设置推送免打扰。
     * <p>
     * 设置 IM 用户免打扰，在免打扰期间，用户将不会收到离线消息推送。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     service.push().openNotificationNoDisturbing("username", 10, 13).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username  用户名
     * @param startTime 免打扰起始时间，单位是小时
     * @param endTime   免打扰结束时间，单位是小时
     * @return {@code Mono}
     * @see <a href="https://docs-im.easemob.com/im/server/ready/user#%E8%AE%BE%E7%BD%AE%E5%85%8D%E6%89%93%E6%89%B0">设置推送免打扰</a>
     */
    public Mono<Void> openNotificationNoDisturbing(String username, int startTime, int endTime) {
        try {
            if (context.getProperties().getValidateUserName()) {
                EMUser.validateUsername(username);
            }
        } catch (EMInvalidArgumentException e) {
            return Mono.error(e);
        }

        validateTime(startTime);

        validateTime(endTime);

        return this.notificationNoDisturbing
                .open(username, String.valueOf(startTime), String.valueOf(endTime));
    }

    /**
     * 取消推送免打扰。
     * <p>
     * 设置 IM 用户免打扰，在免打扰期间，用户将不会收到离线消息推送。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     service.push().closeNotificationNoDisturbing("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 用户名
     * @return {@code Mono}
     * @see <a href="https://docs-im.easemob.com/im/server/ready/user#%E8%AE%BE%E7%BD%AE%E5%85%8D%E6%89%93%E6%89%B0">设置推送免打扰</a>
     */
    public Mono<Void> closeNotificationNoDisturbing(String username) {
        try {
            if (context.getProperties().getValidateUserName()) {
                EMUser.validateUsername(username);
            }
        } catch (EMInvalidArgumentException e) {
            return Mono.error(e);
        }

        return this.notificationNoDisturbing.close(username);
    }

    /**
     * 设置离线推送设置
     *
     * @param username         用户名
     * @param conversationType 会话类型
     * @param conversationId   会话Id，单聊时为对端用户的用户 ID，群聊时为群组 ID
     * @param notificationType 离线推送通知方式
     * @param ignoreInterval   离线推送免打扰时间段，精确到分钟，格式为 HH:MM-HH:MM，例如 08:30-10:00。
     *                         该时间为 24 小时制，免打扰时间段的开始时间和结束时间中的小时数和分钟数的取值范围分别为 [00,23] 和 [00,59]。
     *                         免打扰时段的设置仅针对 app 生效，对单聊或群聊不生效。如需设置 app 的免打扰时段，type 指定为 user，key 指定为当前用户 ID
     * @param ignoreDuration   离线推送免打扰时长，单位为毫秒。该参数的取值范围为 [0,604800000]，0 表示该参数无效，604800000 表示免打扰模式持续 7 天
     * @return {@code Mono}
     * @see <a href="http://docs-im-beta.easemob.com/document/server-side/push.html#%E8%AE%BE%E7%BD%AE%E7%A6%BB%E7%BA%BF%E6%8E%A8%E9%80%81%E8%AE%BE%E7%BD%AE">设置离线推送设置</a>
     */
    public Mono<Void> offlineSetting(String username, EMConversationType conversationType,
            String conversationId, EMNotificationType notificationType, String ignoreInterval,
            long ignoreDuration) {

        try {
            if (context.getProperties().getValidateUserName()) {
                EMUser.validateUsername(username);
            }
        } catch (EMInvalidArgumentException e) {
            return Mono.error(e);
        }

        return setting.execute(username, conversationType, conversationId, notificationType,
                ignoreInterval, ignoreDuration);
    }

    private void validateTime(int time) {
        if (!(time >= 0 && time <= 23)) {
            throw new EMInvalidArgumentException("no disturbing time should be between 0 and 23");
        }
    }

}
