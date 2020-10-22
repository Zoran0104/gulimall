package com.zoran.gulimallmember.dao;

import com.zoran.gulimallmember.entity.MemberEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 会员
 * 
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 09:57:59
 */
@Mapper
public interface MemberDao extends BaseMapper<MemberEntity> {
	
}
