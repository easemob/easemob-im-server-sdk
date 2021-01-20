package com.easemob.im.server.api.chatfiles;

import com.easemob.im.server.EMProperties;
import com.easemob.im.server.api.chatfiles.exception.ChatFilesException;
import com.easemob.im.server.model.ChatFile;
import com.easemob.im.server.utils.HttpUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.github.benmanes.caffeine.cache.Cache;
import reactor.netty.http.client.HttpClient;

import java.io.*;

public class ChatFilesApi {

    private final HttpClient http;

    private final ObjectMapper mapper;

    private final EMProperties properties;

    private final Cache<String, String> tokenCache;

    public ChatFilesApi(HttpClient http, ObjectMapper mapper, EMProperties properties, Cache<String, String> tokenCache) {
        this.http = http;
        this.mapper = mapper;
        this.properties = properties;
        this.tokenCache = tokenCache;
    }

    /**
     * 上传附件(包含图片，语音，视频，文件)
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/fileoperation#%E4%B8%8A%E4%BC%A0%E8%AF%AD%E9%9F%B3%E5%9B%BE%E7%89%87%E6%96%87%E4%BB%B6
     *
     * 默认上传文件大小不能超过 10M，超过会上传失败
     *
     * @param fileLocalPath  传附件的本地路径
     * @return JsonNode
     */
    public ChatFile uploadAttachment(String fileLocalPath) {
        verifyFileLocalPath(fileLocalPath);

        File file = new File(fileLocalPath);
        JsonNode response = uploadRequest(file);

        return responseToChatFileObject(file, response);
    }

    /**
     * 上传附件(包含图片，语音，视频，文件)
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/fileoperation#%E4%B8%8A%E4%BC%A0%E8%AF%AD%E9%9F%B3%E5%9B%BE%E7%89%87%E6%96%87%E4%BB%B6
     *
     * 默认上传文件大小不能超过 10M，超过会上传失败
     *
     * @param file  传文件
     * @return JsonNode
     */
    public ChatFile uploadAttachment(File file) {
        if (file == null || !file.exists()) {
            throw new ChatFilesException("invalid file");
        }
        JsonNode response = uploadRequest(file);
        return responseToChatFileObject(file, response);
    }

    /**
     * 下载附件（包含图片，语音，视频，文件）
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/fileoperation#%E4%B8%8B%E8%BD%BD%E8%AF%AD%E9%9F%B3%E5%9B%BE%E7%89%87%E6%96%87%E4%BB%B6
     *
     * @param shareSecret                   向环信服务器上传附件后，环信给返回的 share_secret
     * @param uuid                          向环信服务器上传附件后，环信给返回的 uuid
     * @param assignDownloadAttachmentPath  指定附件要下载到的路径，例如 /xx/.../file/
     * @param assignDownloadAttachmentName  指定下载附件的名称，要加后缀，比如下载的图片就是 imageName.jpg , imageName.png
     * @return JsonNode
     */
    public JsonNode downloadAttachment(String shareSecret, String uuid, String assignDownloadAttachmentPath, String assignDownloadAttachmentName) {
        verifyShareSecret(shareSecret);
        verifyUuid(uuid);
        verifyAssignDownloadPath(assignDownloadAttachmentPath);
        verifyAssignDownLoadName(assignDownloadAttachmentName);

        HttpClient client = this.http.headers(h -> {
            h.add("Accept", "application/octet-stream");
            h.add("share-secret", shareSecret);
        });

        return downloadAttachmentRequest(client, uuid, assignDownloadAttachmentPath, assignDownloadAttachmentName);
    }

