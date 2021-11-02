package com.easemob.im.server.api.push;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.push.nickname.UpdateUserNickname;
import com.easemob.im.server.api.user.UserApi;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import com.easemob.im.server.model.EMUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import reactor.core.publisher.Mono;

/**
 * 推送API。
 */
public class PushApi {

    private static final Logger log = LoggerFactory.getLogger(PushApi.class);

    private Context context;

    private UpdateUserNickname updateUserNickname;

    public PushApi(Context context) {
        this.updateUserNickname = new UpdateUserNickname(context);
        this.context = context;
    }

    /**
     * 设置推送昵称。
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
}
