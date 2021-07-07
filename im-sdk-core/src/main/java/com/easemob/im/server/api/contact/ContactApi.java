package com.easemob.im.server.api.contact;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.contact.user.ContactUser;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
     * @param user 所属用户的用户名
     * @return 每个联系人的用户名
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E5%A5%BD%E5%8F%8B%E5%88%97%E8%A1%A8">获取联系人列表</a>
     */
    public Flux<String> list(String user) {
        return this.contactUser.list(user);
    }

}
