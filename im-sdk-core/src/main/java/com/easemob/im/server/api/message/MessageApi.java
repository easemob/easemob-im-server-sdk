package com.easemob.im.server.api.message;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.message.history.MessageHistory;
import com.easemob.im.server.api.message.missed.MessageMissed;
import com.easemob.im.server.api.message.missed.MissedMessageCount;
import com.easemob.im.server.api.message.send.SendMessage;
import com.easemob.im.server.api.message.status.MessageStatus;
import com.easemob.im.server.model.EMKeyValue;
import com.easemob.im.server.model.EMMessage;
import com.easemob.im.server.model.EMSentMessageIds;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Set;

/**
 * 消息API。
 */
public class MessageApi {

    private MessageMissed missed;

    private SendMessage sendMessage;

    private MessageHistory messageHistory;

    private MessageStatus messageStatus;

    public MessageApi(Context context) {
        EMProperties properties = context.getProperties();
        this.missed = new MessageMissed(context);
        this.sendMessage = new SendMessage(context);
        this.messageHistory = new MessageHistory(context, properties.getServerTimezone());
        this.messageStatus = new MessageStatus(context);
    }

    /**
     * 查询用户离线消息数。
     *
     * @param username 用户名
     * @return 每个对话的离线消息数
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E7%94%A8%E6%88%B7%E7%A6%BB%E7%BA%BF%E6%B6%88%E6%81%AF%E6%95%B0">获取用户离线消息数</a>
     */
    public Flux<MissedMessageCount> countMissedMessages(String username) {
        return this.missed.count(username);
    }

    /**
     * 查询某条离线消息的状态，如是否已经传达。
     *
     * @param messageId 要查看对消息ID
     * @param toUser    消息接收方的用户名
     * @return 是否送达或错误
     * @see <a href="http://docs-im.easemob.com/im/server/ready/user#%E8%8E%B7%E5%8F%96%E6%9F%90%E6%9D%A1%E7%A6%BB%E7%BA%BF%E6%B6%88%E6%81%AF%E7%8A%B6%E6%80%81">获取某条离线消息状态</a>
     */
    public Mono<Boolean> isMessageDeliveredToUser(String messageId, String toUser) {
        return this.messageStatus.isMessageDeliveredToUser(messageId, toUser);
    }

    /**
     * 构造消息并发送。
     * <p>
     * 例如，发送一条带有扩展字段的文本消息:
     * <pre>{@code
     * EMService service;
     * service.message().send()
     *                  .fromUser("alice").toUser("rabbit")
     *                  .text(msg -> msg.text("hello"))
     *                  .extension(exts -> exts.add(EMKeyValue.of("timeout", 1)))
     *                  .send()
     *                  .block(Duration.ofSeconds(3));
     * }</pre>
     *
     * @return 发送消息的构造器
     * @see <a href="http://docs-im.easemob.com/im/server/basics/messages">发送消息</a>
     */
    public SendMessage send() {
        return this.sendMessage;
    }

    /**
     * 发送消息。
     *
     * @param from       发送者用户名
     * @param toType     目标类型，可以是 `users`, `chatgroups`, `chatrooms`
     * @param tos        目标id列表
     * @param message    要发送的消息
     * @param extensions 要发送的扩展，可以为空
     * @return 发消息响应或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/messages">发送消息</a>
     */
    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions) {
        return this.sendMessage.send(from, toType, tos, message, extensions);
    }

    /**
     * 发送消息(只投递在线消息)。
     *
     * @param from       发送者用户名
     * @param toType     目标类型，可以是 `users`, `chatgroups`, `chatrooms`
     * @param tos        目标id列表
     * @param message    要发送的消息
     * @param extensions 要发送的扩展，可以为空
     * @param routeType  只投递在线消息，请传入 `ROUTE_ONLINE`
     * @return 发消息响应或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/messages">发送消息</a>
     */
    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
                                       EMMessage message, Set<EMKeyValue> extensions, String routeType) {
        return this.sendMessage.send(from, toType, tos, message, extensions, routeType);
    }

    /**
     * 获取消息历史文件的下载地址。
     * 历史文件是每小时一个文件，比如指定12:10，则返回12点的历史文件。
     *
     * @param instant 时间点
     * @return 下载地址或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatrecord#%E8%8E%B7%E5%8F%96%E5%8E%86%E5%8F%B2%E6%B6%88%E6%81%AF%E6%96%87%E4%BB%B6">获取历史消息文件</a>
     */
    public Mono<String> getHistoryAsUri(Instant instant) {
        return this.messageHistory.toUri(instant);
    }

    /**
     * 下载消息历史文件到本地。
     * 消息历史文件是gz压缩的。
     *
     * @param instant  时间点
     * @param dir      下载目录
     * @param filename 文件名，如果为空，则默认为YYYYMMDD.gz
     * @return 下载文件的路径或错误
     * @see <a href="http://docs-im.easemob.com/im/server/basics/chatrecord#%E8%8E%B7%E5%8F%96%E5%8E%86%E5%8F%B2%E6%B6%88%E6%81%AF%E6%96%87%E4%BB%B6">获取历史消息文件</a>
     */
    public Mono<Path> getHistoryAsLocalFile(Instant instant, Path dir, String filename) {
        return this.messageHistory.toLocalFile(instant, dir, filename);
    }

}
