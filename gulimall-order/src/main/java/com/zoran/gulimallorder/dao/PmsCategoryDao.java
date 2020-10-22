package com.zoran.gulimallorder.dao;

import com.zoran.gulimallorder.entity.PmsCategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 10:50:27
 */
@Mapper
public interface PmsCategoryDao extends BaseMapper<PmsCategoryEntity> {
	
}
