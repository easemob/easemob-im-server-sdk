package com.easemob.im.server.api.message.send;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.*;
import io.github.resilience4j.core.StringUtils;
import reactor.core.publisher.Mono;

import java.util.*;
import java.util.function.Consumer;

public class SendMessage {
    private final Context context;

    public SendMessage(Context context) {
        this.context = context;
    }

    public RouteSpec fromUser(String username) {
        return new RouteSpec(username);
    }

    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages?useMsgId=true")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new SendMessageRequest(from, toType, tos, message,
                                        SendMessageRequest.parseExtensions(extensions))))))
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
                .map(byteBuf -> {
                    SendMessageResponse sendMessageResponse =
                            context.getCodec().decode(byteBuf, SendMessageResponse.class);
                    byteBuf.release();
                    return sendMessageResponse.toEMSentMessages();
                });
    }

    public Mono<EMSentMessageResults> sendWithoutMsgId(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new SendMessageRequest(from, toType, tos, message,
                                        SendMessageRequest.parseExtensions(extensions))))))
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
                .map(byteBuf -> {
                    SendMessageResultResponse sendMessageResultResponse =
                            context.getCodec().decode(byteBuf, SendMessageResultResponse.class);
                    byteBuf.release();
                    return sendMessageResultResponse.toEMSentMessages();
                });
    }

    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions, String routeType) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages?useMsgId=true")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new SendMessageRequest(from, toType, tos, message,
                                        SendMessageRequest.parseExtensions(extensions),
                                        routeType)))))
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
                .map(byteBuf -> {
                    SendMessageResponse sendMessageResponse = context.getCodec()
                            .decode(byteBuf, SendMessageResponse.class);
                    byteBuf.release();
                    return sendMessageResponse.toEMSentMessages();
                });
    }

    public Mono<EMSentMessageResults> sendWithoutMsgId(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions, String routeType) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new SendMessageRequest(from, toType, tos, message,
                                        SendMessageRequest.parseExtensions(extensions),
                                        routeType)))))
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
                .map(byteBuf -> {
                    SendMessageResultResponse sendMessageResultResponse =
                            context.getCodec()
                                    .decode(byteBuf, SendMessageResultResponse.class);
                    byteBuf.release();
                    return sendMessageResultResponse.toEMSentMessages();
                });
    }

    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions, Boolean syncDevice) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages?useMsgId=true")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new SendMessageRequest(from, toType, tos, message,
                                        SendMessageRequest.parseExtensions(extensions),
                                        syncDevice)))))
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
                .map(byteBuf -> {
                    SendMessageResponse sendMessageResponse = context.getCodec()
                            .decode(byteBuf, SendMessageResponse.class);
                    byteBuf.release();
                    return sendMessageResponse.toEMSentMessages();
                });
    }

    public Mono<EMSentMessageResults> sendWithoutMsgId(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions, Boolean syncDevice) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new SendMessageRequest(from, toType, tos, message,
                                        SendMessageRequest.parseExtensions(extensions),
                                        syncDevice)))))
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
                .map(byteBuf -> {
                    SendMessageResultResponse sendMessageResultResponse =
                            context.getCodec()
                                    .decode(byteBuf, SendMessageResultResponse.class);
                    byteBuf.release();
                    return sendMessageResultResponse.toEMSentMessages();
                });
    }

    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions, String routeType, Boolean syncDevice) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages?useMsgId=true")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new SendMessageRequest(from, toType, tos, message,
                                        SendMessageRequest.parseExtensions(extensions), routeType,
                                        syncDevice)))))
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
                .map(byteBuf -> {
                    SendMessageResponse sendMessageResponse = context.getCodec()
                            .decode(byteBuf, SendMessageResponse.class);
                    byteBuf.release();
                    return sendMessageResponse.toEMSentMessages();
                });
    }

    public Mono<EMSentMessageResults> sendWithoutMsgId(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions, String routeType, Boolean syncDevice) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages")
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new SendMessageRequest(from, toType, tos, message,
                                        SendMessageRequest.parseExtensions(extensions), routeType,
                                        syncDevice)))))
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
                .map(byteBuf -> {
                    SendMessageResultResponse sendMessageResultResponse =
                            context.getCodec()
                                    .decode(byteBuf, SendMessageResultResponse.class);
                    byteBuf.release();
                    return sendMessageResultResponse.toEMSentMessages();
                });
    }

    public class RouteSpec {

        private String from;

        public RouteSpec(String from) {
            this.from = from;
        }

        public MessageSpec toUser(String username) {
            Set<String> tos = new HashSet<>();
            tos.add(username);
            return new MessageSpec(from, "users", tos);
        }

        public MessageSpec toUsers(Set<String> usernames) {
            return new MessageSpec(from, "users", usernames);
        }

        public MessageSpec toGroup(String groupId) {
            Set<String> tos = new HashSet<>();
            tos.add(groupId);
            return new MessageSpec(from, "chatgroups", tos);
        }

        public MessageSpec toGroups(Set<String> groupIds) {
            return new MessageSpec(from, "chatgroups", groupIds);
        }

        public MessageSpec toRoom(String roomId) {
            Set<String> tos = new HashSet<>();
            tos.add(roomId);
            return new MessageSpec(from, "chatrooms", tos);
        }

        public MessageSpec toRooms(Set<String> roomIds) {
            return new MessageSpec(from, "chatrooms", roomIds);
        }
    }


    public class MessageSpec {

        private String from;

        private String toType;

        private Set<String> tos;

        public MessageSpec(String from, String toType, Set<String> tos) {
            this.from = from;
            this.toType = toType;
            this.tos = tos;
        }

        public SendSpec text(Consumer<EMTextMessage> customizer) {
            EMTextMessage message = new EMTextMessage();
            customizer.accept(message);
            return new SendSpec(this.from, this.toType, this.tos, message);
        }

        public SendSpec image(Consumer<EMImageMessage> customizer) {
            EMImageMessage message = new EMImageMessage();
            customizer.accept(message);
            return new SendSpec(this.from, this.toType, this.tos, message);
        }

        public SendSpec voice(Consumer<EMVoiceMessage> customizer) {
            EMVoiceMessage message = new EMVoiceMessage();
            customizer.accept(message);
            return new SendSpec(this.from, this.toType, this.tos, message);
        }

        public SendSpec video(Consumer<EMVideoMessage> customizer) {
            EMVideoMessage message = new EMVideoMessage();
            customizer.accept(message);
            return new SendSpec(this.from, this.toType, this.tos, message);
        }

        public SendSpec location(Consumer<EMLocationMessage> customizer) {
            EMLocationMessage message = new EMLocationMessage();
            customizer.accept(message);
            return new SendSpec(this.from, this.toType, this.tos, message);
        }

        public SendSpec file(Consumer<EMFileMessage> customizer) {
            EMFileMessage message = new EMFileMessage();
            customizer.accept(message);
            return new SendSpec(this.from, this.toType, this.tos, message);
        }

        public SendSpec command(Consumer<EMCommandMessage> customizer) {
            EMCommandMessage message = new EMCommandMessage();
            customizer.accept(message);
            return new SendSpec(this.from, this.toType, this.tos, message);
        }

        public SendSpec custom(Consumer<EMCustomMessage> customizer) {
            EMCustomMessage message = new EMCustomMessage();
            customizer.accept(message);
            return new SendSpec(this.from, this.toType, this.tos, message);
        }
    }


    public class SendSpec {

        private String from;

        private String toType;

        private Set<String> tos;

        private EMMessage message;

        private Set<EMKeyValue> extensions;

        private String routeType;

        private Boolean syncDevice;

        SendSpec(String from, String toType, Set<String> tos, EMMessage message) {
            this.from = from;
            this.toType = toType;
            this.tos = tos;
            this.message = message;
        }

        SendSpec(String from, String toType, Set<String> tos, EMMessage message, String routeType) {
            this.from = from;
            this.toType = toType;
            this.tos = tos;
            this.message = message;
            this.routeType = routeType;
        }

        SendSpec(String from, String toType, Set<String> tos, EMMessage message,
                Boolean syncDevice) {
            this.from = from;
            this.toType = toType;
            this.tos = tos;
            this.message = message;
            this.syncDevice = syncDevice;
        }

        SendSpec(String from, String toType, Set<String> tos, EMMessage message, String routeType,
                Boolean syncDevice) {
            this.from = from;
            this.toType = toType;
            this.tos = tos;
            this.message = message;
            this.routeType = routeType;
            this.syncDevice = syncDevice;
        }

        public SendSpec extension(Consumer<Set<EMKeyValue>> customizer) {
            if (this.extensions == null) {
                this.extensions = new HashSet<>();
            }
            customizer.accept(this.extensions);
            return this;
        }

        public SendSpec routeType(String type) {
            this.routeType = type;
            return this;
        }

        public SendSpec syncDevice(Boolean syncDevice) {
            this.syncDevice = syncDevice;
            return this;
        }

        public Mono<EMSentMessageIds> send() {
            if (StringUtils.isNotEmpty(routeType) && syncDevice != null) {
                return SendMessage.this
                        .send(this.from, this.toType, this.tos, this.message, this.extensions,
                                this.routeType, this.syncDevice);
            } else if (StringUtils.isNotEmpty(routeType)) {
                return SendMessage.this
                        .send(this.from, this.toType, this.tos, this.message, this.extensions,
                                this.routeType);
            } else if (syncDevice != null) {
                return SendMessage.this
                        .send(this.from, this.toType, this.tos, this.message, this.extensions,
                                this.syncDevice);
            } else {
                return SendMessage.this
                        .send(this.from, this.toType, this.tos, this.message, this.extensions);
            }
        }

        public Mono<EMSentMessageResults> sendWithoutMsgId() {
            if (StringUtils.isNotEmpty(routeType) && syncDevice != null) {
                return SendMessage.this
                        .sendWithoutMsgId(this.from, this.toType, this.tos, this.message,
                                this.extensions, this.routeType, this.syncDevice);
            } else if (StringUtils.isNotEmpty(routeType)) {
                return SendMessage.this
                        .sendWithoutMsgId(this.from, this.toType, this.tos, this.message,
                                this.extensions, this.routeType);
            } else if (syncDevice != null) {
                return SendMessage.this
                        .sendWithoutMsgId(this.from, this.toType, this.tos, this.message,
                                this.extensions, this.syncDevice);
            } else {
                return SendMessage.this
                        .sendWithoutMsgId(this.from, this.toType, this.tos, this.message,
                                this.extensions);
            }
        }

    }

}
