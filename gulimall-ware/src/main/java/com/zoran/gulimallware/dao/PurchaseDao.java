package com.zoran.gulimallware.dao;

import com.zoran.gulimallware.entity.PurchaseEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 采购信息
 * 
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 10:54:00
 */
@Mapper
public interface PurchaseDao extends BaseMapper<PurchaseEntity> {
	
}
