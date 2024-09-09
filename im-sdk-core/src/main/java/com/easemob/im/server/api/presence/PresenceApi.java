package com.easemob.im.server.api.presence;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.presence.get.UserStatusGet;
import com.easemob.im.server.api.presence.online.UserOnlineCountGet;
import com.easemob.im.server.api.presence.set.UserStatusSet;
import com.easemob.im.server.api.presence.subscribe.PresenceUserStatusSubscribeResult;
import com.easemob.im.server.api.presence.subscribe.UserStatusSubscribe;
import com.easemob.im.server.api.presence.subscribe.UserStatusSubscribeListGet;
import com.easemob.im.server.api.presence.subscribe.UserStatusUnSubscribe;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * Presence 在线状态 API。
 */
public class PresenceApi {

    private UserStatusGet userStatusGet;

    private UserStatusSet userStatusSet;

    private UserStatusSubscribe userStatusSubscribe;

    private UserStatusUnSubscribe userStatusUnSubscribe;

    private UserStatusSubscribeListGet userStatusSubscribeListGet;

    private UserOnlineCountGet userOnlineCountGet;

    public PresenceApi(Context context) {
        this.userStatusGet = new UserStatusGet(context);
        this.userStatusSet = new UserStatusSet(context);
        this.userStatusSubscribe = new UserStatusSubscribe(context);
        this.userStatusUnSubscribe = new UserStatusUnSubscribe(context);
        this.userStatusSubscribeListGet = new UserStatusSubscribeListGet(context);
        this.userOnlineCountGet = new UserOnlineCountGet(context);
    }

    /**
     * 设置用户在线状态信息。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.presence().setUserStatus("username", "resource", "1", "ext").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 设置哪个用户的在线状态信息。
     * @param resource 要设置用户在哪个设备的在线状态信息，即传入服务器分配给每个设备资源的唯一标识符，格式为 {device type}_{resource ID}，其中设备类型 device type 可以是 android、ios 或 web，资源 ID resource ID 由 SDK 分配。例如，android_123423453246。
     * @param status 用户的在线状态：- 0：离线；- 1：在线；- 其它数字字符串：自定义在线状态。
     * @param ext 在线状态扩展信息。建议不超过 1024 字节。
     * @return 成功或错误
     * @see <a href="https://doc.easemob.com/document/server-side/presence.html#%E8%AE%BE%E7%BD%AE%E7%94%A8%E6%88%B7%E5%9C%A8%E7%BA%BF%E7%8A%B6%E6%80%81%E4%BF%A1%E6%81%AF">设置用户在线状态信息</a>
     */
    public Mono<Void> setUserStatus(String username, String resource, String status, String ext) {
        return this.userStatusSet.execute(username, resource, status, ext);
    }

    /**
     * 批量获取在线状态信息。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> usernames = Arrays.asList("test_user1", "test_user2");
     *     List<PresenceUserStatusResource> userStatus = service.presence().getUserStatus("operator", usernames).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param operator 操作者用户名
     * @param usernames 需要获取其在线状态的用户列表，例如 ["user1", "user2"]，最多可传 100 个用户 ID。
     * @return 成功或错误
     * @see <a href="https://doc.easemob.com/document/server-side/presence.html#%E6%89%B9%E9%87%8F%E8%8E%B7%E5%8F%96%E5%9C%A8%E7%BA%BF%E7%8A%B6%E6%80%81%E4%BF%A1%E6%81%AF">批量获取在线状态信息</a>
     */
    public Mono<List<PresenceUserStatusResource>> getUserStatus(String operator, List<String> usernames) {
        return this.userStatusGet.execute(operator, usernames);
    }

    /**
     * 批量订阅在线状态。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> usernames = Arrays.asList("test_user1", "test_user2");
     *     List<PresenceUserStatusSubscribeResource> subscribeUserStatus = service.presence().subscribeUserStatus("operator", usernames, 86400).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param operator 为哪个用户订阅在线状态。
     * @param usernames 被订阅用户的用户 ID 数组，例如 ["user1", "user2"]。该数组最多可包含 100 个用户 ID。
     * @param expiry 订阅时长，单位为秒，最大值为 2,592,000，即 30 天。
     * @return 成功或错误
     * @see <a href="https://doc.easemob.com/document/server-side/presence.html#%E6%89%B9%E9%87%8F%E8%AE%A2%E9%98%85%E5%9C%A8%E7%BA%BF%E7%8A%B6%E6%80%81">批量订阅在线状态</a>
     */
    public Mono<List<PresenceUserStatusSubscribeResource>> subscribeUserStatus(String operator, List<String> usernames, Long expiry) {
        return this.userStatusSubscribe.execute(operator, usernames, expiry);
    }

    /**
     * 取消订阅多个用户的在线状态。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> usernames = Arrays.asList("test_user1", "test_user2");
     *     service.presence().unsubscribeUserStatus("operator", usernames).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param operator 为哪个用户取消订阅的在线状态。
     * @param usernames 要取消订阅在线状态的用户 ID 数组，例如 ["user1", "user2"]，最多可传 100 个用户 ID。
     * @return 成功或错误
     * @see <a href="https://doc.easemob.com/document/server-side/presence.html#%E5%8F%96%E6%B6%88%E8%AE%A2%E9%98%85%E5%A4%9A%E4%B8%AA%E7%94%A8%E6%88%B7%E7%9A%84%E5%9C%A8%E7%BA%BF%E7%8A%B6%E6%80%81">取消订阅多个用户的在线状态</a>
     */
    public Mono<Void> unsubscribeUserStatus(String operator, List<String> usernames) {
        return this.userStatusUnSubscribe.execute(operator, usernames);
    }

    /**
     * 查询订阅列表。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     PresenceUserStatusSubscribeResult result = service.presence().getSubscribeList("operator", 1, 10).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param operator 查询哪个用户的订阅列表。若传入的用户 ID 不存在或未订阅其他用户的在线状态，返回空列表。
     * @param pageNum 要查询的页码。该参数的值须大于等于 1。若不传，默认值为 1。
     * @param pageSize 每页显示的订阅用户数量。取值范围为 [1,500]，若不传默认值为 1。
     * @return 成功或错误
     * @see <a href="https://doc.easemob.com/document/server-side/presence.html#%E6%9F%A5%E8%AF%A2%E8%AE%A2%E9%98%85%E5%88%97%E8%A1%A8">查询订阅列表</a>
     */
    public Mono<PresenceUserStatusSubscribeResult> getSubscribeList(String operator, int pageNum, int pageSize) {
        return this.userStatusSubscribeListGet.execute(operator, pageNum, pageSize);
    }

    /**
     * 查询单个群组的在线成员数量。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     Integer onlineCount = service.presence().getUserOnlineCount("1324236456", 1).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param id 群组 ID。
     * @param type 查询类型，查询群组的在线成员数量，传 1 即可。
     * @return 成功或错误
     * @see <a href="https://doc.easemob.com/document/server-side/presence.html#%E6%9F%A5%E8%AF%A2%E5%8D%95%E4%B8%AA%E7%BE%A4%E7%BB%84%E7%9A%84%E5%9C%A8%E7%BA%BF%E6%88%90%E5%91%98%E6%95%B0%E9%87%8F">查询单个群组的在线成员数量</a>
     */
    public Mono<Integer> getUserOnlineCount(String id, int type) {
        return this.userOnlineCountGet.execute(id, type);
    }

}
