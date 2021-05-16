package com.easemob.im.server.api.sms;

import com.easemob.im.server.api.Context;
import com.easemob.im.server.api.sms.send.SendSms;
import reactor.core.publisher.Mono;

import java.util.Map;
import java.util.Set;

/**
 * 发送短信API。
 */
public class SmsApi {

    private SendSms sendSms;

    public SmsApi(Context context) {
        this.sendSms = new SendSms(context);
    }

    /**
     * 发送短信
     * @param phoneNumbers 需要发送短信的手机号，最多100个号码
     * @param smsTemplateId 短信模板id，在环信console后台添加短信模板后自动生成此id，根据模板id发送对应的短信模板内容
     * @param templateVariableContent 传入在环信console后台短信模板内容中自己定义的变量名以及需要给该变量名传的值，
     *                                比如我在短信模板内容中定义的变量名为 "smsVerificationCode" 代表短信验证码，那么需要这样 {"smsVerificationCode":"965789"}
     * @param extendCode 扩展码，主要用于自己短信业务区分的标识，当用户自定义扩展码后，平台推送短信发送状态或平台推送用户上行短信时，将携带此扩展码，回调给你的回调地址
     * @param custom 用户自定义属性，长度不能超过64字符，该参数会回调给你的回调地址
     * @return 发送短信的数量
     * @see <a href="http://docs-im.easemob.com/telco/sms/api#%E5%8F%91%E9%80%81%E7%9F%AD%E4%BF%A1">发送短信</a>
     */
    public Mono<Integer> send(Set<String> phoneNumbers, String smsTemplateId, Map<String, String> templateVariableContent, String extendCode, String custom) {
        return this.sendSms.send(phoneNumbers, smsTemplateId, templateVariableContent, extendCode, custom);
    }
}
