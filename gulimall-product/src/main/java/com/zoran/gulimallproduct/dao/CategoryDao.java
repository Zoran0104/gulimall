package com.zoran.gulimallproduct.dao;

import com.zoran.gulimallproduct.entity.CategoryEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 商品三级分类
 * 
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 00:03:52
 */
@Mapper
public interface CategoryDao extends BaseMapper<CategoryEntity> {
	
}
