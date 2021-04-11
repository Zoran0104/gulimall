package com.zoran.gulimallmember.controller;

import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.R;
import com.zoran.gulimallmember.entity.MemberEntity;
import com.zoran.gulimallmember.service.MemberService;
import com.zoran.gulimallmember.vo.UserLoginVo;
import com.zoran.gulimallmember.vo.UserRegisterVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;


/**
 * 会员
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 09:57:59
 */
@RestController
@RequestMapping("member/member")
public class MemberController {
    @Autowired
    private MemberService memberService;

    @PostMapping("/register")
    public R register(@RequestBody UserRegisterVo userRegisterVo) {
        try {
            memberService.register(userRegisterVo);
        } catch (Exception e) {
            return R.error(510, "用户名或手机号已经存在");
        }
        return R.ok();
    }

    @PostMapping("/login")
    public R login(@RequestBody UserLoginVo userLoginVo) {
        MemberEntity memberEntity = memberService.login(userLoginVo);
        return Optional.ofNullable(memberEntity).map(entity -> R.ok()).orElse(R.error(555,"账号或密码错误"));
    }

    /**
     * 列表
     */
    @RequestMapping("/list")
    public R list(@RequestParam Map<String, Object> params) {
        PageUtils page = memberService.queryPage(params);

        return R.ok().put("page", page);
    }


    /**
     * 信息
     */
    @RequestMapping("/info/{id}")
    public R info(@PathVariable("id") Long id) {
        MemberEntity member = memberService.getById(id);

        return R.ok().put("member", member);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    public R save(@RequestBody MemberEntity member) {
        memberService.save(member);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    public R update(@RequestBody MemberEntity member) {
        memberService.updateById(member);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    public R delete(@RequestBody Long[] ids) {
        memberService.removeByIds(Arrays.asList(ids));

        return R.ok();
    }

}
