package com.zoran.gulimallmember.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoran.common.utils.PageUtils;
import com.zoran.gulimallmember.entity.MemberEntity;
import com.zoran.gulimallmember.vo.UserLoginVo;
import com.zoran.gulimallmember.vo.UserRegisterVo;

import java.util.Map;

/**
 * 会员
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 09:57:59
 */
public interface MemberService extends IService<MemberEntity> {

    PageUtils queryPage(Map<String, Object> params);

    void register(UserRegisterVo userRegisterVo) throws Exception;

    MemberEntity login(UserLoginVo userLoginVo);
}

