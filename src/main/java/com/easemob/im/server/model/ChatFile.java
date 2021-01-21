package com.easemob.im.server.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChatFile {
    /**
     * 附件的名称（带后缀名的）
     */
    private String attachmentName;

    /**
     * 附件的 uuid
     */
    private String uuid;

    /**
     * 附件的 share-secret
     */
    private String shareSecret;

    /**
     * 附件的 url
     */
    private String url;

    /**
     * 附件的上传或下载时间
     */
    private Long timestamp;
}
