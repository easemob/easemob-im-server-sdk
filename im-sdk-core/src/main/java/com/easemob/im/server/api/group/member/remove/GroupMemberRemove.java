package com.easemob.im.server.api.group.member.remove;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.model.EMRemoveMember;
import com.sun.deploy.util.StringUtils;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GroupMemberRemove {

    private Context context;

    public GroupMemberRemove(Context context) {
        this.context = context;
    }

    public Mono<Void> single(String groupId, String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/chatgroups/%s/users/%s", groupId, username))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .then())
                .onErrorResume(EMNotFoundException.class, errorIgnored -> Mono.empty());
    }

    public Mono<List<EMRemoveMember>> batch(String groupId, List<String> usernames) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.delete()
                        .uri(String.format("/chatgroups/%s/users/%s", groupId, convert(usernames)))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                        .map(buf -> this.context.getCodec().decode(buf, GroupMemberRemoveResponse.class))
                        .map(GroupMemberRemoveResponse::getMembers));
    }

    private String convert(Collection var0) {
        String var1 = ",";

        StringBuffer var2 = new StringBuffer();

        for(Iterator var3 = var0.iterator(); var3.hasNext(); var2.append((String)var3.next())) {
            if (var2.length() != 0) {
                var2.append(var1);
            }
        }

        return var2.toString();
    }
}
