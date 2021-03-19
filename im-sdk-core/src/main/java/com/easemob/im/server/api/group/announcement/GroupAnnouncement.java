package com.easemob.im.server.api.group.announcement;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import reactor.core.publisher.Mono;

public class GroupAnnouncement {

    private Context context;

    public GroupAnnouncement(Context context) {
        this.context = context;
    }

    public Mono<String> get(String groupId) {
        return context.getHttpClient()
            .get()
            .uri(String.format("/chatgroups/%s/announcement", groupId))
            .responseSingle((rsp, buf) -> this.context.getErrorMapper().apply(rsp).then(buf))
            .map(buf -> this.context.getCodec().decode(buf, GroupAnnouncementGetResponse.class))
            .map(GroupAnnouncementGetResponse::getAnnouncement);
    }

    public Mono<Void> set(String groupId, String announcement) {
        if (announcement.length() > 512) {
            return Mono.error(new EMInvalidArgumentException("announcement must not be longer than 512 characters"));
        }
        return context.getHttpClient()
            .post()
            .uri(String.format("/chatgroups/%s/announcement", groupId))
            .send(Mono.create(sink -> sink.success(this.context.getCodec().encode(new GroupAnnouncementResource(announcement)))))
            .response()
            .flatMap(rsp -> this.context.getErrorMapper().apply(rsp))
            .then();
    }
}

