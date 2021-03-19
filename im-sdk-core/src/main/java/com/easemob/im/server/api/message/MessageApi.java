package com.easemob.im.server.api.message;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.message.history.MessageHistory;
import com.easemob.im.server.api.message.missed.MessageMissed;
import com.easemob.im.server.api.message.missed.MissedMessageCount;
import com.easemob.im.server.api.message.send.SendMessage;
import com.easemob.im.server.api.message.send.SendMessageResponse;
import com.easemob.im.server.api.message.status.MessageStatus;
import com.easemob.im.server.model.EMKeyValue;
import com.easemob.im.server.model.EMMessage;
import com.easemob.im.server.model.EMMessageStatus;
import com.easemob.im.server.model.EMSentMessages;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.file.Path;
import java.time.Instant;
import java.util.Set;

/** 消息API。
 * 支持：
 * - 发送消息
 * - 查询离线消息数
 * - 获取/下载聊天历史
 *
 */
public class MessageApi {

    private Context context;

    private MessageMissed missed;

    private SendMessage sendMessage;

    private MessageHistory messageHistory;

    public MessageApi(Context context) {
        EMProperties properties = context.getProperties();
        this.context = context;
        this.missed = new MessageMissed(context);
        this.sendMessage = new SendMessage(context);
        this.messageHistory = new MessageHistory(context, properties.getServerTimezone());
    }

    /**
     * 查询用户离线消息数。
     *
     * @param username 用户名
     * @return 每个对话的离线消息数
     * @see com.easemob.im.server.api.message.missed.MissedMessageCount
     */
    public Flux<MissedMessageCount> countMissedMessages(String username) {
        return this.missed.count(username);
    }

    public Mono<EMMessageStatus> isMessageDeliveredToUser(String messageId, String toUser) {
        return MessageStatus.isMessageDeliveredToUser(this.context, messageId, toUser);
    }

    /**
     * 构造消息并发送。
     *
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
     * @see com.easemob.im.server.api.message.send.SendMessage
     */
    public SendMessage send() {
        return this.sendMessage;
    }

    /**
     * 发送消息。
     *
     * @param from 发送者用户名
     * @param toType 目标类型，可以是 `users`, `chatgroups`, `chatrooms`
     * @param tos 目标id列表
     * @param message 要发送的消息
     * @param extensions 要发送的扩展，可以为空
     * @return 发消息响应或错误
     * @see com.easemob.im.server.model.EMMessage
     * @see com.easemob.im.server.model.EMKeyValue
     */
    public Mono<EMSentMessages> send(String from, String toType, Set<String> tos, EMMessage message, Set<EMKeyValue> extensions) {
        return this.sendMessage.send(from, toType, tos, message, extensions);
    }

    /**
     * 获取消息历史文件的下载地址。
     * 历史文件是每小时一个文件，比如指定12:10，则返回12点的历史文件。
     * @param instant 时间点
     * @return 下载地址或错误
     */
    public Mono<String> getHistoryAsUri(Instant instant) {
        return this.messageHistory.toUri(instant);
    }

    /**
     * 下载消息历史文件到本地。
     * 消息历史文件是gz压缩的。
     * @param instant 时间点
     * @param dir 下载目录
     * @param filename 文件名，如果为空，则默认为YYYYMMDD.gz
     * @return A {@code Mono} of the path of downloaded file
     */
    public Mono<Path> getHistoryAsLocalFile(Instant instant, Path dir, String filename) {
        return this.messageHistory.toLocalFile(instant, dir, filename);
    }

}
