package com.easemob.im.server.api.attachment;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.attachment.download.Download;
import com.easemob.im.server.api.attachment.upload.Upload;
import com.easemob.im.server.model.EMAttachment;
import reactor.core.publisher.Mono;

import java.nio.file.Path;

/**
 * 附件API
 * TODO: 支持上传和下载带有密码的附件
 */
public class AttachmentApi {

    private Context context;
    
    private Upload upload;

    private Download download;

    public AttachmentApi(Context context) {
        this.context = context;
        this.upload = new Upload(context);
        this.download =  new Download(context);
    }

    /**
     * 上传附件
     *
     * @param path 要上传的文件的路径
     * @return 上传完成后返回附件的id
     * @see <a href="http://docs-im.easemob.com/im/server/basics/fileoperation#%E4%B8%8A%E4%BC%A0%E8%AF%AD%E9%9F%B3%E5%9B%BE%E7%89%87%E6%96%87%E4%BB%B6">上传附件</a>
     */
    public Mono<EMAttachment> uploadFile(Path path) {
        return this.upload.fromLocalFile(path, false);
    }

    /* TODO: 上传带有密码保护的附件 */

    /**
     * 下载附件
     * @param fileId 附件的id
     * @param dir 下载到哪个目录，如果不存在会自动创建
     * @param filename 下载到哪个文件
     * @return 下载完成后返回文件路径
     *
     * @see <a href="http://docs-im.easemob.com/im/server/basics/fileoperation#%E4%B8%8B%E8%BD%BD%E8%AF%AD%E9%9F%B3%E5%9B%BE%E7%89%87%E6%96%87%E4%BB%B6">下载附件</a>
     */
    public Mono<Path> downloadFile(String fileId, Path dir, String filename) {
        return this.download.toLocalFile(fileId, dir, filename);
    }


}
