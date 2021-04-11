package com.zoran.gulimall.auth.feign;

import com.zoran.common.utils.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient("gulimall-thirdparty")
public interface ThirdPartyFeignService {
    @GetMapping("/sms/sendSms")
    R sendSms(@RequestParam("mobile") String mobile, @RequestParam("code") String code);
}
