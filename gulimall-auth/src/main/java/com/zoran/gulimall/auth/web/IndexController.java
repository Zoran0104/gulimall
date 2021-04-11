package com.zoran.gulimall.auth.web;

import com.zoran.common.constant.AuthConstant;
import com.zoran.common.utils.R;
import com.zoran.gulimall.auth.feign.MemberFeignService;
import com.zoran.gulimall.auth.vo.UserLoginVo;
import com.zoran.gulimall.auth.vo.UserRegisterVo;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Controller
@AllArgsConstructor
public class IndexController {
    private final StringRedisTemplate stringRedisTemplate;
    private final MemberFeignService memberFeignService;
    private final String SUCCESS_REGISTER_FORWARD = "redirect:http://auth.gulimall.com/login.html";
    private final String FAIL_REGISTER_FORWARD = "redirect:http://auth.gulimall.com/register.html";
    private final String SUCCESS_LOGIN_FORWARD = "redirect:http://gulimall.com/";
    private final String FAIL_LOGIN_FORWARD = "redirect:http://gulimall.com/";

    @PostMapping("/register")
    public String register(@Valid UserRegisterVo userRegisterVo, BindingResult result, RedirectAttributes attr) {
        if (result.hasErrors()) {
            Map<String, String> map = result.getFieldErrors().stream()
                    .collect(Collectors.toMap(FieldError::getField, FieldError::getDefaultMessage, (v1, v2) -> v1));
            attr.addFlashAttribute("errors", map);
            return FAIL_REGISTER_FORWARD;
        }
        String code = stringRedisTemplate.opsForValue()
                .get(AuthConstant.SMS_CODE_CACHE_PREFIX + userRegisterVo.getPhone());
        if (!StringUtils.isEmpty(code) && code.split("-")[0].equals(userRegisterVo.getCode())) {
            stringRedisTemplate.delete(AuthConstant.SMS_CODE_CACHE_PREFIX + userRegisterVo.getPhone());
            //调用远程服务写入用户信息
            R register = memberFeignService.register(userRegisterVo);
            return SUCCESS_REGISTER_FORWARD;
        }
        Map<String, String> map = new HashMap<>(16);
        map.put("code", "验证码错误或已失效，请核对后输入");
        attr.addFlashAttribute("errors", map);
        return FAIL_REGISTER_FORWARD;
    }

    @PostMapping("/login")
    public String login(UserLoginVo userLoginVo) {
        R login = memberFeignService.login(userLoginVo);
        if (login.get("code").equals(0)) {
            return SUCCESS_LOGIN_FORWARD;
        }
        return FAIL_LOGIN_FORWARD;
    }
}
