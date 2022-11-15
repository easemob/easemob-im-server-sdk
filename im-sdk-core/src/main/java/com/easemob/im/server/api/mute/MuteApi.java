package com.easemob.im.server.api.mute;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.mute.detail.MuteDetail;
import com.easemob.im.server.api.mute.list.GetMuteListResponse;
import com.easemob.im.server.api.mute.list.MuteList;
import com.easemob.im.server.api.mute.mute.MuteUser;
import com.easemob.im.server.api.mute.mute.MuteUserRequest;
import com.easemob.im.server.model.EMMute;
import reactor.core.publisher.Mono;

import java.util.List;

public class MuteApi {

    private MuteUser muteUser;

    private MuteDetail muteDetail;

    private MuteList muteList;

    public MuteApi(Context context) {
        this.muteUser = new MuteUser(context);
        this.muteDetail = new MuteDetail(context);
        this.muteList = new MuteList(context);
    }

    /**
     * 设置用户全局禁言。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     MuteUserRequest request = MuteUserRequest.builder().username("test_user").chat(0).build();
     *     service.mute().muteUser(request).block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param request 用户全局禁言请求体
     * @return 成功或错误
     * @see <a href="https://docs-im.easemob.com/ccim/rest/accountban#%E8%AE%BE%E7%BD%AE%E7%94%A8%E6%88%B7%E5%85%A8%E5%B1%80%E7%A6%81%E8%A8%80">设置用户全局禁言</a>
     */
    public Mono<Void> muteUser(MuteUserRequest request) {
        return this.muteUser.execute(request);
    }

    /**
     * 查询单个用户全局禁言剩余时间。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.mute().detail("test_user").block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @param username 查询禁言信息的用户名
     * @return 成功或错误
     * @see <a href="https://docs-im.easemob.com/ccim/rest/accountban#%E6%9F%A5%E8%AF%A2%E5%8D%95%E4%B8%AA%E7%94%A8%E6%88%B7_id_%E5%85%A8%E5%B1%80%E7%A6%81%E8%A8%80">查询单个用户全局禁言剩余时间</a>
     */
    public Mono<EMMute> detail(String username) {
        return this.muteDetail.execute(username);
    }

    /**
     * 查询所有用户全局禁言剩余时间。
     * <p>
     * API使用示例：
     * <pre> {@code
     * EMService service;
     * try {
     *     service.mute().muteList().block();
     * } catch (EMException e) {
     *     e.getErrorCode();
     *     e.getMessage();
     * }
     * }</pre>
     *
     * @return 成功或错误
     * @see <a href="https://docs-im.easemob.com/ccim/rest/accountban#%E6%9F%A5%E8%AF%A2_app_key_%E7%9A%84%E7%94%A8%E6%88%B7%E7%A6%81%E8%A8%80">查询所有用户全局禁言剩余时间</a>
     */
    public Mono<GetMuteListResponse> muteList() {
        return this.muteList.execute();
    }
}
