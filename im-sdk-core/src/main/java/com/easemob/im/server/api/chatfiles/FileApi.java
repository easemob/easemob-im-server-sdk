package com.easemob.im.server.api.chatfiles;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.chatfiles.download.Download;
import com.easemob.im.server.api.chatfiles.upload.Upload;
import reactor.core.publisher.Mono;

import java.io.File;
import java.nio.file.Path;

public class FileApi {

    private Context context;

    private Path downloadDir;

    public FileApi(Context context, Path downloadDir) {
        if (!downloadDir.toFile().exists()) {
            downloadDir.toFile().mkdirs();
        }
        this.context = context;
        this.downloadDir = downloadDir;
    }

    public Mono<String> uploadFile(Path path, boolean restrictAccess) {
        return Upload.fromLocalFile(this.context, path, restrictAccess);
    }

    public Mono<Path> downloadFile(String fileId, String filename) {
        return Download.toLocalFile(this.context, fileId, this.downloadDir, filename);
    }


}
