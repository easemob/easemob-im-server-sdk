package com.easemob.im.server.api.push.offline;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.model.EMConversationType;
import com.easemob.im.server.model.EMNotificationType;
import io.netty.buffer.ByteBuf;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClientResponse;

public class Setting {
    private Context context;

    public Setting(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(String username, EMConversationType conversationType,
            String conversationId, EMNotificationType notificationType, String ignoreInterval,
            long ignoreDuration) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/users/%s/notification/%s/%s", username,
                                conversationType.getConversationType(), conversationId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new SettingRequest(notificationType.getNotificationType(),
                                        ignoreInterval, ignoreDuration)))))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .then();
    }
}
