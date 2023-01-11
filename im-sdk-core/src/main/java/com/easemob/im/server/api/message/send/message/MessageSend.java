package com.easemob.im.server.api.message.send.message;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.message.send.SendMessageRequest;
import com.easemob.im.server.api.message.send.SendMessageResponse;
import com.easemob.im.server.model.*;
import io.github.resilience4j.core.StringUtils;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class MessageSend {
    private final Context context;

    public MessageSend(Context context) {
        this.context = context;
    }

    public MessageSend.RouteSpec fromUser(String username) {
        return new MessageSend.RouteSpec(username);
    }

    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages/" + toType)
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new MessageSendRequest(from, tos, message,
                                        MessageSendRequest.parseExtensions(extensions))))))
                        .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(byteBuf -> {
                    SendMessageResponse sendMessageResponse =
                            context.getCodec().decode(byteBuf, SendMessageResponse.class);
                    return sendMessageResponse.toEMSentMessages();
                });
    }

    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions, String routeType) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages/" + toType)
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new MessageSendRequest(from, tos, message,
                                        SendMessageRequest.parseExtensions(extensions),
                                        routeType)))))
                        .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(byteBuf -> {
                    SendMessageResponse sendMessageResponse = context.getCodec()
                            .decode(byteBuf, SendMessageResponse.class);
                    return sendMessageResponse.toEMSentMessages();
                });
    }

    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions, Boolean syncDevice) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages/" + toType)
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new MessageSendRequest(from, tos, message,
                                        SendMessageRequest.parseExtensions(extensions),
                                        syncDevice)))))
                        .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(byteBuf -> {
                    SendMessageResponse sendMessageResponse = context.getCodec()
                            .decode(byteBuf, SendMessageResponse.class);
                    return sendMessageResponse.toEMSentMessages();
                });
    }

    public Mono<EMSentMessageIds> send(String from, String toType, Set<String> tos,
            EMMessage message, Set<EMKeyValue> extensions, String routeType, Boolean syncDevice) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri("/messages/" + toType)
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(new MessageSendRequest(from, tos, message,
                                        MessageSendRequest.parseExtensions(extensions), routeType,
                                        syncDevice)))))
                        .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                .map(tuple2 -> {
                    ErrorMapper mapper = new DefaultErrorMapper();
                    mapper.statusCode(tuple2.getT1());
                    mapper.checkError(tuple2.getT2());

                    return tuple2.getT2();
                })
                .map(byteBuf -> {
                    SendMessageResponse sendMessageResponse = context.getCodec()
                            .decode(byteBuf, SendMessageResponse.class);
                    return sendMessageResponse.toEMSentMessages();
                });
    }

    public class RouteSpec {

        private String from;

        public RouteSpec(String from) {
            this.from = from;
        }

        public MessageSend.MessageSpec toUser(String username) {
            Set<String> tos = new HashSet<>();
            tos.add(username);
            return new MessageSend.MessageSpec(from, "users", tos);
        }

        public MessageSend.MessageSpec toUsers(Set<String> usernames) {
            return new MessageSend.MessageSpec(from, "users", usernames);
        }

        public MessageSend.MessageSpec toGroup(String groupId) {
            Set<String> tos = new HashSet<>();
            tos.add(groupId);
            return new MessageSend.MessageSpec(from, "chatgroups", tos);
        }

        public MessageSend.MessageSpec toGroups(Set<String> groupIds) {
            return new MessageSend.MessageSpec(from, "chatgroups", groupIds);
        }

        public MessageSend.MessageSpec toRoom(String roomId) {
            Set<String> tos = new HashSet<>();
            tos.add(roomId);
            return new MessageSend.MessageSpec(from, "chatrooms", tos);
        }

        public MessageSend.MessageSpec toRooms(Set<String> roomIds) {
            return new MessageSend.MessageSpec(from, "chatrooms", roomIds);
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

        public MessageSend.SendSpec text(Consumer<EMTextMessage> customizer) {
            EMTextMessage message = new EMTextMessage();
            customizer.accept(message);
            return new MessageSend.SendSpec(this.from, this.toType, this.tos, message);
        }

        public MessageSend.SendSpec image(Consumer<EMImageMessage> customizer) {
            EMImageMessage message = new EMImageMessage();
            customizer.accept(message);
            return new MessageSend.SendSpec(this.from, this.toType, this.tos, message);
        }

        public MessageSend.SendSpec voice(Consumer<EMVoiceMessage> customizer) {
            EMVoiceMessage message = new EMVoiceMessage();
            customizer.accept(message);
            return new MessageSend.SendSpec(this.from, this.toType, this.tos, message);
        }

        public MessageSend.SendSpec video(Consumer<EMVideoMessage> customizer) {
            EMVideoMessage message = new EMVideoMessage();
            customizer.accept(message);
            return new MessageSend.SendSpec(this.from, this.toType, this.tos, message);
        }

        public MessageSend.SendSpec location(Consumer<EMLocationMessage> customizer) {
            EMLocationMessage message = new EMLocationMessage();
            customizer.accept(message);
            return new MessageSend.SendSpec(this.from, this.toType, this.tos, message);
        }

        public MessageSend.SendSpec file(Consumer<EMFileMessage> customizer) {
            EMFileMessage message = new EMFileMessage();
            customizer.accept(message);
            return new MessageSend.SendSpec(this.from, this.toType, this.tos, message);
        }

        public MessageSend.SendSpec command(Consumer<EMCommandMessage> customizer) {
            EMCommandMessage message = new EMCommandMessage();
            customizer.accept(message);
            return new MessageSend.SendSpec(this.from, this.toType, this.tos, message);
        }

        public MessageSend.SendSpec custom(Consumer<EMCustomMessage> customizer) {
            EMCustomMessage message = new EMCustomMessage();
            customizer.accept(message);
            return new MessageSend.SendSpec(this.from, this.toType, this.tos, message);
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

        public MessageSend.SendSpec extension(Consumer<Set<EMKeyValue>> customizer) {
            if (this.extensions == null) {
                this.extensions = new HashSet<>();
            }
            customizer.accept(this.extensions);
            return this;
        }

        public MessageSend.SendSpec routeType(String type) {
            this.routeType = type;
            return this;
        }

        public MessageSend.SendSpec syncDevice(Boolean syncDevice) {
            this.syncDevice = syncDevice;
            return this;
        }

        public Mono<EMSentMessageIds> send() {
            if (StringUtils.isNotEmpty(routeType) && syncDevice != null) {
                return MessageSend.this
                        .send(this.from, this.toType, this.tos, this.message, this.extensions,
                                this.routeType, this.syncDevice);
            } else if (StringUtils.isNotEmpty(routeType)) {
                return MessageSend.this
                        .send(this.from, this.toType, this.tos, this.message, this.extensions,
                                this.routeType);
            } else if (syncDevice != null) {
                return MessageSend.this
                        .send(this.from, this.toType, this.tos, this.message, this.extensions,
                                this.syncDevice);
            } else {
                return MessageSend.this
                        .send(this.from, this.toType, this.tos, this.message, this.extensions);
            }
        }

    }

}
