package com.easemob.im.server.integration.attachment;

import com.easemob.im.server.integration.AbstractIT;
import com.easemob.im.server.model.EMAttachment;
import org.junit.jupiter.api.Test;
import org.junit.platform.commons.util.ClassLoaderUtils;

import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.time.Duration;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

public class AttachmentIT extends AbstractIT {

    AttachmentIT() {
        super();
    }

    @Test
    void testAttachmentUpload() {
        String path =
                ClassLoaderUtils.getDefaultClassLoader().getResource("upload/image.png").getPath();
        Path uploadPath = FileSystems.getDefault().getPath(path);
        assertDoesNotThrow(() -> this.service.attachment().uploadFile(uploadPath)
                .block(Duration.ofSeconds(3)));
    }

    @Test
    void testAttachmentDownload() {
        Path uploadPath = FileSystems.getDefault().getPath(
                ClassLoaderUtils.getDefaultClassLoader().getResource("upload/image.png").getPath());
        EMAttachment attachment = assertDoesNotThrow(
                () -> this.service.attachment().uploadFile(uploadPath)
                        .block(Duration.ofSeconds(3)));

        Path downloadPath = FileSystems.getDefault().getPath(
                ClassLoaderUtils.getDefaultClassLoader().getResource("download/attachment/")
                        .getPath());
        assertDoesNotThrow(() -> this.service.attachment()
                .downloadFile(attachment.getId(), downloadPath, "file.png")
                .block(Duration.ofSeconds(3)));
    }

}
