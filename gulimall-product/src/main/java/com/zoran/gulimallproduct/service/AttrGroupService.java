package com.zoran.gulimallproduct.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.zoran.common.utils.PageUtils;
import com.zoran.gulimallproduct.entity.AttrAttrgroupRelationEntity;
import com.zoran.gulimallproduct.entity.AttrGroupEntity;

import java.util.Map;

/**
 * 属性分组
 *
 * @author zoran
 * @email zwyliufneg@outlook.com
 * @date 2020-10-22 00:03:52
 */
public interface AttrGroupService extends IService<AttrGroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    PageUtils queryPage(Map<String, Object> params, Long catalogId);

    void deleteRelation(AttrAttrgroupRelationEntity[] attrAttrgroupRelationEntities);
}