    /**
     * 下载缩略图
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/fileoperation#%E4%B8%8B%E8%BD%BD%E7%BC%A9%E7%95%A5%E5%9B%BE
     *
     * 环信支持在服务器端自动的创建图片的缩略图。可以先下载缩略图，当用户有需求的时候，再下载大图。
     * 这里和下载大图唯一不同的就是 header 中多了一个“thumbnail: true”，当服务器看到过来的请求的 header 中包括这个的时候，就会返回缩略图，否则返回原始大图
     *
     * @param shareSecret                  向环信服务器上传附件后，环信给返回的 share_secret
     * @param uuid                         向环信服务器上传附件后，环信给返回的 uuid
     * @param assignDownloadThumbnailPath  指定缩略图要下载到的路径，例如 /xx/.../file/
     * @param assignDownloadThumbnailName  指定下载缩略图的名称，要加后缀，比如下载的图片就是 imageName.jpg , imageName.png
     * @return JsonNode
     */
    public JsonNode downloadThumbnail(String shareSecret, String uuid, String assignDownloadThumbnailPath, String assignDownloadThumbnailName) {
        verifyShareSecret(shareSecret);
        verifyUuid(uuid);
        verifyAssignDownloadPath(assignDownloadThumbnailPath);
        verifyAssignDownLoadName(assignDownloadThumbnailName);

        HttpClient client = this.http.headers(h -> {
            h.add("Accept", "application/octet-stream");
            h.add("share-secret", shareSecret);
            h.add("thumbnail", true);
        });

        return downloadAttachmentRequest(client, uuid, assignDownloadThumbnailPath, assignDownloadThumbnailName);
    }

    // 上传附件请求
    private JsonNode uploadRequest(File file) {
        HttpClient client = this.http.headers(h -> {
            h.add("restrict-access", "true");
        });

        return HttpUtils.upload(client, "/chatfiles", file, this.mapper, this.properties, this.tokenCache);
    }

    // 下载附件请求
    private JsonNode downloadAttachmentRequest(HttpClient client, String uuid, String assignDownloadPath, String assignDownloadName) {
        String uri = "/chatfiles/" + uuid;
        return HttpUtils.download(client, uri, assignDownloadPath, assignDownloadName, this.mapper, this.properties, this.tokenCache);
    }

    // 上传附件返回结果转成 ChatFile 对象
    private ChatFile responseToChatFileObject(File file, JsonNode response) {
        String uuid;
        String shareSecret;

        ArrayNode entities = (ArrayNode) response.get("entities");
        if (entities != null) {
            JsonNode entitiesJson = entities.get(0);
            if (entitiesJson.get("uuid") != null || entitiesJson.get("share-secret") != null) {
                uuid = entitiesJson.get("uuid").asText();
                shareSecret = entitiesJson.get("share-secret").asText();
            } else {
                throw new ChatFilesException("uuid or share-secret is null");
            }
        } else {
            throw new ChatFilesException("entities is null");
        }

        return ChatFile.builder()
                .attachmentName(file.getName())
                .uuid(uuid)
                .shareSecret(shareSecret)
                .timestamp(response.get("timestamp").asLong())
                .build();
    }

    // 验证 file local path
    private void verifyFileLocalPath(String localPath) {
        if (localPath == null || localPath.isEmpty()) {
            throw new ChatFilesException("Bad Request invalid localPath");
        }
    }

    // 验证 share secret
    private void verifyShareSecret(String shareSecret) {
        if (shareSecret == null || shareSecret.isEmpty()) {
            throw new ChatFilesException("Bad Request invalid shareSecret");
        }
    }

    // 验证 share secret
    private void verifyUuid(String uuid) {
        if (uuid == null || uuid.isEmpty()) {
            throw new ChatFilesException("Bad Request invalid uuid");
        }
    }

    // 验证 assign download path
    private void verifyAssignDownloadPath(String assignDownloadPath) {
        if (assignDownloadPath == null || assignDownloadPath.isEmpty()) {
            throw new ChatFilesException("Bad Request invalid assignDownloadPath");
        }
    }

    // 验证 assign download name
    private void verifyAssignDownLoadName(String assignDownloadName) {
        if (assignDownloadName == null || assignDownloadName.isEmpty()) {
            throw new ChatFilesException("Bad Request invalid assignDownloadName");
        }
    }
}
