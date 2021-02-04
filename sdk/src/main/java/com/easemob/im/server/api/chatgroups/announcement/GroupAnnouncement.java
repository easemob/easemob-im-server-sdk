package com.easemob.im.server.api.chatgroups.announcement;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import reactor.core.publisher.Mono;

public class GroupAnnouncement {

    public static Mono<String> get(Context context, String groupId) {
        return context.getHttpClient()
            .get()
            .uri(String.format("/chatgroups/%s/announcement", groupId))
            .responseSingle((rsp, buf) -> context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> context.getCodec().decode(buf, GroupAnnouncementGetResponse.class))
            .map(GroupAnnouncementGetResponse::getAnnouncement);
    }

    public static Mono<Void> update(Context context, String groupId, String announcement) {
        if (announcement.length() > 512) {
            return Mono.error(new EMInvalidArgumentException("announcement must not be longer than 512 characters"));
        }
        return context.getHttpClient()
            .post()
            .uri(String.format("/chatgroups/%s/announcement", groupId))
            .send(Mono.create(sink -> sink.success(context.getCodec().encode(new GroupAnnouncementResource(announcement)))))
            .response()
            .flatMap(rsp -> context.getErrorMapper().apply(rsp))
            .then();
    }
}

