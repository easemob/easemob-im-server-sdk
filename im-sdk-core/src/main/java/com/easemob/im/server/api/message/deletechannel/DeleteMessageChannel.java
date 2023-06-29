package com.easemob.im.server.api.message.deletechannel;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import reactor.core.publisher.Mono;

public class DeleteMessageChannel {
    private final Context context;

    public DeleteMessageChannel(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(String username, String channelName, String channelType, Boolean deleteRoam) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/users/%s/user_channel", username))
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new DeleteMessageChannelRequest(channelName, channelType, deleteRoam)))))
                        .responseSingle((rsp, buf) -> {
                            return buf.switchIfEmpty(
                                            Mono.error(new EMUnknownException("response is null")))
                                    .flatMap(byteBuf -> {
                                        ErrorMapper mapper = new DefaultErrorMapper();
                                        mapper.statusCode(rsp);
                                        mapper.checkError(byteBuf);
                                        return Mono.just(byteBuf);
                                    });
                        }))
                .then();
    }
}
