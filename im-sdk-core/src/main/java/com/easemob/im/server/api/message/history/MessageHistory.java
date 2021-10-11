package com.easemob.im.server.api.message.history;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.util.FileSystem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.OutputStream;
import java.nio.file.Path;
import java.time.Instant;
import java.time.OffsetDateTime;
import java.time.ZoneOffset;

public class MessageHistory {

    private Context context;

    private String timezone;

    public MessageHistory(Context context, String timezone) {
        this.context = context;
        this.timezone = timezone;
    }

    public Mono<String> toUri(Instant instant) {
        return this.context.getHttpClient()
                .flatMap(httpClient -> httpClient.get()
                        .uri(String.format("/chatmessages/%s", toPath(instant)))
                        .responseSingle(
                                (rsp, buf) -> {
                                    this.context.getErrorMapper().statusCode(rsp);
                                    return buf;
                                })
                        .doOnNext(buf -> this.context.getErrorMapper().checkError(buf)))
                .map(buf -> this.context.getCodec().decode(buf, MessageHistoryResponse.class))
                .map(MessageHistoryResponse::getUrl);
    }

    public Mono<Path> toLocalFile(Instant instant, Path dir, String filename) {
        if (!dir.toFile().exists()) {
            dir.toFile().mkdirs();
        }

        if (filename == null) {
            filename = toPath(instant) + ".gz";
        }

        String finalFilename = filename;

        return toUri(instant)
                .flatMap(uri -> {
                    Path local = FileSystem.choosePath(dir, finalFilename);
                    return Mono.<OutputStream>create(sink -> sink.success(FileSystem.open(local)))
                            .flatMap(out -> this.context.getHttpClient()
                                    .flatMap(httpClient -> httpClient.get()
                                            .uri(uri)
                                            .response((rsp, buf) -> {
                                                this.context.getErrorMapper().statusCode(rsp);
                                                return buf;
                                            })
                                            .doOnNext(buf -> this.context.getErrorMapper().checkError(buf))
                                            .doOnNext(buf -> FileSystem.append(out, buf))
                                            .doFinally(sig -> FileSystem.close(out))
                                            .then()))
                            .thenReturn(local);
                });
    }

    private String toPath(Instant instant) {
        OffsetDateTime dateTime = instant.atOffset(ZoneOffset.of(this.timezone));
        int year = dateTime.getYear();
        int month = dateTime.getMonthValue();
        int day = dateTime.getDayOfMonth();
        int hour = dateTime.getHour();
        return String.format("%4d%02d%02d%02d", year, month, day, hour);
    }
}
