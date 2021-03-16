package com.easemob.im.server.api.attachment;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.attachment.download.Download;
import com.easemob.im.server.api.attachment.upload.Upload;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

public class AttachmentApi {

    private Context context;

    public AttachmentApi(Context context) {
        this.context = context;
    }

    public Mono<String> uploadFile(Path path, boolean restrictAccess) {
        return Upload.fromLocalFile(this.context, path, restrictAccess);
    }

    public Mono<Path> downloadFile(String fileId, Path dir, String filename) {
        return Download.toLocalFile(this.context, fileId, dir, filename);
    }


}
