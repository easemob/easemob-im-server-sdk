package com.easemob.im.server.api.chatgroups;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.chatgroups.detail.GroupDetail;
import com.easemob.im.server.api.chatgroups.list.GroupList;
import com.easemob.im.server.api.chatgroups.update.GroupUpdate;
import com.easemob.im.server.api.chatgroups.update.GroupUpdateRequest;
import reactor.core.publisher.Mono;

import java.util.function.Function;

public class GroupApiV1 {
    private Context context;

    private String groupId;

    public GroupApiV1(Context context, String groupId) {
        this.context = context;
        this.groupId = groupId;
    }

    public GroupDetail detail() {
        return new GroupDetail(this.context);
    }


    // EMService.group("111").updateSettings(settings -> settings.canMemberJoin(true).maxMembers(2000)).block();

    /**
     * Update group settings.
     *
     * To update max members to 100,
     * <pre>{@code
     *     EMService service;
     *     service.group("111").updateSettings(settings -> settings.maxMembers(100)).block();
     * }</pre>
     *
     * @param customizer update request customizer
     * @return A {@code Mono} complete if successful.
     */
    public Mono<Void> update(Function<GroupUpdateRequest, GroupUpdateRequest> customizer) {
        return new GroupUpdate(this.context, this.groupId, customizer.apply(new GroupUpdateRequest()))
            .execute();
    }
}
