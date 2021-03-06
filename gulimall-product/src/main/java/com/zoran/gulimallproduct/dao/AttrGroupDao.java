package com.zoran.gulimallproduct.dao;

import com.zoran.gulimallproduct.entity.AttrAttrgroupRelationEntity;
import com.zoran.gulimallproduct.entity.AttrGroupEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * 属性分组
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 00:03:52
 */
@Mapper
public interface AttrGroupDao extends BaseMapper<AttrGroupEntity> {

    void batchDeleteRelations(@Param("entities") AttrAttrgroupRelationEntity[] attrAttrgroupRelationEntities);
}
