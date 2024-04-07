package com.easemob.im.server.api.contact;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.contact.user.ContactUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

/**
 * 通讯录API。
 */
public class ContactApi {

    private ContactUser contactUser;

    public ContactApi(Context context) {
        this.contactUser = new ContactUser(context);
    }

    /**
     * 向用户通讯录添加联系人。
     *
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.contact().add("user", "contact").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param user    所属用户的用户名
     * @param contact 联系人的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E6%B7%BB%E5%8A%A0%E5%A5%BD%E5%8F%8B">添加联系人</a>
     */
    public Mono<Void> add(String user, String contact) {
        return this.contactUser.add(user, contact);
    }

    /**
     * 从用户通讯录移除联系人
     *
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.contact().remove("user", "contact").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param user    所属用户的用户名
     * @param contact 联系人的用户名
     * @return 成功或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E7%A7%BB%E9%99%A4%E5%A5%BD%E5%8F%8B">移除联系人</a>
     */
    public Mono<Void> remove(String user, String contact) {
        return this.contactUser.remove(user, contact);
    }

    /**
     * 获取用户联系人列表。
     *
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<String> users = service.contact().list("user").collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param user 所属用户的用户名
     * @return 每个联系人的用户名
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E5%A5%BD%E5%8F%8B%E5%88%97%E8%A1%A8">获取联系人列表</a>
     */
    public Flux<String> list(String user) {
        return this.contactUser.list(user);
    }

    /**
     * 分页获取好友列表。
     *
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     List<ContactUserPageResource> users = service.contact().list("user", 10, null).collectList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param user 所属用户的用户名
     * @return 每个联系人的用户名
     * @see <a href="https://doc.easemob.com/document/server-side/user_relationship.html#%E5%88%86%E9%A1%B5%E8%8E%B7%E5%8F%96%E5%A5%BD%E5%8F%8B%E5%88%97%E8%A1%A8">分页获取好友列表</a>
     */
    public Mono<List<ContactUserPageResource>> list(String user, int limit, String cursor) {
        return this.contactUser.list(user, limit, cursor);
    }

    /**
     * 设置好友备注。
     *
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.contact().setRemark("user", "contact", "remark").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param user    所属用户的用户名
     * @param contact 联系人的用户名
     * @param remark  好友备注。好友备注的长度不能超过 100 个字符。
     * @return 成功或错误
     * @see <a href="https://doc.easemob.com/document/server-side/user_relationship.html#%E8%AE%BE%E7%BD%AE%E5%A5%BD%E5%8F%8B%E5%A4%87%E6%B3%A8">设置好友备注</a>
     */
    public Mono<Void> setRemark(String user, String contact, String remark) {
        return this.contactUser.setRemark(user, contact, remark);
    }

}
