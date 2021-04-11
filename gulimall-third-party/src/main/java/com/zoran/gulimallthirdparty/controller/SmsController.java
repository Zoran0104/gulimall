package com.zoran.gulimallthirdparty.controller;

import com.zoran.common.utils.R;
import com.zoran.gulimallthirdparty.component.SmsComponent;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ：zoran
 * @date ：Created in 2021/4/10 15:57
 * @description：
 * @modified By：
 * @version:
 */
@RestController
@RequestMapping("sms")
@AllArgsConstructor
public class SmsController {
    private final SmsComponent smsComponent;

    @GetMapping("/sendSms")
    public R sendSms(String mobile, String code) {
        smsComponent.sendSms(code, mobile);
        return R.ok();
    }
}
