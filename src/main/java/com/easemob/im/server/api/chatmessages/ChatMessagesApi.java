package com.easemob.im.server.api.chatmessages;

import com.easemob.im.server.api.chatfiles.exception.ChatFilesException;
import com.easemob.im.server.api.chatmessages.exception.ChatMessagesException;
import com.easemob.im.server.api.message.exception.MessageException;
import com.easemob.im.server.utils.HttpUtils;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import io.netty.handler.codec.http.HttpMethod;

import reactor.netty.http.client.HttpClient;

import java.util.regex.Pattern;

public class ChatMessagesApi {
    private static final Pattern VALID_TIME_PATTERN = Pattern.compile("[0-9]{10}");

    private final HttpClient http;

    private final ObjectMapper mapper;

    public ChatMessagesApi(HttpClient http, ObjectMapper mapper) {
        this.http = http;
        this.mapper = mapper;
    }

    /**
     * 获取历史消息文件
     *
     * 环信官网接口文档：http://docs-im.easemob.com/im/server/basics/chatrecord#%E8%8E%B7%E5%8F%96%E5%8E%86%E5%8F%B2%E6%B6%88%E6%81%AF%E6%96%87%E4%BB%B6
     *
     * 导出聊天记录接口不是实时接口，获取成功存在一定的延时，不能够作为实时拉取消息的接口使用。
     *
     * 接口返回的下载地址30分钟内有效，服务端默认保存3天文本消息，7天文件消息，如需延长存储时间请联系商务经理。
     *
     * @param time 获取历史消息的时间，查询的时间格式为10位数字形式(YYYYMMDDHH),例如要查询2016年12月10号7点到8点的历史记录，
     *             则需要输入2016121007,7:00:00的信息也会包含在这个文件里。
     *             因为历史记录文件生成需要一定时间，建议用户在取得历史记录时要间隔一个小时，
     *             例如2016/12/10 09:00之后，可以开始下载2016/12/10 07:00 ～ 08:00的消息历史记录。
     * @return String
     */
    public String getHistoryMessage(Long time) {
        verifyTime(time);

        String uri = "/chatmessages/" + time;
        JsonNode result = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);

        if (result != null) {
            ArrayNode data = (ArrayNode) result.get("data");
            if (data != null && data.size() > 0) {
                JsonNode url = data.get(0).get("url");
                if (url != null) {
                    return url.asText();
                } else {
                    throw new ChatMessagesException("result url is null");
                }
            } else {
                throw new ChatMessagesException("result data is null");
            }
        } else {
            throw new ChatMessagesException("result is null");
        }
    }


    /**
     * 指定路径自动下载历史消息文件
     * @param time       获取历史消息的时间
     * @param localPath  指定下载历史消息文件的路径
     * @return JsonNode
     */
    public JsonNode getHistoryMessageAndAutoDownloadFile(Long time, String localPath) {
        verifyTime(time);
        verifyFileLocalPath(localPath);

        String uri = "/chatmessages/" + time;
        JsonNode result = HttpUtils.execute(this.http, HttpMethod.GET, uri, this.mapper);

        if (result != null) {
            ArrayNode data = (ArrayNode) result.get("data");
            if (data != null && data.size() > 0) {
                JsonNode url = data.get(0).get("url");
                if (url != null) {
                    return HttpUtils.download(this.http, url.asText(), localPath, String.format("%s.gz", time.toString()), this.mapper);
                } else {
                    throw new ChatMessagesException("result url is null");
                }
            } else {
                throw new ChatMessagesException("result data is null");
            }
        } else {
            throw new ChatMessagesException("result is null");
        }
    }

    // 验证时间
    private void verifyTime(Long time) {
        if (time == null || !VALID_TIME_PATTERN.matcher(String.valueOf(time)).matches()) {
            throw new MessageException(String.format("Bad Request %s invalid time", time));
        }
    }

    // 验证 file local path
    private void verifyFileLocalPath(String localPath) {
        if (localPath == null || localPath.isEmpty()) {
            throw new ChatFilesException("Bad Request invalid localPath");
        }
    }
}
