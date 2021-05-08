package com.easemob.im.server.api.metadata;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.metadata.user.capacity.MetadataCapacity;
import com.easemob.im.server.api.metadata.user.delete.MetadataDelete;
import com.easemob.im.server.api.metadata.user.get.MetadataGet;
import com.easemob.im.server.api.metadata.user.get.MetadataGetResponse;
import com.easemob.im.server.api.metadata.user.set.MetadataSet;
import com.easemob.im.server.api.metadata.user.set.MetadataSetResponse;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 用户属性API。
 * 支持：
 * - 设置用户属性
 * - 获取用户属性
 * - 获取app用户属性容量
 * - 删除用户属性
 */
public class MetadataApi {

    private MetadataSet metadataSet;

    private MetadataGet metadataGet;

    private MetadataCapacity metadataCapacity;

    private MetadataDelete metadataDelete;

    public MetadataApi(Context context) {
        this.metadataSet = new MetadataSet(context);
        this.metadataGet = new MetadataGet(context);
        this.metadataCapacity = new MetadataCapacity(context);
        this.metadataDelete = new MetadataDelete(context);
    }

    /**
     * 设置用户属性
     * @param username 要被设置用户属性的用户名
     * @param metadata 要设置的属性
     * @return 返回设置的用户属性
     * @see com.easemob.im.server.api.metadata.user.set.MetadataSetResponse
     */
    public Mono<MetadataSetResponse> set(String username, Map<String, String> metadata) {
        return this.metadataSet.set(username, metadata);
    }

    /**
     * 获取用户属性
     * @param username 要获取的用户名
     * @return 返回获取到的用户属性
     * @see com.easemob.im.server.api.metadata.user.get.MetadataGetResponse
     */
    public Mono<MetadataGetResponse> get(String username) {
        return this.metadataGet.get(username);
    }

    /**
     * 获取app用户属性容量
     * @return 返回获取到的容量大小，单位Bytes
     */
    public Mono<Long> getCapacity() {
        return this.metadataCapacity.getCapacity();
    }

    /**
     * 删除用户属性
     * @param username 要被删除用户属性的用户名
     * @return 返回删除是否成功的结果
     */
    public Mono<Boolean> delete(String username) {
        return this.metadataDelete.delete(username);
    }

}
