package com.easemob.im.server.api.group.get;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMNotFoundException;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMGroup;
import reactor.core.publisher.Mono;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;

public class GetGroup {
    private Context context;

    public GetGroup(Context context) {
        this.context = context;
    }

    public Mono<EMGroup> execute(String groupId) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatgroups/%s", groupId))
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
                .map(buf -> {
                    GetGroupResponse response =
                            this.context.getCodec().decode(buf, GetGroupResponse.class);
                    buf.release();
                    return response;
                })
                .map(rsp -> {
                    EMGroup detail = rsp.toGroupDetail(groupId);
                    if (detail == null) {
                        throw new EMNotFoundException(String.format("group:%s", groupId));
                    }
                    return detail;
                });
    }

    public Mono<List<EMGroup>> execute(List<String> groupIdList) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatgroups/%s", join(groupIdList, ",")))
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
                .map(buf -> {
                    GetGroupResponse response =
                            this.context.getCodec().decode(buf, GetGroupResponse.class);
                    buf.release();
                    return response;
                })
                .map(GetGroupResponse::toGroupDetails);
    }

    public static String join(Collection var0, String var1) {
        StringBuffer var2 = new StringBuffer();

        for(Iterator var3 = var0.iterator(); var3.hasNext(); var2.append((String)var3.next())) {
            if (var2.length() != 0) {
                var2.append(var1);
            }
        }

        return var2.toString();
    }
}
