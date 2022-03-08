package com.easemob.im.server.api.moderation.export;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.user.create.CreateUserRequest;
import com.easemob.im.server.api.user.get.UserGetResponse;
import reactor.core.publisher.Mono;

public class ExportMessageRecord {

    private Context context;

    public ExportMessageRecord(Context context) {
        this.context = context;
    }

    public Mono<String> export(Long beginTimestamp, Long endTimestamp,
            String targetType, String messageType, String moderationResult, String providerResult) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.headers(header -> header.add("Content-Type", "application/json"))
                        .post()
                        .uri("/moderation/record/message/export")
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new ExportMessageRecordRequest(beginTimestamp, endTimestamp,
                                        targetType, messageType, moderationResult,
                                        providerResult)))))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, ExportMessageRecordResponse.class)
                        .getFileUuid());

    }

}
