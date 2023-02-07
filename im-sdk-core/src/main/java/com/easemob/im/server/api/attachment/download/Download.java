package com.easemob.im.server.api.attachment.download;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.DefaultErrorMapper;
import com.easemob.im.server.api.ErrorMapper;
import com.easemob.im.server.api.util.FileSystem;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.io.OutputStream;
import java.nio.file.Path;

public class Download {

    private Context context;

    public Download(Context context) {
        this.context = context;
    }

    public Mono<Path> toLocalFile(String id, Path dir, String filename) {
        Path local = FileSystem.choosePath(dir, filename);
        return Mono.<OutputStream>create(sink -> sink.success(FileSystem.open(local)))
                .flatMap(out -> this.context.getHttpClient()
                        .flatMap(httpClient -> httpClient.get()
                                .uri(String.format("/chatfiles/%s", id))
                                .responseSingle((rsp, buf) -> Mono.zip(Mono.just(rsp), buf)))
                        .map(tuple2 -> {
                            ErrorMapper mapper = new DefaultErrorMapper();
                            mapper.statusCode(tuple2.getT1());
                            mapper.checkError(tuple2.getT2());

                            return FileSystem.append(out, tuple2.getT2());
                        })
                        .doOnSuccess(suc -> {
                            FileSystem.close(out);
                        })
                        .then())
                .thenReturn(local);
    }

}
