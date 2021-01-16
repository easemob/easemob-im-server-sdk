package com.easemob.im.server.utils;

import com.easemob.im.server.api.ApiException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ContainerNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufAllocator;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpResponseStatus;
import reactor.core.publisher.Mono;
import reactor.netty.http.client.HttpClient;

import java.io.*;
import java.nio.charset.StandardCharsets;

public class HttpUtils {

    public static JsonNode execute(HttpClient http, HttpMethod method, String uri, ObjectMapper mapper) {
        return execute(http, method, uri, null, null, mapper);
    }

    public static JsonNode execute(HttpClient http, HttpMethod method, String uri, ContainerNode request, ByteBufAllocator allocator, ObjectMapper mapper) {
        Mono<ByteBuf> buf;
        if (request == null && allocator == null) {
            buf = Mono.empty();
        } else {
            ByteBuf bb = allocator.buffer();
            bb.writeCharSequence(request.toString(), StandardCharsets.UTF_8);
            buf = Mono.just(bb);
        }

        ObjectNode response = http
                .request(method)
                .uri(uri)
                .send(buf)
                .responseSingle((r, b) -> b.asString(StandardCharsets.UTF_8)
                        .map(s -> {
                            JsonNode jsonResult;
                            ObjectNode objectResult;
                            try {
                                jsonResult = mapper.readTree(s);
                                objectResult = mapper.createObjectNode();
                                objectResult.put("statusCode", r.status().code());
                                objectResult.set("result", jsonResult);
                            } catch (IOException e) {
                                throw new ApiException("could not decode json", e);
                            }
                            return objectResult;
                        })).block();

        if (response != null) {
            return verifyResponse(response);
        } else {
            throw new ApiException("response is null");
        }

    }

    // 上传附件请求
    public static JsonNode upload(HttpClient client, String uri, File file, ObjectMapper mapper) {

        ObjectNode response = client
                .request(HttpMethod.POST)
                .uri(uri)
                .sendForm((t,u) -> u.multipart(true).file("file", file))
                .responseSingle((r, b) -> b.asString(StandardCharsets.UTF_8)
                        .map(s -> {
                            JsonNode jsonResult;
                            ObjectNode objectResult;
                            try {
                                jsonResult = mapper.readTree(s);
                                objectResult = mapper.createObjectNode();
                                objectResult.put("statusCode", r.status().code());
                                objectResult.set("result", jsonResult);
                            } catch (IOException e) {
                                throw new ApiException("could not decode json", e);
                            }
                            return objectResult;
                        })).block();

        if (response != null) {
            return verifyResponse(response);
        } else {
            throw new ApiException("response is null");
        }
    }

    // 下载附件请求
    public static JsonNode download(HttpClient client, String uri, String assignDownloadPath, String assignDownloadName, ObjectMapper mapper) {
        String filePath;
        if (assignDownloadPath.endsWith("/")) {
            filePath = assignDownloadPath + assignDownloadName;
        } else {
            filePath = assignDownloadPath + "/" + assignDownloadName;
        }

        return client.request(HttpMethod.GET)
                .uri(uri)
                .responseSingle((r, b) -> {
                    if (!r.status().equals(HttpResponseStatus.OK) && r.status().code() != 200 && r.status().code() != 302) {
                        Mono<String> res = b.asString(StandardCharsets.UTF_8);
                        return Mono.error(new ApiException(String.format("%d - %s", r.status().code(), res)));
                    }

                    return b.asInputStream()
                            .map(s -> {
                                File file = new File(filePath);
                                try (OutputStream os = new BufferedOutputStream(new FileOutputStream(file))) {
                                    byte[] buffer = new byte[8192];
                                    int len;
                                    while ((len = s.read(buffer)) != -1) {
                                        os.write(buffer, 0, len);
                                    }
                                } catch (IOException e) {
                                    throw new ApiException("write file fail " + e);
                                }

                                ObjectNode response = mapper.createObjectNode();
                                response.put("code", r.status().code());
                                response.put("path", filePath);
                                response.put("result", "download attachment success");

                                return response;
                            });
                }).block();
    }

    private static JsonNode verifyResponse(ObjectNode response) {
        JsonNode result = response.get("result");
        JsonNode statusCode = response.get("statusCode");
        if (statusCode.asInt() != 200) {
            if (result != null) {
                if (result.get("error") != null && result.get("error_description") != null) {
                    throw new ApiException(String.format("statusCode %s , %s", statusCode, result));
                } else {
                    return result;
                }
            } else {
                throw new ApiException("response is null , statusCode " + statusCode);
            }
        } else {
            return result;
        }
    }

}
