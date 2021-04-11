package com.zoran.gulimall.auth.feign;

import com.zoran.common.utils.R;
import com.zoran.gulimall.auth.vo.UserRegisterVo;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient("gulimall-member")
public interface MemberFeignService {
    @PostMapping("member/member/register")
    R register(@RequestBody UserRegisterVo userRegisterVo);
}
