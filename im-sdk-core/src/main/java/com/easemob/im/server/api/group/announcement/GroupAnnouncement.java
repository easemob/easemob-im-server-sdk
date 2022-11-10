package com.easemob.im.server.api.group.announcement;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import reactor.core.publisher.Mono;

public class GroupAnnouncement {

    private Context context;

    public GroupAnnouncement(Context context) {
        this.context = context;
    }

    public Mono<String> get(String groupId) {
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatgroups/%s/announcement", groupId))
                        .responseSingle(
                                (rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(buf -> this.context.getCodec().decode(buf, GroupAnnouncementGetResponse.class))
                .map(GroupAnnouncementGetResponse::getAnnouncement);
    }

    public Mono<Void> set(String groupId, String announcement) {
        if (announcement.length() > 512) {
            return Mono.error(new EMInvalidArgumentException(
                    "announcement must not be longer than 512 characters"));
        }
        return context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/chatgroups/%s/announcement", groupId))
                        .send(Mono.create(sink -> sink.success(this.context.getCodec()
                                .encode(new GroupAnnouncementResource(announcement)))))
                        .responseSingle((rsp, buf) -> {
                            this.context.getErrorMapper().statusCode(rsp);
                            return buf;
                        })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .then();
    }
}

