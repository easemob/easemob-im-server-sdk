package com.easemob.im.server.api.group.list;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.EMPage;
import io.netty.handler.codec.http.QueryStringEncoder;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

public class GroupList {

    private Context context;

    public GroupList(Context context) {
        this.context = context;
    }

    public Flux<String> all(int limit) {
        return next(limit, null)
                .expand(rsp -> rsp.getCursor() == null ?
                        Mono.empty() :
                        next(limit, rsp.getCursor()))
                .concatMapIterable(EMPage::getValues);
    }

    public Mono<EMPage<String>> next(int limit, String cursor) {
        QueryStringEncoder encoder = new QueryStringEncoder("/chatgroups");
        encoder.addParam("limit", String.valueOf(limit));
        if (cursor != null) {
            encoder.addParam("cursor", cursor);
        }
        String uriString = encoder.toString();
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(uriString)
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
                    GroupListResponse response =
                            this.context.getCodec().decode(buf, GroupListResponse.class);
                    buf.release();
                    return response;
                })
                .map(GroupListResponse::toEMPage);
    }

    public Flux<String> userJoined(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/joined_chatgroups", username))
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
                    JoinGroupListResponse response =
                            this.context.getCodec().decode(buf, JoinGroupListResponse.class);
                    buf.release();
                    return response;
                })
                .flatMapIterable(JoinGroupListResponse::getGroupIds);
    }

    public Flux<GroupResource> allWithInfo(int limit) {
        return nextWithInfo(limit, null)
                .expand(rsp -> rsp.getCursor() == null ?
                        Mono.empty() :
                        nextWithInfo(limit, rsp.getCursor()))
                .concatMapIterable(EMPage::getValues);
    }

    public Mono<EMPage<GroupResource>> nextWithInfo(int limit, String cursor) {
        QueryStringEncoder encoder = new QueryStringEncoder("/chatgroups");
        encoder.addParam("limit", String.valueOf(limit));
        if (cursor != null) {
            encoder.addParam("cursor", cursor);
        }
        String uriString = encoder.toString();
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(uriString)
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
                    GroupListResponse response =
                            this.context.getCodec().decode(buf, GroupListResponse.class);
                    buf.release();
                    return response;
                })
                .map(GroupListResponse::toEMPageWithInfo);
    }

    public Flux<JoinGroupResource> userJoinedWithInfo(String username) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/users/%s/joined_chatgroups", username))
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
                    JoinGroupListResponse response =
                            this.context.getCodec().decode(buf, JoinGroupListResponse.class);
                    buf.release();
                    return response;
                })
                .flatMapIterable(JoinGroupListResponse::getGroups);
    }

    public Mono<List<JoinGroupResource>> userJoinedWithInfo(String username, int pageNum, int pageSize) {
        QueryStringEncoder encoder = new QueryStringEncoder(String.format("/users/%s/joined_chatgroups", username));
        encoder.addParam("pagenum", String.valueOf(pageNum));
        encoder.addParam("pagesize", String.valueOf(pageSize));

        String uriString = encoder.toString();
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(uriString)
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
                    JoinGroupListResponse response =
                            this.context.getCodec().decode(buf, JoinGroupListResponse.class);
                    buf.release();
                    return response;
                })
                .map(JoinGroupListResponse::getGroups);
    }
}
