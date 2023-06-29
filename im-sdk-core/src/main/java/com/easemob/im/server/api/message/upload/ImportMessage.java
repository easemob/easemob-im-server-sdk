package com.easemob.im.server.api.message.upload;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.exception.EMUnknownException;
import com.easemob.im.server.model.*;
import reactor.core.publisher.Mono;

import java.util.HashSet;
import java.util.Set;
import java.util.function.Consumer;

public class ImportMessage {
    private final Context context;

    public ImportMessage(Context context) {
        this.context = context;
    }

    public class RouteSpec {

        private String from;

        public RouteSpec(String from) {
            this.from = from;
        }

        public ImportMessage.MessageSpec toUser(String username) {
            return new ImportMessage.MessageSpec(from, "users", username);
        }

        public ImportMessage.MessageSpec toGroup(String groupId) {
            return new ImportMessage.MessageSpec(from, "chatgroups", groupId);
        }

    }

    public class MessageSpec {

        private String from;

        private String toType;

        private String to;

        public MessageSpec(String from, String toType, String to) {
            this.from = from;
            this.toType = toType;
            this.to = to;
        }

        public ImportSpec text(Consumer<EMTextMessage> customizer) {
            EMTextMessage message = new EMTextMessage();
            customizer.accept(message);
            return new ImportSpec(this.from, this.toType, this.to, message);
        }

        public ImportSpec image(Consumer<EMImageMessage> customizer) {
            EMImageMessage message = new EMImageMessage();
            customizer.accept(message);
            return new ImportSpec(this.from, this.toType, this.to, message);
        }

        public ImportSpec voice(Consumer<EMVoiceMessage> customizer) {
            EMVoiceMessage message = new EMVoiceMessage();
            customizer.accept(message);
            return new ImportSpec(this.from, this.toType, this.to, message);
        }

        public ImportSpec video(Consumer<EMVideoMessage> customizer) {
            EMVideoMessage message = new EMVideoMessage();
            customizer.accept(message);
            return new ImportSpec(this.from, this.toType, this.to, message);
        }

        public ImportSpec location(Consumer<EMLocationMessage> customizer) {
            EMLocationMessage message = new EMLocationMessage();
            customizer.accept(message);
            return new ImportSpec(this.from, this.toType, this.to, message);
        }

        public ImportSpec file(Consumer<EMFileMessage> customizer) {
            EMFileMessage message = new EMFileMessage();
            customizer.accept(message);
            return new ImportSpec(this.from, this.toType, this.to, message);
        }

        public ImportSpec command(Consumer<EMCommandMessage> customizer) {
            EMCommandMessage message = new EMCommandMessage();
            customizer.accept(message);
            return new ImportSpec(this.from, this.toType, this.to, message);
        }

        public ImportSpec custom(Consumer<EMCustomMessage> customizer) {
            EMCustomMessage message = new EMCustomMessage();
            customizer.accept(message);
            return new ImportSpec(this.from, this.toType, this.to, message);
        }
    }

    public class ImportSpec {

        private String from;

        private String toType;

        private String to;

        private EMMessage message;

        private Set<EMKeyValue> extensions;

        private Boolean isAckRead;

        private Long msgTimestamp;

        private Boolean needDownload;

        ImportSpec(String from, String toType, String to, EMMessage message) {
            this.from = from;
            this.toType = toType;
            this.to = to;
            this.message = message;
        }

        public ImportSpec extension(Consumer<Set<EMKeyValue>> customizer) {
            if (this.extensions == null) {
                this.extensions = new HashSet<>();
            }
            customizer.accept(this.extensions);
            return this;
        }

        public ImportSpec isAckRead(Boolean isAckRead) {
            this.isAckRead = isAckRead;
            return this;
        }

        public ImportSpec msgTimestamp(Long msgTimestamp) {
            this.msgTimestamp = msgTimestamp;
            return this;
        }

        public ImportSpec needDownload(Boolean needDownload) {
            this.needDownload = needDownload;
            return this;
        }

        public Mono<String> importChatMessage() {
            if ("users".equalsIgnoreCase(toType)) {
                return ImportMessage.this
                        .importChatMessage(this.from, this.to, this.message, this.extensions,
                                this.isAckRead, this.msgTimestamp, this.needDownload);
            } else {
                return ImportMessage.this
                        .importChatGroupMessage(this.from, this.to, this.message, this.extensions,
                                this.isAckRead, this.msgTimestamp, this.needDownload);
            }
        }
    }

    public RouteSpec from(String from) {
        return new RouteSpec(from);
    }

    public Mono<String> importChatMessage(String from, String to,
            EMMessage message, Set<EMKeyValue> extensions, Boolean isAckRead,
            Long msgTimestamp, Boolean needDownload) {
        String toType = "users";
        return importMessage(from, to, message, extensions, isAckRead, msgTimestamp,
                needDownload, toType);
    }

    public Mono<String> importChatGroupMessage(String from, String to,
            EMMessage message, Set<EMKeyValue> extensions, Boolean isAckRead,
            Long msgTimestamp, Boolean needDownload) {
        String toType = "chatgroups";
        return importMessage(from, to, message, extensions, isAckRead, msgTimestamp,
                needDownload, toType);
    }

    public Mono<String> importMessage(String from, String to,
            EMMessage message, Set<EMKeyValue> extensions, Boolean isAckRead,
            Long msgTimestamp, Boolean needDownload, String toType) {
        ImportMessageRequest importMessageRequest = new ImportMessageRequest(from, to, message,
                ImportMessageRequest.parseExtensions(extensions), isAckRead, msgTimestamp,
                needDownload);
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.post()
                        .uri(String.format("/messages/%s/import", toType))
                        .send(Mono.create(sink -> sink.success(context.getCodec()
                                .encode(importMessageRequest))))
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
                .map(buf -> context.getCodec().decode(buf, ImportMessageResponse.class))
                .map(ImportMessageResponse::getMessageId);
    }
}
