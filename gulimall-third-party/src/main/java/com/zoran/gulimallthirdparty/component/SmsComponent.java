package com.zoran.gulimallthirdparty.component;

import lombok.Data;
import org.apache.http.HttpResponse;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author ：zoran
 * @date ：Created in 2021/4/10 15:26
 * @description：
 * @modified By：
 * @version:
 */
@Component
@ConfigurationProperties(prefix = "alibaba.cloud.sms")
@Data
public class SmsComponent {
    private String host;
    private String path;
    private String method;
    private String appcode;
    private String templateId;
    private String signId;
    public void sendSms(String code,String mobile) {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>(16);
        querys.put("mobile", mobile);
        querys.put("param", "**code**:"+code+",**minute**:5");
        querys.put("smsSignId", signId);
        querys.put("templateId", templateId);
        Map<String, String> bodys = new HashMap<>();
        try {
            HttpResponse response = HttpUtils.doPost(host, path, method, headers, querys, bodys);
            System.out.println(response.toString());
            //获取response的body
            //System.out.println(EntityUtils.toString(response.getEntity()));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
