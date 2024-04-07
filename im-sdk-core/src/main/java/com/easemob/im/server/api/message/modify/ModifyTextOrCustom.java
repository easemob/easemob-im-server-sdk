package com.easemob.im.server.api.message.modify;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import io.netty.util.ReferenceCounted;
import reactor.core.publisher.Mono;

import java.util.Map;

public class ModifyTextOrCustom {

    private final Context context;

    public ModifyTextOrCustom(Context context) {
        this.context = context;
    }

    public Mono<Void> execute(String messageId, String username, NewMessage newMessage,
            Map<String, Object> newExt, boolean isCombineExt) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.put()
                        .uri(String.format("/messages/rewrite/%s", messageId))
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new ModifyTextOrCustomRequest(username, newMessage, newExt, isCombineExt)))))
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
                .doOnSuccess(ReferenceCounted::release)
                .then();
    }
}
