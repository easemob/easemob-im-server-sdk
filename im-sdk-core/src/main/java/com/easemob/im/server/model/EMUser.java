package com.easemob.im.server.model;

import com.easemob.im.server.api.user.PushResource;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import io.netty.util.internal.StringUtil;

import java.util.List;
import java.util.Objects;
import java.util.regex.Pattern;

public class EMUser extends EMEntity {

    private static final Pattern USERNAME_PATTERN = Pattern.compile("^[a-z][0-9a-z-]{1,32}$");

    private static final Pattern PASSWORD_PATTERN =
            Pattern.compile("^[a-zA-Z0-9~!@#$%^&*\\-_=+<>;:,./?]{1,32}$");

    private final String username;
    private final String uuid;
    private final Boolean canLogin;
    private final List<PushResource> pushResources;

    /**
     * @param username 用户名
     * @param canLogin 是否可登录
     * @deprecated use {@link EMUser} instead
     */
    @Deprecated
    public EMUser(String username, Boolean canLogin) {
        this(username, null, canLogin, null);
    }

    /**
     * @param username 用户名
     * @param uuid     用户的uuid
     * @param canLogin 是否可登录
     */
    public EMUser(String username, String uuid, Boolean canLogin) {
        this(username, uuid, canLogin, null);
    }

    /**
     * @param username 用户名
     * @param uuid     用户 UUID
     * @param canLogin 是否可登录
     * @param pushResources 推送信息，例如 deviceId、deviceToken
     */
    public EMUser(String username, String uuid, Boolean canLogin, List<PushResource> pushResources) {
        super(EntityType.USER);
        if (StringUtil.isNullOrEmpty(username)) {
            throw new EMInvalidArgumentException("username cannot be blank");
        }
        super.id(username);
        this.username = username;
        this.uuid = uuid;
        this.canLogin = canLogin;
        this.pushResources = pushResources;
    }

    public static void validateUsername(String username) {
        if (StringUtil.isNullOrEmpty(username)) {
            throw new EMInvalidArgumentException("username must not be null or blank");
        }
        if (!USERNAME_PATTERN.matcher(username).matches()) {
            throw new EMInvalidArgumentException(
                    String.format("username '%s' should match regex %s", username,
                            USERNAME_PATTERN.toString()));
        }
    }

    public static void validatePassword(String password) {
        if (StringUtil.isNullOrEmpty(password)) {
            throw new EMInvalidArgumentException("password must not be null or empty");
        }
        if (!PASSWORD_PATTERN.matcher(password).matches()) {
            // DO NOT show password
            throw new EMInvalidArgumentException(
                    String.format("password should match regex %s", PASSWORD_PATTERN.toString()));
        }
    }

    public String getUsername() {
        return this.username;
    }

    public String getUuid() {
        return this.uuid;
    }

    public boolean getCanLogin() {
        return this.canLogin;
    }

    public List<PushResource> getPushResources() {
        return pushResources;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof EMUser)) {
            return false;
        }
        EMUser emUser = (EMUser) o;
        return username.equals(emUser.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

    @Override public String toString() {
        return "EMUser{" +
                "username='" + username + '\'' +
                ", uuid='" + uuid + '\'' +
                ", canLogin=" + canLogin +
                ", pushResources=" + pushResources +
                '}';
    }
}
