package com.zoran.gulimall.auth.controller;

import com.zoran.common.constant.AuthConstant;
import com.zoran.common.utils.R;
import com.zoran.gulimall.auth.feign.ThirdPartyFeignService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@RestController
@AllArgsConstructor
@Slf4j
public class LoginController {
    private final ThirdPartyFeignService thirdPartyFeignService;
    private final StringRedisTemplate stringRedisTemplate;

    @GetMapping("/sms/sendCode")
    public R sendCode(@RequestParam("mobile") String mobile) {
        String result = stringRedisTemplate.opsForValue().get(AuthConstant.SMS_CODE_CACHE_PREFIX + mobile);
        if (result != null && System.currentTimeMillis() - Long.parseLong(result.split("-")[1]) < 60 * 1000) {
            return R.error(503,"短信发送间隔太短，请稍后再试");
        }
        String code = UUID.randomUUID().toString().substring(0, 5);
        System.err.println(code);
        stringRedisTemplate.opsForValue()
                .set(AuthConstant.SMS_CODE_CACHE_PREFIX + mobile,
                        code + "-" + System.currentTimeMillis(), 10, TimeUnit.MINUTES);
        // 因短信服务商收费问题，此处仅模拟调用，非实际调用
        //thirdPartyFeignService.sendSms(mobile, code);
        return R.ok();
    }



}
