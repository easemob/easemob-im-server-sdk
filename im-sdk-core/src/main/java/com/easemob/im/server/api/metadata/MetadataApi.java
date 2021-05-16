package com.easemob.im.server.api.metadata;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.metadata.user.capacity.MetadataCapacity;
import com.easemob.im.server.api.metadata.user.delete.MetadataDeleteUser;
import com.easemob.im.server.api.metadata.user.get.MetadataGetUser;
import com.easemob.im.server.api.metadata.user.set.MetadataSetUser;
import com.easemob.im.server.model.EMMetadata;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 用户属性API。
 */
public class MetadataApi {

    private MetadataSetUser metadataSetUser;

    private MetadataGetUser metadataGetUser;

    private MetadataCapacity metadataCapacity;

    private MetadataDeleteUser metadataDeleteUser;

    public MetadataApi(Context context) {
        this.metadataSetUser = new MetadataSetUser(context);
        this.metadataGetUser = new MetadataGetUser(context);
        this.metadataCapacity = new MetadataCapacity(context);
        this.metadataDeleteUser = new MetadataDeleteUser(context);
    }

    /**
     * 设置用户属性
     * @param username 要被设置用户属性的用户名
     * @param metadata 要设置的属性
     * @return 成功或错误
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E8%AE%BE%E7%BD%AE%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7">设置用户属性</a>
     */
    public Mono<Void> setUser(String username, Map<String, String> metadata) {
        return this.metadataSetUser.setUser(username, metadata);
    }

    /**
     * 获取用户属性
     * @param username 要获取的用户名
     * @return 返回获取到的用户属性
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7">获取用户属性</a>
     */
    public Mono<EMMetadata> getUser(String username) {
        return this.metadataGetUser.getUser(username);
    }

    /**
     * 获取app用户属性容量
     * @return 返回获取到的容量大小，单位Bytes
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7%E6%80%BB%E9%87%8F%E5%A4%A7%E5%B0%8F">获取app用户属性容量</a>
     */
    public Mono<Long> getCapacity() {
        return this.metadataCapacity.getCapacity();
    }

    /**
     * 删除用户属性
     * @param username 要被删除用户属性的用户名
     * @return 返回删除是否成功的结果
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E5%88%A0%E9%99%A4%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7">删除用户属性</a>
     */
    public Mono<Boolean> deleteUser(String username) {
        return this.metadataDeleteUser.deleteUser(username);
    }

}
