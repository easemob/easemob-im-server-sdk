package com.easemob.im.server.api.metadata;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.metadata.user.usage.MetadataUsage;
import com.easemob.im.server.api.metadata.user.delete.MetadataDelete;
import com.easemob.im.server.api.metadata.user.get.MetadataGet;
import com.easemob.im.server.api.metadata.user.set.MetadataSet;
import com.easemob.im.server.model.EMMetadata;
import com.easemob.im.server.model.EMMetadataUsage;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 用户属性API。
 */
public class MetadataApi {

    private MetadataSet metadataSet;

    private MetadataGet metadataGet;

    private MetadataUsage metadataUsage;

    private MetadataDelete metadataDelete;

    public MetadataApi(Context context) {
        this.metadataSet = new MetadataSet(context);
        this.metadataGet = new MetadataGet(context);
        this.metadataUsage = new MetadataUsage(context);
        this.metadataDelete = new MetadataDelete(context);
    }

    /**
     * 设置用户属性
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * Map<String, String> map = new HashMap<>();
     * map.put("nickname", "昵称");
     * map.put("avatar", "http://www.easemob.com/avatar.png");
     * map.put("phone", "159");
     *
     * try {
     *     service.metadata().setMetadataToUser("username", map).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要被设置用户属性的用户名
     * @param metadata 要设置的属性
     * @return 成功或错误
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E8%AE%BE%E7%BD%AE%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7">设置用户属性</a>
     */
    public Mono<Void> setMetadataToUser(String username, Map<String, String> metadata) {
        return this.metadataSet.toUser(username, metadata);
    }

    /**
     * 获取用户属性
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     EMMetadata userMetadata = service.metadata().getMetadataFromUser("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要获取的用户名
     * @return 返回获取到的用户属性
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7">获取用户属性</a>
     */
    public Mono<EMMetadata> getMetadataFromUser(String username) {
        return this.metadataGet.fromUser(username);
    }

    /**
     * 获取app用户属性当前所占空间。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     EMMetadataUsage metadataUsage = service.metadata().getUsage().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @return 返回占用空间大小，单位Bytes
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7%E6%80%BB%E9%87%8F%E5%A4%A7%E5%B0%8F">获取app用户属性总量大小</a>
     */
    public Mono<EMMetadataUsage> getUsage() {
        return this.metadataUsage.getUsage();
    }

    /**
     * 删除用户属性。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     Boolean isSuc = service.metadata().deleteMetadataFromUser("username").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 要被删除用户属性的用户名
     * @return 返回删除是否成功的结果
     * @see <a href="https://docs-im.easemob.com/im/server/ready/usermetadata#%E5%88%A0%E9%99%A4%E7%94%A8%E6%88%B7%E5%B1%9E%E6%80%A7">删除用户属性</a>
     */
    public Mono<Boolean> deleteMetadataFromUser(String username) {
        return this.metadataDelete.fromUser(username);
    }

}
