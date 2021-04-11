package com.zoran.gulimallmember.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zoran.common.utils.PageUtils;
import com.zoran.common.utils.Query;
import com.zoran.gulimallmember.dao.MemberDao;
import com.zoran.gulimallmember.entity.MemberEntity;
import com.zoran.gulimallmember.entity.MemberLevelEntity;
import com.zoran.gulimallmember.exception.PhoneExistException;
import com.zoran.gulimallmember.exception.UsernameExistException;
import com.zoran.gulimallmember.service.MemberLevelService;
import com.zoran.gulimallmember.service.MemberService;
import com.zoran.gulimallmember.vo.UserRegisterVo;
import lombok.AllArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("memberService")
@AllArgsConstructor
public class MemberServiceImpl extends ServiceImpl<MemberDao, MemberEntity> implements MemberService {
    private final MemberLevelService memberLevelService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        IPage<MemberEntity> page = this.page(
                new Query<MemberEntity>().getPage(params),
                new QueryWrapper<MemberEntity>()
        );

        return new PageUtils(page);
    }

    @Override
    public void register(UserRegisterVo userRegisterVo) throws Exception {
        MemberEntity memberEntity = new MemberEntity();
        checkPhoneUnique(userRegisterVo.getPhone());
        checkUserName(userRegisterVo.getUserName());
        MemberLevelEntity memberLevelEntity = memberLevelService.getDefaultLevel();
        memberEntity.setLevelId(memberLevelEntity.getId());
        memberEntity.setMobile(userRegisterVo.getPhone());
        memberEntity.setUsername(userRegisterVo.getUserName());
        memberEntity.setPassword(new BCryptPasswordEncoder().encode(userRegisterVo.getPassword()));
        this.baseMapper.insert(memberEntity);
    }

    private void checkUserName(String userName) throws Exception {
        if (this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("username", userName))>0) {
            throw new UsernameExistException();
        }
    }

    private void checkPhoneUnique(String phone) throws Exception {
        if (this.baseMapper.selectCount(new QueryWrapper<MemberEntity>().eq("mobile", phone))>0) {
            throw new PhoneExistException();
        }
    }

}
