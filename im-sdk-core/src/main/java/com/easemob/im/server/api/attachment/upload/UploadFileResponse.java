package com.easemob.im.server.api.attachment.upload;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.List;

public class UploadFileResponse {

    @JsonProperty("uri")
    private String baseUrl;

    @JsonProperty("entities")
    private List<FileUploaded> files;

    @JsonCreator
    public UploadFileResponse(@JsonProperty("entities") List<FileUploaded> files,
            @JsonProperty("uri") String baseUrl) {
        this.files = files;
        this.baseUrl = baseUrl;
    }

    public String getBaseUrl() {
        return this.baseUrl;
    }

    public List<FileUploaded> getFiles() {
        return this.files;
    }

    public static class FileUploaded {
        @JsonProperty("uuid")
        private String id;
        @JsonProperty("type")
        private String type;
        @JsonProperty("share-secret")
        private String secret;

        @JsonCreator
        public FileUploaded(@JsonProperty("uuid") String id,
                @JsonProperty("type") String type,
                @JsonProperty("share-secret") String secret) {
            this.id = id;
            this.type = type;
            this.secret = secret;
        }

        public String getId() {
            return this.id;
        }

        public String getType() {
            return this.type;
        }

        public String getSecret() {
            return this.secret;
        }
    }

}
