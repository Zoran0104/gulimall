package com.zoran.gulimallware.dao;

import com.zoran.gulimallware.entity.WareSkuEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品库存
 * 
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 10:53:59
 */
@Mapper
public interface WareSkuDao extends BaseMapper<WareSkuEntity> {
	
}
