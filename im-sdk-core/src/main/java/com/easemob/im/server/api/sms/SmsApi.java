package com.easemob.im.server.api.sms;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.sms.send.SendSms;
import com.easemob.im.server.api.sms.send.SendSmsResponse;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

/**
 * 发送短信API。
 * 支持:
 * - 发送短信
 */
public class SmsApi {

    private SendSms sendSms;

    public SmsApi(Context context) {
        this.sendSms = new SendSms(context);
    }

    /**
     * 发送短信
     * @param mobiles  需要发送短信的手机号，最多100个号码
     * @param tid  短信模板id
     * @param tmap  模板变量名和变量值对
     * @param extendCode  扩展码
     * @param custom  用户自定义属性，长度不能超过64字符
     * @return 发送短信的数量以及成功信息
     * @see com.easemob.im.server.api.sms.send.SendSmsResponse
     */
    public Mono<SendSmsResponse> send(Set<String> mobiles, String tid, Map<String, String> tmap, String extendCode, String custom) {
        return this.sendSms.send(mobiles, tid, tmap, extendCode, custom);
    }
}
