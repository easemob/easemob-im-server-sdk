package com.easemob.im.server.api.moderation;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.moderation.download.RecordFileDownload;
import com.easemob.im.server.api.moderation.export.ExportMessageRecord;
import com.easemob.im.server.api.moderation.export.ExportMessageRecordResponse;
import com.easemob.im.server.api.moderation.list.ExportDetailsList;
import com.easemob.im.server.api.moderation.list.ExportDetailsListResponse;
import com.easemob.im.server.exception.EMInvalidArgumentException;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * 开通内容审核（高级版）服务后，过内容审核的消息会有审核记录，可以通过以下 API 导出这块数据记录
 */
public class ModerationApi {

    private ExportMessageRecord exportMessageRecord;

    private ExportDetailsList exportDetailsList;

    private RecordFileDownload recordFileDownload;

    public ModerationApi(Context context) {
        this.exportMessageRecord = new ExportMessageRecord(context);
        this.exportDetailsList = new ExportDetailsList(context);
        this.recordFileDownload = new RecordFileDownload(context);
    }

    /**
     * 按查询条件导出文件。
     * <p>
     *
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     String uuid = service.moderation().export(1646723027000, 1646733127000, "chat", "txt", "PASS", "PASS").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param beginTimestamp 查询起始时间，毫秒时间戳
     * @param endTimestamp 查询截止时间，毫秒时间戳
     * @param targetType 用判断单聊、群聊还是聊天室，chat: 单聊；groupchat: 群聊；chatroom: 聊天室
     * @param messageType 消息类型，txt: 文本；img: 图片；video: 视频；audio: 音频
     * @param moderationResult 处置结果，PASS: 通过；REJECT: 拒绝；EXCHANGE: 替换；RECALL: 撤回
     * @param providerResult 审核结果，PASS: 正常内容；REVIEWED: 需要审核；REJECT: 违规内容；UNKNOWN: 异常
     * @return uuid
     */
    public Mono<String> export(long beginTimestamp, long endTimestamp, String targetType, String messageType, String moderationResult, String providerResult) {

        if (beginTimestamp == 0 || endTimestamp == 0) {
            throw new EMInvalidArgumentException("beginTimestamp or endTimestamp Cannot be 0");
        }

        if (beginTimestamp > endTimestamp) {
            throw new EMInvalidArgumentException("beginTimestamp cannot be greater than endTimestamp");
        }

        return this.exportMessageRecord.export(beginTimestamp, endTimestamp, targetType, messageType, moderationResult, providerResult);
    }

    /**
     * 获取导出详情列表。
     * <p>
     *
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     String uuid = service.moderation().get(1, 10, "287c0730-9e97-11ec-ba62-139a925bb42e").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param page 导出哪页的详情
     * @param pageSize 每页详情的数量
     * @param uuid 导出文件的uuid，通过 "按查询条件导出文件" 的 api 获取
     * @return 详情列表
     */
    public Mono<ExportDetailsListResponse> get(int page, int pageSize, String uuid) {
        return this.exportDetailsList.get(page, pageSize, uuid);
    }

    /**
     * 下载文件。
     * <p>
     *
     * API使用示例：
     * <pre> {@code
     * EMService service;
     *
     * try {
     *     Map<String, Object> fileMap = service.moderation().download("287c0730-9e97-11ec-ba62-139a925bb42e").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param uuid 文件的uuid，通过 "按查询条件导出文件" 的 api 获取
     * @return
     */
    public Mono<Map> download(String uuid) {
        return this.recordFileDownload.download(uuid);
    }
}
