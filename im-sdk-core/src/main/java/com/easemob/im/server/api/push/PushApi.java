package com.easemob.im.server.api.push;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.push.displaystyle.set.DisplayStyle;
import com.easemob.im.server.api.push.displaystyle.set.NotificationDisplayStyle;
import com.easemob.im.server.api.push.nickname.UpdateUserNickname;
import com.easemob.im.server.api.push.nodisturbing.NotificationNoDisturbing;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.model.EMUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

import java.util.regex.Pattern;

/**
 * 推送API。
 */
public class PushApi {

    private static final Logger log = LoggerFactory.getLogger(PushApi.class);

    private Context context;

    private UpdateUserNickname updateUserNickname;

    private NotificationDisplayStyle notificationDisplayStyle;

    private NotificationNoDisturbing notificationNoDisturbing;

    public PushApi(Context context) {
        this.updateUserNickname = new UpdateUserNickname(context);
        this.notificationDisplayStyle = new NotificationDisplayStyle(context);
        this.notificationNoDisturbing = new NotificationNoDisturbing(context);
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
     * @param username  用户名
     * @param nickname  推送昵称
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
     * @param username  用户名
     * @param style  展示方式，DEFAULT 仅通知，DETAILS 通知以及消息详情
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
     * @param startTime  免打扰起始时间，单位是小时
     * @param endTime  免打扰结束时间，单位是小时
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

        return this.notificationNoDisturbing.open(username, String.valueOf(startTime), String.valueOf(endTime));
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
     * @param username  用户名
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

    private void validateTime(int time) {
        if (!(time >=0 && time <= 23)) {
            throw new EMInvalidArgumentException("no disturbing time should be between 0 and 23");
        }
    }
}
